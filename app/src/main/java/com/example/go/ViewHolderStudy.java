package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;



public class ViewHolderStudy extends MyItemView{
    TextView studyname; //학습법
    TextView studyadress;
    TextView mytype; //유형
    TextView studyscore; //효율
    TextView percent; //퍼센트
    TextView clickname; //조회
    TextView clicknum; //조회 수


    DataType data;


    public ViewHolderStudy(@NonNull View itemView) {
        super(itemView);

        studyname = itemView.findViewById(R.id.studyView1);
        studyadress = itemView.findViewById(R.id.studyadress);
        mytype = itemView.findViewById(R.id.mytype);
        studyscore = itemView.findViewById(R.id.studyView2); // 안 쓸수도 있는데 일단 놔둬봄
        percent = itemView.findViewById(R.id.percent);
        clickname = itemView.findViewById(R.id.studyView3); // "
        clicknum = itemView.findViewById(R.id.clicknum);


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
    OnViewHolderStudyClickListener onViewHolderStudyClickListener = null;
    public void onBind(MyItem data) {
        this.data = (DataType) data;
        studyname.setText(this.data.getStudyName());
        studyadress.setText(this.data.getStudyadress());
        mytype.setText(this.data.getMytype());
        percent.setText(this.data.getPercent());
        clicknum.setText(this.data.getClicknum());
        final int count = this.data.getDefalt2();
        TextView adressView = (TextView)itemView.findViewById(R.id.studyadress);

        final intchek check = new intchek(count);
        check.setCount(count);


        final String adress = adressView.getText().toString();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewHolderStudyClickListener.OnViewHolderStudyClickListener();
                Context context = v.getContext();

                try {
                    String sendmsg;
                    String rst;
                    String scount;
                    int count;
                    count = check.getCount();
                    scount = String.valueOf(count);
                    Log.d("count",scount);
                    sendmsg = "typeget";
                    rst = new ConnectDB(sendmsg).execute("typeget", UserInfo.userid).get();
                    rst = rst.replaceAll("\t","");
                    Log.d("rst", rst);
                    if (rst.equals("null")) {
                        Toast.makeText(context,"설문을 먼저 해주세요.", Toast.LENGTH_LONG).show();

                    }else{
                        sendmsg = "log";
                        new ConnectDB(sendmsg).execute("log", UserInfo.respon, rst, scount).get();
                        Log.v("주소",adress);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(adress+""));
                        context.startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


        //db에서 가져온 유형과 효율, 클릭 수 가져와서 셋팅



    public void setOnViewHolderStudyClickListener(OnViewHolderStudyClickListener onViewHolderStudyClickListener) {
        this.onViewHolderStudyClickListener = onViewHolderStudyClickListener;
    }
}
