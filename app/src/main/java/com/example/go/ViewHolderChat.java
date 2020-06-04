package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class ViewHolderRoom extends MyItemView{
    TextView chatName, lastContext, userCount;

    DataType data;



    public ViewHolderRoom(@NonNull View itemView) {
        super(itemView);

        chatName = itemView.findViewById(R.id.chatName);
        lastContext = itemView.findViewById(R.id.lastContext);
        userCount = itemView.findViewById(R.id.userCount);


    }

    class  intchek{ // survey position 값 중복방지
        int count;
        intchek(int count){
            this.count = count;
        }
        public int getCount(){
            return this.count;
        }

        public void setCount(int count){
            this.count = count;
        }
    }
    private String checkSurvey() {
    String checkSurvey = null;

    return checkSurvey;
    }
    OnViewHolderRoomClickListener onViewHolderRoomClickListener = null;

    public void setOnViewHolderRoomClickListener(OnViewHolderRoomClickListener onViewHolderRoomClickListener) {
        this.onViewHolderRoomClickListener = onViewHolderRoomClickListener;
    }

    public void onBind(MyItem data) {
        this.data = (DataType) data;
        chatName.setText(this.data.getChatName());
        lastContext.setText(this.data.getChatLastContext());
        userCount.setText(String.valueOf(this.data.getUserCount()));




        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String userid = mAuth.getCurrentUser().getUid();

        itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                onViewHolderRoomClickListener.onViewHolderRoomClickListener(v);
                Context context = v.getContext();
                //chatid 받아서 넘겨주기
                String chatid = "VKM2QKLOHGMRRN1WXJVQBIMN4CC21";

            }
        });
    }

}
