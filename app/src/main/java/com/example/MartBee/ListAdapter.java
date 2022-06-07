package com.example.MartBee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {

    private ArrayList<ListNote> listNoteArrayList;
    private Context context;

    public ListAdapter(ArrayList<ListNote> listNoteArrayList, Context context) {
        this.listNoteArrayList = listNoteArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.category.setText(listNoteArrayList.get(position).getShoppingList());
    }

    @Override
    public int getItemCount() {
        return (listNoteArrayList != null ? listNoteArrayList.size() : 0);    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView category;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.category = itemView.findViewById(R.id.category);
        }
    }
}
//    private static final String TAG = "ListAdapter";
//    private Context context;
//    ArrayList<ListNote> items = new ArrayList<>();
//
//    public ListAdapter(ArrayList<ListNote> items, Context context) {
//        this.items = items;
//        this.context = context;
//    }
//
//    // xml 적용
//    @NonNull
//    @Override
//    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView = inflater.inflate(R.layout.item_list, parent, false);
//
//        return new ViewHolder(itemView);
//    }
//
//    // 각 항목에 들어갈 데이터
//    @Override
//    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
//        ListNote item = items.get(position);
//        holder.setItem(item);
//        holder.setLayout();
//    }
//
//    // 화면에 보여줄 데이터 개수
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        LinearLayout layout;
//        CheckBox checkBox;
//        Button deleteBtn;
//
//        // 뷰홀더 객체에 저장되어 화면에 표시
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            this.layout = itemView.findViewById(R.id.layoutList);
//            this.checkBox = itemView.findViewById(R.id.listCheckBox);
//            this.deleteBtn = itemView.findViewById(R.id.deleteListBtn);
//
//            deleteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String list = (String) checkBox.getText();
//                    deleteItem(list);
//                    Toast.makeText(v.getContext(), "삭제되었습니다.",Toast.LENGTH_SHORT).show();
//                }
//            private void deleteItem(String item) {
//
//                }
//            });
//
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "체크 완료", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        //EditText에서 입력받은 checkBox의 텍스트를 checkBox의 Text에 넣을 수 있게 하는 메서드
//        public void setItem(ListNote item) {
//            checkBox.setText(item.getShoppingList());
//        }
//        //아이템들을 담은 LinearLayout을 보여주게하는 메서드
//        public void setLayout() {
//            layout.setVisibility(View.VISIBLE);
//        }
//    }
//
//    //배열에 있는 item들을 가리킴
//    public void setItems(ArrayList<ListNote> items) {
//        this.items = items;
//    }