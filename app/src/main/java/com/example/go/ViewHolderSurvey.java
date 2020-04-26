package com.example.go;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ViewHolderSurvey extends MyItemView{
    TextView surveyView1;
    TextView surveyNum;
    RadioGroup rg;
    RadioButton rb1, rb2, rb3, rb4;
    DataType data;

    OnViewHolderRadioClickListener onViewHolderRadioClickListener;
    public ViewHolderSurvey(@NonNull View itemView) {
        super(itemView);

        surveyView1 = itemView.findViewById(R.id.surveyView1);
        surveyNum = itemView.findViewById(R.id.surveyNum);
        rg = (RadioGroup)itemView.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onViewHolderRadioClickListener.onViewHolderRadioClickListener();
            }
        });
    }



    public void onBind(MyItem data){
        this.data = (DataType) data;
        surveyView1.setText(this.data.getSurveyName());
        surveyNum.setText(this.data.getSurveyNum());
    }

    public void setOnViewHolderRadioClickListener(OnViewHolderRadioClickListener onViewHolderRadioClickListener){
        this.onViewHolderRadioClickListener = onViewHolderRadioClickListener;
    }
}
