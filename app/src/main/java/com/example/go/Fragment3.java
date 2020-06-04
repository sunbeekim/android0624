package com.example.go;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;

//tab3 서비스 구현
public class Fragment3 extends Fragment implements View.OnClickListener {

    public static Fragment3 newInstance() {
        return new Fragment3();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //https://nuggy875.tistory.com/7 시간 구하기
    //https://d4emon.tistory.com/60
    SuperAdapter adapter_study;
    RecyclerView recyclerView;
    FrameLayout frameLayout;
    Button survey, surveycom, survey2;
    //변경사항이 있을 때만 db출력 후 변경?
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv3 = inflater.inflate(R.layout.fragment3, container, false);


        survey = (Button) fv3.findViewById(R.id.survey);
        survey.setOnClickListener(this);
        surveycom = (Button) fv3.findViewById(R.id.surveycom);
        survey2 = (Button) fv3.findViewById(R.id.survey2);
        survey2.setOnClickListener(this);
        recyclerView = fv3.findViewById(R.id.recyclerView);

        //frameLayout = (FrameLayout)fv3.findViewById(R.id.fragment3child);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter_study = new SuperAdapter(CaseSelected.STUDY);

        recyclerView.setAdapter(adapter_study);
       // if(UserInfo.survey_position == null) {
            if (getSurveyCechk().equals("")) {
                nulldata();
            } else {
                getData();
            }
       // }
        if(UserInfo.usertypesum != null) {
            if (getcheck() == 1) {
                surveycom.setVisibility(GONE);
                survey2.setVisibility(View.VISIBLE);
            }
        }

        return fv3;
    }


    private void nulldata() {

        String sendmsg = "studylist";
        String result;
        String oj[];
        int cut;
        try{
            result = new ConnectDB(sendmsg).execute("studylist").get();//디비값을 가져오기
            oj = result.split("\t"); //db에 설문을 추가하면 row만큼 리사이클러뷰 생성
            cut = oj.length;
            int j = 1;
            for (int i = 0; i < cut / 2; i++) {
                DataType data = new DataType(j, oj[i + j], oj[i + j + 1], "나의 유형 : " + j, "empty", "empty");
                adapter_study.addItem(data);
                j++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getcheck() {//수정필요
        int icheck = 0;
        String today = null;
        try {


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH:mm"); //데이터형식지정
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);// 현재시간 가져오기

            Calendar cal = Calendar.getInstance();
            cal.setTime(mDate);////---------
            //cal.add(Calendar.MONTH, +5);
            cal.add(Calendar.MINUTE, -1); //-30분
            today = simpleDateFormat.format(cal.getTime());

//========================================//
            String oj[];
            String sendmsg = "surveycheck"; //TYPERESULT에서 포지션과 날짜 가져오기
            String rst = new ConnectDB(sendmsg).execute("surveycheck", UserInfo.userid).get();

                oj = rst.split("--");
                String form2 = oj[1].replaceAll("\t", "");

                form2 = form2.replaceAll("\t", "");
                Date mDate2 = simpleDateFormat.parse(form2);
                cal.setTime(mDate2);
                SimpleDateFormat newdate = new SimpleDateFormat("yyyy.MM.dd.HH:mm");
                mDate = newdate.parse(today);
                newdate.format(mDate);
                newdate.format(mDate2);

                sendmsg = "typeget";
                rst = new ConnectDB(sendmsg).execute("typeget", UserInfo.userid).get();
                rst = rst.replaceAll("\t", "");

                Log.d("날짜 1 ", "" + mDate); //현재시간 - 30분
                Log.d("날짜 2 ", "" + mDate2); //마지막 설문 작성 시간
                int compare = mDate.compareTo(mDate2); //현재시간 > 설문시간 실행  8:41-30= 8:11 > 8:10 = 양수
                Log.d("compare : ", compare+"");
                if (compare > 0) { //compare이 양수라면 1을 반환
                    icheck = 1;
                } else {
                    icheck = 0;//설문 필요
                }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return icheck;
    }

    private String getSurveyCechk() {

        String sendmsg2 = "typeget";
        String result2 = null;
        String[] oj;
        try {
            result2 = new ConnectDB(sendmsg2).execute("typeget", UserInfo.userid).get(); //typesum 가져오기
            oj = result2.split("--");
            oj[0] = result2.replaceAll("\t", "");
//            oj[1] = result2.replaceAll("\t", "");
            result2 = oj[0];

            Log.d("rest", result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result2;
    }

    private void getData() {

        String sendmsg = "studylist";
        String sendmsg2 = "typeget";
        String sendmsg3 = "type_sum";
        String sendmsg4 = "type_questions";
        String sendmsg5 = "type_log";
        String result;
        String result2;
        String result3;
        String result4;
        String result5;
        String spercent;
        String[] oj;
        String[] oj4;
        String[] oj5;
        int cut = 0;
        int cut4 = 0;
        int cut5 = 0;
        float percent = 0;
        int j = 1;
        String de;
        try {
            result = new ConnectDB(sendmsg).execute("studylist").get();//디비값을 가져오기
            oj = result.split("\t"); //db에 설문을 추가하면 row만큼 리사이클러뷰 생성
            cut = oj.length;

            result3 = new ConnectDB(sendmsg3).execute("type_sum", UserInfo.usertypesum).get(); //유형의 총 추천 수
            result3 = result3.replaceAll("\t", "");
            result4 = new ConnectDB(sendmsg4).execute("type_questions", UserInfo.usertypesum, String.valueOf(j)).get();//학습법 각 추천 수
            result4 = result4.replaceAll("\t", "");
            oj4 = result4.split("--");
            cut4 = oj4.length;
            result5 = new ConnectDB(sendmsg5).execute("type_log", UserInfo.usertypesum, String.valueOf(j)).get();//유형의 학습법 각 클릭수
            result5 = result5.replaceAll("\t", "");
            oj5 = result5.split("--");
            cut5 = oj5.length;
            Log.d("return3", result3);
            if(!result3.equals("null")){
                survey.setVisibility(GONE);
                surveycom.setVisibility(View.VISIBLE);
            }else{
                survey.setVisibility(View.VISIBLE);
                surveycom.setVisibility(GONE);
            }
            for (int i = 0; i < cut / 2; i++) {

                Log.d("return4", result4);
                if(UserInfo.usertypesum == null){
                    DataType data = new DataType(i + 1, oj[i + j], oj[i + j + 1], "나의 유형 :  " + "empty " +"   surveycount : "+ "empty", "★", " ★");
                    adapter_study.addItem(data);
                }else {
                    if(i < cut4){
                        float a = Float.parseFloat(oj4[i]);
                        float b = Float.parseFloat(result3);
                        percent = (a / b) * 100;
                        spercent = String.format("%.2f", percent);
                    }else{
                        spercent = "★";
                    }
                    if(i < cut5){
                        de = oj5[i];
                    }else{
                        de = "★";
                    }


                    DataType data = new DataType(i + 1, oj[i + j], oj[i + j + 1], "나의 유형 : " + UserInfo.usertypesum+"   surveycount : "+ UserInfo.survey_position, spercent, de);
                    adapter_study.addItem(data);
                }

                Log.d("percent", " " + percent);

                //1 ,3, 5
                //2, 4, 6
                j++;
                //퍼센트 클릭수 배열에 넣고 비교하고 퍼센트 포지션값 저장할 변수
                adapter_study.notifyDataSetChanged();//-
                //https://lakue.tistory.com/35?category=853542 url이미지 불러오기
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fg, fg2;
        switch (v.getId()) {
            case R.id.survey:
                recyclerView.setVisibility(GONE);
                v.setVisibility(GONE); //뷰 안보이기(영역도 없앰)
                Toast.makeText(getContext(), "설문지 작성", Toast.LENGTH_SHORT).show();
                fg = Fragment3_child.newInstance();
                setChildFragment(fg);
//                if(UserInfo.survey_position.equals("")){
//                    UserInfo.survey_position = "1";
//                }
                break;
            case R.id.survey2:
                recyclerView.setVisibility(GONE);
                v.setVisibility(GONE); //뷰 안보이기(영역도 없앰)
                //frameLayout.setVisibility(GONE);
                //frameLayout2.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "설문지 작성", Toast.LENGTH_SHORT).show();
                fg2 = Fragment3_child_2.newInstance();
                setChildFragment(fg2);
                //UserInfo.survey_position = "2";
                break;
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childF3 = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childF3.replace(R.id.fragment3child, child); //프레임레이아웃
            childF3.addToBackStack(null);
            childF3.commit();
        }
//        isAdded()를 사용하여 Fragment가 존재하는 지 확인 후 작업.
//        FragmentTransaction 에 add(int containerViewId, Fragment, fragment) 를 사용하면
//        버튼을 누를 때마다 계속 Fragment가 생겨나서
//        replace(int containerViewId, Fragment, fragment) 하여 container가 재사용되도록 했다.
//        addToBackStack(String name)을 호출하면 생성하는 childFragment 들이 차곡차곡 쌓여
//        back 버튼을 누를 때마다 이전 Fragment로 순차적으로 돌아간다.
    }

}
