package com.example.go;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//tab2 서비스 구현
public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fv2 = inflater.inflate(R.layout.fragment2, container, false);

        //은행 api 추가
        //리사이클러뷰 추가(다이어그램) 삭제 형식 만들기
        //날짜 추가, 최근 별로 나열
        //한번 누르면 내용확인 길게 누르면 선택(수정 및 삭제)

        return fv2;
    }
}
