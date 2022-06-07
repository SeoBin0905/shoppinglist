package com.example.MartBee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.BitmapDrawable;


import androidx.annotation.Nullable;

public class Loading extends View {

    ImageView imageView = findViewById(R.id.image);

    public Loading(Context context) {
        super(context);
        init();
    }

    private void Dijkstra() {

    }


    public void init() {
        Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.floor1);
        Canvas canvas = new Canvas(map);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.floor1);
//        canvas.drawBitmap(map, 200, 200, null);
//
//        Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
//        canvas.drawBitmap(marker, 200, 200, null);
//        canvas.restore();
////        canvas.drawBitmap(marker, xPositionFor2ndMarker, yPositionFor2ndMarker, null);
//
//        //Create a new image bitmap and attach a brand new canvas to it
//        Bitmap tempBitmap = Bitmap.createBitmap(map.getWidth(), map.getHeight(), Bitmap.Config.RGB_565);
//        Canvas tempCanvas = new Canvas(tempBitmap);
//
//        //Draw the image bitmap into the cavas
//        tempCanvas.drawBitmap(map, 0, 0, null);
//
//        //Draw everything else you want into the canvas, in this example a rectangle with rounded edges
//        tempCanvas.drawBitmap(marker, 200, 200, null);
//
//        //Attach the canvas to the ImageView
//        imageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }
}
