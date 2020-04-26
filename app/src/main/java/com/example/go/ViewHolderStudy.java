package com.example.go;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ViewHolderStudy extends MyItemView{
    TextView studyname;
    TextView studyscore;

    DataType data;


    public ViewHolderStudy(@NonNull View itemView) {
        super(itemView);

        studyname = itemView.findViewById(R.id.studyView1);
        studyscore = itemView.findViewById(R.id.studyView2);
    }

    public void onBind(MyItem data){
        this.data = (DataType) data;
        studyname.setText(this.data.getStudyName());
        studyscore.setText(this.data.getStudyScore());
    }
}
