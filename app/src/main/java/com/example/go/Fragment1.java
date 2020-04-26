package com.example.go;

import android.os.Bundle;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//tab1 서비스 구현
public class Fragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fv1 = inflater.inflate(R.layout.fragment1, container, false);
        // Inflate the layout for this fragment

        TextView fg = (TextView)fv1.findViewById(R.id.fm1);
        //if(getArguments()!=null) {
            String Id = getArguments().getString("userId");
            Toast.makeText(getActivity(), Id, Toast.LENGTH_LONG);
            fg.setText(Id);
        //}

        //채팅방 어댑터 만들기
        //리사이클러뷰 0~2개

        //채팅방 생성 버튼 -> 채팅방이 2개 이상이면 return 아니면 생성 -> QRcode? randomcode? Uid?
        //채팅방 접속 버튼 -> Fragment_child1 or Fragment_child2 클릭리스너 통해 보내기

        return fv1;
    }

}
