package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




//설문지 구현
public class Fragment3_child extends Fragment implements View.OnClickListener{


    private static SuperAdapter adapter_survey;

    public static Fragment3_child newInstance(){

        return new Fragment3_child();
    }
    Fragment3_child(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    TextView textView999;
    //SuperAdapter adapter_survey;
    Context context;
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View fvchild = inflater.inflate(R.layout.fragment3_child, container, false);

        context = container.getContext(); //이 프래그먼트가 가진 컨테이너의 포괄적인 내용을 담음
        final TextView textView = (TextView)fvchild.findViewById(R.id.spinnertext);

        Spinner spinner = (Spinner)fvchild.findViewById(R.id.spinner);

        Button surveyComplete, btn_person;
        surveyComplete = (Button) fvchild.findViewById(R.id.surveyComplete);
        btn_person = (Button)fvchild.findViewById(R.id.person);

        surveyComplete.setOnClickListener(this);
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



        //==================================================================================// 코드 정리 필요 어댑터 만들어야함
        //radiobutton 값 반환 후 db 입력
        //설문db의 row 값만큼 자동생성


//=======================================================================================================//

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//스피너
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("선택 된 유형 ==> " + parent.getItemAtPosition(position)); //선택 된 포지션의 아이템 셋
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.spinner = spinner;

      return fvchild;
    }
//https://youngest-programming.tistory.com/71 리사이클러뷰 고정, 메모리 효율은 좋지 안음

  //리사이클러뷰 참고  https://lakue.tistory.com/16
    private static void getData() {//설문 내용을 리사이클러뷰 아이템에 셋
        String sendmsg = "survey1";
        String result; //전체출력 result;
        String[] oj;
        int cut;
        
        try{
            result  = new ConnectDB(sendmsg).execute("survey1").get();//디비값을 가져오기
            oj = result.split("\t"); //db에 설문을 추가하면 row만큼 리사이클러뷰 생성
            cut = oj.length;

            int j = 1;
            for(int i = 0; i < cut/2; i++) {
                DataType data = new DataType(i+1,""+j+"번", oj[i + i + 1]);
                adapter_survey.addItem(data);
                j++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
//===========================================================================//
    @Override
    public void onClick(View v) {
//utgoing transactions from this process must be FLAG_ONEWAY 에러


        switch (v.getId()){
            case R.id.surveyComplete: //작성완료 버튼
                Log.d("surveyResultCount() ", surveyResultCount() +"");
                Log.d("axCount() ", maxCount() +"");
                if(surveyResultCount() == maxCount()) {
                    //체크포인트 db 삽입
                    //surveyCheck();



                    type();
                    //유형 삽입
                    UserInfo.survey_position = "1";
                    fragmentRemove();

                    break;
                }else{
                getresult();
                    // commit();
                }
                break;
            case R.id.person: //성격검사버튼
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ko"));
                startActivity(intent);
                break;
        }

    }

    //===========================================================================//
    private int surveyResultCount() {
    //현재 설문 된 행 개수 반환

        String sendmsg = "survey1rc";
        String result;
        int cur = 0;
        String[] oj;
        
        try{
            result  = new ConnectDB(sendmsg).execute("survey1rc", UserInfo.respon).get();//입력 된 설문 행 수 가져오기

            oj = result.split("\t");
            cur = oj.length;

        }catch (Exception e){
            e.printStackTrace();
        }

        return cur-1;
    }


    void fragmentRemove(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, Fragment3.newInstance()).commit();
    }


    private void getresult() {
    //설문 값을 불러와서 빈 곳이 있는지 체크하고 포지션 알림

        String sendmsg = "responcount";
        String result = "";

        String[] oj;
        int cut = maxCount()*2;//24
        int cut2 = maxCount(); //12
        String[] check = new String[cut];
//        Log.d("userid =============>", userid);
        UserInfo.respon = UserInfo.userid+"1";
        try{
            result  = new ConnectDB(sendmsg).execute("responcount", UserInfo.respon).get();
            oj = result.split("\t"); //\t를 기준으로 분할
            for(int i = 0; i < oj.length/2; i++){
                oj[i] = oj[i].replaceAll("\t", "");
                if(i < oj.length) {
                    check[i] = oj[i+i+1];
                }else{
                    break;
                }
                Log.d("check", (i+1)+"'"+check[i]+"'");
            }

            for(int i = 0; i < cut2; i++){

               if((String.valueOf((i+1))).equals(check[i])){
                    Log.d("same value", (i+1)+"");
               }else{
                   Toast.makeText(context, (i+1)+" 번째를 체크해주세요.",Toast.LENGTH_SHORT).show();
                   break;
               }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void type() {


        //db에서 값 가져오고 게산 후 유형 전달
        //설문을 했다는 인증코드 db에 입력
        //총체적 = + , 분석적 = -   // -1 or 1
        //자기지향 = + , 사회지향 = -  // -1 or 1
        //4가지 유형 a(-1, -1), b(-1, 1), c(1, -1), d(1, 1)
        //분류 first abcd
        //직관적 = + , 연역적 = -  // -1 or 1
        //좌뇌형 = + , 우뇌형 = -  //-1 or 1
        //4가지 유형 A(-1, -1), B(-1, 1), C(1, -1), D(1, 1)
        //분류 second ABCD
        //경우의 수 (a, b, c, d) * (A, B, C, D) = 16가지
        //16가지의 유형에 성격검사를 통한 4가지 대분류 타입의 조합 = 64가지 유형
        //16가지의 유형에 성격검사를 통한 16가지 세세분류 타입의 조합 = 256가지 유형
        //1개의 질문 값은 +1 or -1로 나오며 3개를 합치면 sample1 = +1 or sample2 = -1
        //질문의 개수 별 단위 : 1(질문단위), 3(타입 단위), 6(소형 유형 단위 - 4가지 유형) 12(중형 유형 단위 - 16가지 유형)
        // 중요 - 설문 추가 시 3개씩 추가(1번 긍정문, 2번 부정문, 3번 긍정문)
        String sendmsg = "responcount";
        String result ;
        int six = 6; //설문은 3개(설문넘버 3개, 설문넘버 3개 =  6
        int cut = maxCount()*2;//24
        int dev = cut / six;
        int tran = 0;
        int resultsample[] = new int[4];
        int sample = 0;
        String type1 = null;
        String type2 = null;
        String type3 ;
        String typesum;
        Log.d("userid =============>", UserInfo.userid);
        String[] oj;


        try{
            Log.d("respon ====>", UserInfo.respon);
            //UserInfo.respon = UserInfo.userid+"1";
            result  = new ConnectDB(sendmsg).execute("responcount", UserInfo.respon).get();//id를 조건으로 servey1result 테이블 값 가져오기
            oj = result.split("\t"); //\t를 기준으로 분할
            for(int c = 1; c <= cut/2; c++){
                Log.d("oj count 값은 : ", oj[c+c-1]);
                Log.d("oj save 값은 : ", oj[c+c]);
            }
            for(int j = 0; j < dev; j++){
                sample = 0;
                for(int i = (j*six)/2+1; i <= (six * (j+1))/2; i++){
//                    if(i !=0)
//                        Log.d("======????", oj[i+i-1]);
//                    if(i == 11)
//                        Log.d("======????", oj[i+i+1]);

                    if(tran == 0){
                        if(oj[i+i].equals("3") || oj[i+i].equals("4")){ //save 값이 2 초과
                            sample += -1; //분석적, 자기지향, 직관적, 좌뇌형
                            Log.d("oj========>  ", 1 + "번 " + sample);
                            Log.d("oj========> ", oj[i+i]);
                        }else if(oj[i+i].equals("1") || oj[i+i].equals("2")){
                            sample += +1; //총체적, 사회지향, 연역적, 우뇌형
                            Log.d("oj========>  ", 2 + "번 " + sample); //?? 무슨 문제?
                            Log.d("oj========> ", oj[i+i]);
                        }
                        tran = 1;
                    }else{
                        if(oj[i+i].equals("1") || oj[i+i].equals("2")){ //save 값이 3 미만
                            sample += -1; //분석적, 자기지향, 직관적, 좌뇌형
                            Log.d("oj========>  ", 3 + "번 " + sample);
                            Log.d("oj========> ", oj[i+i]);
                        }else if(oj[i+i].equals("3") || oj[i+i].equals("4")){
                            sample += +1; //총체적, 사회지향, 연역적, 우뇌형
                            Log.d("oj========>  ", 4 + "번 " + sample);
                            Log.d("oj========> ", oj[i+i]);
                        }
                        tran = 0;
                    }
                    if(i == ((six * (j+1))/2)){ // ex) i ==(6 * (3+1)) / 2 // i == 12
                        int change;
                        change = sample;
                        if(change>0){
                            change = 1;
                        }else{
                            change = -1;
                        }
                        resultsample[j] = change; //[i = 0] = 3,[i = 1] = 6, [i = 2] = 9, [i = 3] = 12

                        Log.d("result!!!!!!", (i+1) +" "+ resultsample[j]);
                    }
                }

            }
//===========================================================================================//
            //아래 코드도 위와 같이 db에서 몇개의 값을 가져와도 자동계산 되게 만들어야 함
            if(resultsample[0] == 1 && resultsample[1] == 1){
                type1 = "As";//분석적 자기지향
            }else if(resultsample[0] == 1 && resultsample[1] == -1){
                type1 = "Ac";//분석적 사회지향
            }else if(resultsample[0] == -1 && resultsample[1] == 1){
                type1 = "Ws";//총체적 자기지향
            }else if(resultsample[0] == -1 && resultsample[1] == -1){
                type1 = "Wc";//총체적 사회지향
            }
//===========================================================================================//
            if(resultsample[2] == 1 && resultsample[3] == 1){
                type2 = "ILb";//직관적 좌뇌형
            }else if(resultsample[2] == 1 && resultsample[3] == -1){
                type2 = "IRb";//직관적 우뇌형
            }else if(resultsample[2] == -1 && resultsample[3] == 1){
                type2 = "DLb";//연역적 좌뇌형
            }else if(resultsample[2] == -1 && resultsample[3] == -1){
                type2 = "DRb";//직관적 우뇌형
            }
            type3 = (String)spinner.getSelectedItem();
            UserInfo.usertypesum = type1 + type2 + type3;
            UserInfo.type1 = type1;
            UserInfo.type2 = type2;
            UserInfo.type3 = type3;
            UserInfo.user_typesum = type1 +"-" + type2 + "-" + type3;
            typesum = type1 +"-" + type2 + "-" + type3;
            Log.d("typesum", typesum+UserInfo.userid);
            sendmsg = "typeresult";
            result  = new ConnectDB(sendmsg).execute("typeresult", UserInfo.userid, typesum).get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}



