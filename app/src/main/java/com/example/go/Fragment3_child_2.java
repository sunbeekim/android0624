package com.example.go;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


//설문지 구현
public class Fragment3_child_2 extends Fragment{




    public static Fragment3_child_2 newInstance(){

        return new Fragment3_child_2();
    }
    Fragment3_child_2(){
    }
    String study1, study2, rgbutton, addedit1, fixedit2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    SuperAdapter adapter_survey2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final View fvchild2 = inflater.inflate(R.layout.fragment3_child_2, container, false);

        final Spinner spinner2, spinner3;
        Button survey2Complete;
        final TextView spinnerInt1, spinnerInt2;
        spinnerInt1 = (TextView)fvchild2.findViewById(R.id.spinnerInt1);
        spinnerInt2 = (TextView)fvchild2.findViewById(R.id.spinnerInt2);
        final EditText edit1, edit2;
        edit1 = fvchild2.findViewById(R.id.edit1);
        edit2 = fvchild2.findViewById(R.id.edit2);
        RadioGroup rg2 = (RadioGroup) fvchild2.findViewById(R.id.rg2);

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton select = (RadioButton)fvchild2.findViewById(checkedId);
                rgbutton = select.getText().toString();
            }
        });
        survey2Complete = (Button) fvchild2.findViewById(R.id.survey2Complete);
        spinner2 = (Spinner) fvchild2.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerInt1.setText(""+parent.getItemAtPosition(position));
                position = position+1;
                study1 = String.valueOf(position);
                //study1 = spinnerInt1.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3 = (Spinner) fvchild2.findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerInt2.setText(""+parent.getItemAtPosition(position));
                position = position+1;
                study2 = String.valueOf(position);
                //study2 = spinnerInt2.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        survey2Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedit1 = edit1.getText().toString();
                fixedit2 = edit2.getText().toString();
                Log.d("클릭 시", study1+"\t"+study2+"\t"+rgbutton+"\t"+addedit1+"\t"+fixedit2);

                String survey2result = UserInfo.userid+"--"+study1+"--"+study2+"--"+rgbutton+"--"+addedit1+"--"+fixedit2;
                try {

                    String sendmsg = "typeresult";
                    new ConnectDB(sendmsg).execute("typeresult", UserInfo.userid, UserInfo.user_typesum).get();
                    sendmsg = "survey2result";
                    new ConnectDB(sendmsg).execute("survey2result", survey2result).get();
                    UserInfo.survey_position = "2";

                }catch (Exception e){

                }

                fragmentRemove();
            }
        });





      return fvchild2;
    }



    private static void getData() {//설문 내용을 리사이클러뷰 아이템에 셋

    }
    private int maxCount() { //총 설문이 몇개인지 반환
        String sendmsg = "survey1";
        String result; //전체출력 result;
        String[] oj;
        int cut = 0;
        
        try{
            result  = new ConnectDB(sendmsg).execute("survey1").get();
            oj = result.split("\t"); //
            cut = oj.length;
            
            Log.d("maxcount", String.valueOf(cut));

        }catch (Exception e){
            e.printStackTrace();
        }
        return cut/2;
    }




    void fragmentRemove(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, Fragment3.newInstance()).commit();
    }
}



