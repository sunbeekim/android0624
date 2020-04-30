package com.example.go;

import android.view.View;
import android.widget.TextView;

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

    public void onBind(MyItem data){
        this.data = (DataType) data;
        studyname.setText(this.data.getStudyName());
        studyadress.setText(this.data.getSudyadress());
        mytype.setText(this.data.getMytype());
        percent.setText(this.data.getPercent());
        clicknum.setText(this.data.getClicknum());
        //db에서 가져온 유형과 효율, 클릭 수 가져와서 셋팅
    }
}
