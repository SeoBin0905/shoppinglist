package com.example.MartBee;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MapActivity extends AppCompatActivity {

    private Button showList, prev, next;
    private ImageView imageView;

    enum TOUCH_MODE {
        NONE,   // 터치 안했을 때
        SINGLE, // 한손가락 터치
        MULTI   //두손가락 터치
    }
    private TOUCH_MODE touchMode;
    private Matrix matrix;      //기존 매트릭스
    private Matrix savedMatrix; //작업 후 이미지에 매핑할 매트릭스
    private PointF startPoint;  //한손가락 터치 이동 포인트
    private PointF midPoint;    //두손가락 터치 시 중신 포인트
    private float oldDistance;  //터치 시 두손가락 사이의 거리
    private double oldDegree = 0; // 두손가락의 각도


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        imageView = findViewById(R.id.image);
        showList = findViewById(R.id.showList);
        prev = findViewById(R.id.prevFloor);
        next = findViewById(R.id.nextFloor);

        Intent intent = getIntent();
        String floor = intent.getStringExtra("floor");
        String startPoint = intent.getStringExtra("startPoint");
        String name = intent.getStringExtra("name");
        String mode = intent.getStringExtra("mode");

        // Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child(name);

        if (pathReference == null) {
        }
        else {
            StorageReference submitProfile = storageReference.child(name + "/" +floor+".png");
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MapActivity.this).load(uri).into(imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "이미지 로딩에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }


        matrix = new Matrix();
        savedMatrix = new Matrix();

//        matrix.postTranslate(200, 200);
        matrix.postTranslate(236, 278);
        imageView.setImageMatrix(matrix);

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListCustomDialog customDialog = new ListCustomDialog(MapActivity.this, new ListCustomDialogClickListener(){
                    @Override
                    public void onCloseClick() {

                    }
                });
            customDialog.setCanceledOnTouchOutside(true);
            customDialog.setCancelable(true);
            customDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            customDialog.show();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                floor = Integer.toString(Integer.parseInt(floor) -1);
                StorageReference submitProfile = storageReference.child(name + "/" +floor+".png");
                submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(MapActivity.this).load(uri).into(imageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "이미지 로딩에 실패하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageView.setOnTouchListener(onTouch);
        imageView.setScaleType(ImageView.ScaleType.MATRIX); // 스케일 타입을 매트릭스로 해줘야 움직인다.

    }

    private View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.equals(imageView)) {
                int action = event.getAction();
                switch (action & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        touchMode = TOUCH_MODE.SINGLE;
                        donwSingleEvent(event);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (event.getPointerCount() == 2) { // 두손가락 터치를 했을 때
                            touchMode = TOUCH_MODE.MULTI;
                            downMultiEvent(event);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (touchMode == TOUCH_MODE.SINGLE) {
                            moveSingleEvent(event);
                        } else if (touchMode == TOUCH_MODE.MULTI) {
                            moveMultiEvent(event);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        touchMode = TOUCH_MODE.NONE;
                        break;
                }
            }
            return true;
        }
    };

    private PointF getMidPoint(MotionEvent e) {

        float x = (e.getX(0) + e.getX(1)) / 2;
        float y = (e.getY(0) + e.getY(1)) / 2;

        return new PointF(x, y);
    }

    private float getDistance(MotionEvent e) {
        float x = e.getX(0) - e.getX(1);
        float y = e.getY(0) - e.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
    private void donwSingleEvent(MotionEvent event) {
        savedMatrix.set(matrix);
        startPoint = new PointF(event.getX(), event.getY());
    }
    private void downMultiEvent(MotionEvent event) {
        oldDistance = getDistance(event);
        if (oldDistance > 5f) {
            savedMatrix.set(matrix);
            midPoint = getMidPoint(event);
            double radian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            oldDegree = (radian * 180) / Math.PI;
        }
    }
    private void moveSingleEvent(MotionEvent event) {
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
        imageView.setImageMatrix(matrix);
    }
    private void moveMultiEvent(MotionEvent event) {
        float newDistance = getDistance(event);
        if (newDistance > 5f) {
            matrix.set(savedMatrix);
            float scale = newDistance / oldDistance;
            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

            double nowRadian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            double nowDegress = (nowRadian * 180) / Math.PI;
            float degree = (float) (nowDegress - oldDegree);
            matrix.postRotate(degree, midPoint.x, midPoint.y);


            imageView.setImageMatrix(matrix);

        }
    }
}