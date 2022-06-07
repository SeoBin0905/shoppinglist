package com.example.MartBee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter searchAdapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = (EditText) findViewById(R.id.nameSearch);
        listView = (ListView) findViewById(R.id.listView);

        // 리스트를 생성
        list = new ArrayList<String>();

        // 검색에 사용할 데이터을 미리 저장
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사, list 복사본 생성
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        searchAdapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(searchAdapter);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출
                // search 메소드를 호출
                String text = editSearch.getText().toString();
                search(text);
            }
        });

//        startingPointRadio();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String value = Integer.toString(position);
                String value = list.get(position);
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                intent.putExtra("name", value);
                startActivity(intent);
            }
        });
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        searchAdapter.notifyDataSetChanged();
    }

    // 목록
    // position : list 내 인덱스
    private void settingList() {
        list.add("롯데마트 월드컵점");
        list.add("NC웨이브 충남점");

    }

    // 엘리베이터 / 에스컬레이터 출발 지점 지정
//    public void startingPointRadio() {
//        // 초기 배열리스트 및 초기화 목록
//        String Set[] = {"엘리베이터", "에스컬레이터"};
//        final int[] idx = {0};
//        String title = "출발 지점";
//        // radio 버튼 이름 정의
//        String no = "취소";
//        String yes = "확인";
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                // AlertDialog
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle(title)
//                        .setCancelable(true)
//                        .setSingleChoiceItems(Set, 0, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // 실시간으로 변경된 인덱스 값 확인
//                                idx[0] = which;
//                            }
//                        })
//                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (idx[0] == 0) {
//                                    Toast.makeText(getApplication(), Set[0] + "에서 출발합니다", Toast.LENGTH_SHORT).show();
//                                }
//                                else if (idx[0] == 1) {
//                                    Toast.makeText(getApplication(), Set[1] + "에서 출발합니다", Toast.LENGTH_SHORT).show();
//                                }
//
//                                if(position == 0) {
//                                    Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
//                                    intent.putExtra("position", position);
//                                    intent.putExtra("startingPoint", Set[idx[0]]);
//                                    startActivity(intent);
//                                }
//                            }
//                        })
//                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .show();
//
//            }
//        });
//    }
}