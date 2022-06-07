package com.example.MartBee;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListCustomDialog extends Dialog {

    private Context context;
    private ListCustomDialogClickListener listCustomDialogClickListener;
    private Button closeBtn;

    public ListCustomDialog(@NonNull Context context, ListCustomDialogClickListener listCustomDialogClickListener) {
        super(context);
        this.context = context;
        this.listCustomDialogClickListener = listCustomDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_custom_dialog);

        closeBtn = findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCustomDialogClickListener.onCloseClick();
                dismiss();
            }
        });
    }
}
