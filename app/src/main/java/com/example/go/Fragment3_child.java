package com.example.go;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

//설문지 구현
public class Fragment3_child extends Fragment implements View.OnClickListener{

    public static Fragment3_child newInstance(){
        return new Fragment3_child();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    TextView textView999;
    SuperAdapter adapter_survey;
    RadioGroup rg;
    RadioButton rb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View fvchild = inflater.inflate(R.layout.fragment3_child, container, false);
        final TextView textView = (TextView)fvchild.findViewById(R.id.spinnertext);
        Spinner spinner = (Spinner)fvchild.findViewById(R.id.spinner);
        Button button, btn_person;
        button = (Button) fvchild.findViewById(R.id.button);
        btn_person = (Button)fvchild.findViewById(R.id.person);

        button.setOnClickListener(this);
        btn_person.setOnClickListener(this);
        TextView textView999 = fvchild.findViewById(R.id.textView999);
        this.textView999 = textView999;




        RecyclerView recyclerView = fvchild.findViewById(R.id.surveyRecycle);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter_survey = new SuperAdapter(CaseSelected.SURVEY);
        recyclerView.setAdapter(adapter_survey);
        getData(); //db





        //adapter.addItem(edtit.getText().toString()); //버튼을 클릭하면 adapter에 추가 방식(관리자모드)

        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://123.100.189.65:8088/WebTest/androidDB.jsp"));
        //startActivity(intent);
        //==================================================================================// 코드 정리 필요 어댑터 만들어야함
        //textview, radiogroup, radiobutton 등을 xml
        //radiobutton 값 반환 후 db 입력
        //설문db의 row 값만큼 자동생성


//=======================================================================================================//

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//스피너
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("선택 된 유형 ==> " + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      return fvchild;
    }
  //리사이클러뷰 참고  https://lakue.tistory.com/16
    private void getData() {
        String sendmsg = "vision_list";
        String result; //전체출력 result;
        String[] oj;
        try{
            result  = new ConnectDB(sendmsg).execute("vision_list").get();//디비값을 가져오기
            oj = result.split("\t"); //db에 설문을 추가하면 row만큼 리사이클러뷰 생성
            int cut = oj.length;
            int j = 1;
            for(int i = 0; i < cut/2; i++) {
                DataType data = new DataType(i,""+j+"번째", oj[i + i + 1]);
                adapter_survey.addItem(data);
                j++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button: //작성완료 버튼
                //textView999.setVisibility(GONE);


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(Fragment3_child.this).commit();
                fragmentManager.popBackStack();
                Fragment3 fragment3 = new Fragment3();
                fragmentManager.beginTransaction().replace(R.id.container, fragment3.newInstance()).commit();
                break;
            case R.id.person: //성격검사버튼
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko"));
                startActivity(intent);
                break;
        }
    }

}
