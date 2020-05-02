package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv3 = inflater.inflate(R.layout.fragment3, container, false);

        Button survey, surveycom;
        survey = (Button) fv3.findViewById(R.id.survey);
        survey.setOnClickListener(this);
        surveycom = (Button) fv3.findViewById(R.id.surveycom);

        recyclerView = fv3.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter_study = new SuperAdapter(CaseSelected.STUDY);

        recyclerView.setAdapter(adapter_study);
        if (getSurveyCechk().equals("null")) {
            nulldata();
        } else{
            //nulldata();
            getData();
        }

        //자동화 필요 Fragment3_child 참고
//        if(getcheck(fv3)==1){
//            survey.setVisibility(View.GONE);
//            surveycom.setVisibility(View.VISIBLE);
//
//            Log.d("dddd", getcheck(fv3)+"");
//        }else if(getcheck(fv3)==0){
//            //Toast.makeText(getContext(),"설문을 먼저 해주세요.",Toast.LENGTH_LONG).show();
//            surveycom.setVisibility(View.GONE);
//            survey.setVisibility(View.VISIBLE);
//        }


        return fv3;
    }

    private void nulldata() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String userid = mAuth.getCurrentUser().getUid();
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
                DataType data = new DataType(j, oj[i + j], oj[i + j + 1], "나의 유형 : " + j, null, null);
                adapter_study.addItem(data);
                j++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getcheck(View v) {
        int icheck = 0;
        try {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            final String userid = mAuth.getCurrentUser().getUid();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.mm.dd.hh:mm"); //데이터형식지정
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);// 현재시간 가져오기

            Calendar cal = Calendar.getInstance();
            cal.setTime(mDate);////---------
            cal.add(Calendar.MINUTE, -1); //-30분


//========================================//
            String oj[];
            String sendmsg = "surveycheck";
            String rst = new ConnectDB(sendmsg).execute("surveycheck", userid).get();
            oj = rst.split("--");
            String form2 = oj[1].replaceAll("\t", "");

            form2 = form2.replaceAll("\t", "");
            Date mDate2 = simpleDateFormat.parse(form2);
            simpleDateFormat.format(mDate);
            simpleDateFormat.format(mDate2);

            sendmsg = "typeget";
            rst = new ConnectDB(sendmsg).execute("typeget", userid).get();
            rst = rst.replaceAll("\t", "");

            Log.d("날짜 1 ", "" + mDate); //현재시간 - 30분
            Log.d("날짜 2 ", "" + mDate2); //마지막 설문 작성 시간
            int compare = mDate.compareTo(mDate2);
            if (rst != null || compare > 0 || form2 == null) {
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
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final String userid = mAuth.getCurrentUser().getUid();
        String sendmsg2 = "typeget";
        String result2 = null;
        try {
            result2 = new ConnectDB(sendmsg2).execute("typeget", userid).get(); //typesum 가져오기
            result2 = result2.replaceAll("\t", "");
            Log.d("rest", result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result2;
    }

    private void getData() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String userid = mAuth.getCurrentUser().getUid();
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
        int cut;
        float percent = 0;
        int j = 1;
        try {
            result = new ConnectDB(sendmsg).execute("studylist").get();//디비값을 가져오기
            oj = result.split("\t"); //db에 설문을 추가하면 row만큼 리사이클러뷰 생성
            cut = oj.length;
            result2 = new ConnectDB(sendmsg2).execute("typeget", userid).get(); //typesum 가져오기
            result2 = result2.replaceAll("\t", "");
            Log.d("rest", result2);


            result3 = new ConnectDB(sendmsg3).execute("type_sum", result2).get();
            result3 = result3.replaceAll("\t", "");

            Log.d("return2", result2);
            Log.d("return3", result3);

            for (int i = 0; i < cut / 2; i++) {
                result4 = new ConnectDB(sendmsg4).execute("type_questions", result2, String.valueOf(j)).get();
                result5 = new ConnectDB(sendmsg5).execute("type_log", result2, String.valueOf(j)).get();//클릭수
                result4 = result4.replaceAll("\t", "");
                result5 = result5.replaceAll("\t","");
                //Log.d("return4", result4);
                if(result3.equals("null")||result4.equals("null")){
                    result3 = "empty";
                    spercent = "empty";
                    result5 = "empty";
                }else {
                    float a = Float.parseFloat(result4);
                    float b = Float.parseFloat(result3);
                    percent = (a / b) * 100;
                    spercent = String.format("%.2f", percent);
                }

                Log.d("percent", " " + percent);

                DataType data = new DataType(i + 1, oj[i + j], oj[i + j + 1], "나의 유형 : " + result2, spercent, result5);
                adapter_study.addItem(data);

                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fg;
        switch (v.getId()) {
            case R.id.survey:
                recyclerView.setVisibility(GONE);
                v.setVisibility(GONE); //버튼 뷰 안보이기(영역도 없앰)
                Toast.makeText(getContext(), "설문지 작성", Toast.LENGTH_SHORT).show();
                fg = Fragment3_child.newInstance();
                setChildFragment(fg);
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
//        sAdded()를 사용하여 Fragment가 존재하는 지 확인 후 작업.
//        FragmentTransaction 에 add(int containerViewId, Fragment, fragment) 를 사용하면
//        버튼을 누를 때마다 계속 Fragment가 생겨나서
//        replace(int containerViewId, Fragment, fragment) 하여 container가 재사용되도록 했다.
//        addToBackStack(String name)을 호출하면 생성하는 childFragment 들이 차곡차곡 쌓여
//        back 버튼을 누를 때마다 이전 Fragment로 순차적으로 돌아간다.
    }

}
