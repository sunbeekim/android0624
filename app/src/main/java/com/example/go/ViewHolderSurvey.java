package com.example.go;

import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ViewHolderSurvey extends MyItemView{
    TextView surveyView1;
    TextView surveyNum;
    RadioGroup rg;
    DataType data;





    OnViewHolderRadioClickListener onViewHolderRadioClickListener;
    public ViewHolderSurvey(@NonNull final View itemView) {
        super(itemView);

        surveyView1 = itemView.findViewById(R.id.surveyView1);
        surveyNum = itemView.findViewById(R.id.surveyNum);

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



    public void onBind(MyItem data){
        this.data = (DataType) data;
        surveyView1.setText(this.data.getSurveyName());
        surveyNum.setText(this.data.getSurveyNum());
        final int count = this.data.getDefalt();
        final intchek check = new intchek(count);
        check.setCount(count);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String userid = mAuth.getCurrentUser().getUid();
        rg = (RadioGroup)itemView.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton select = (RadioButton)itemView.findViewById(checkedId);
                //select.isChecked();

                String s;
                String save = "";
                int count;
                count = check.getCount();
//포지션값 필요
                s = select.getText().toString();
                //Log.d("HolderSuvey RadioButton", s);
                if(s.equals("매우 그렇다")){
                    save += "1";
                }else if(s.equals("그렇다")){
                    save += "2";
                }else if(s.equals("그렇지 않다")){
                    save += "3";
                }else if(s.equals("매우 그렇지 않다")){
                    save += "4";
                }
                rg.getCheckedRadioButtonId();

                Log.d("String", save);
                Log.d("int", String.valueOf(count));
//                Log.d("userid", userid);
                String sendmsg = "survey1result";
                String result = save;
                String reuslt2 = String.valueOf(count);//자신이 보내고싶은 값을 보내시면됩니다

                try{
                    String rst = new ConnectDB(sendmsg).execute("survey1_result", userid ,result, reuslt2).get();
                }catch (Exception e){
                    e.printStackTrace();
                }
                onViewHolderRadioClickListener.onViewHolderRadioClickListener();
            }
        });

    }

    public void setOnViewHolderRadioClickListener(OnViewHolderRadioClickListener onViewHolderRadioClickListener){
        this.onViewHolderRadioClickListener = onViewHolderRadioClickListener;

    }
}
