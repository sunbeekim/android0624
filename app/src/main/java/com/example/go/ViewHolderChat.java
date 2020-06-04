package com.example.go;


import android.view.View;
import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.annotation.NonNull;



public class ViewHolderChat extends MyItemView{
    TextView username, chatcontext, chatdate, chatcontextme, chatdateme, userid;
    LinearLayout yourchat, mechat;
    DataType data;



    public ViewHolderChat(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.userName);
        chatcontext = itemView.findViewById(R.id.chatContext);
        chatdate = itemView.findViewById(R.id.chatDate);
        chatcontextme = itemView.findViewById(R.id.chatContextMe);
        chatdateme = itemView.findViewById(R.id.chatDateMe);
        yourchat = itemView.findViewById(R.id.yourchat);
        mechat = itemView.findViewById(R.id.mechat);
        userid = itemView.findViewById(R.id.userid);

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





    public void onBind(MyItem data) {
        this.data = (DataType) data;

        username.setText(this.data.getUserName());
        chatcontext.setText(this.data.getChatContext());
        chatdate.setText(this.data.getChatDate());
        chatcontextme.setText(this.data.getChatContext());
        chatdateme.setText(this.data.getChatDate());
        userid.setText(this.data.getUserid());
        int count = this.data.getCount();


        String userid2 = this.data.getUserid();

        if(UserInfo.userid.equals(userid2)){
           yourchat.setVisibility(View.GONE);
        }else{
            mechat.setVisibility(View.GONE);
        }

                //userid == myid mechat = GONE
                //else yourchat = GONE

    }

}
