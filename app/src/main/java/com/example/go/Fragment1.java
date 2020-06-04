package com.example.go;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import static android.view.View.GONE;

//tab1 서비스 구현
public class Fragment1 extends Fragment implements View.OnClickListener {


    public static Fragment1 newInstance() {
        return new Fragment1();
    }



    SuperAdapter adapter_room;
    RecyclerView recyclerViewRoom;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fv1 = inflater.inflate(R.layout.fragment1, container, false);
        // Inflate the layout for this fragment


        final Button addRoom, makeRoom;

        addRoom = (Button)fv1.findViewById(R.id.addRoom);
        makeRoom = (Button)fv1.findViewById(R.id.makeRoom);
        addRoom.setOnClickListener(this);
        makeRoom.setOnClickListener(this);
        addRoom.setVisibility(fv1.VISIBLE);
        makeRoom.setVisibility(fv1.VISIBLE);
        recyclerViewRoom = fv1.findViewById(R.id.recyclerViewRoom);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRoom.setLayoutManager(linearLayoutManager);

//https://recipes4dev.tistory.com/168
        adapter_room = new SuperAdapter(getContext() ,CaseSelected.ROOM);
        recyclerViewRoom.setAdapter(adapter_room);

        getChat();


        adapter_room.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            Fragment fg;
            @Override
            public void onItemClick(View v, int pos) {
                Log.d("포지션 : ", pos+"");
                addRoom.setVisibility(GONE);
                makeRoom.setVisibility(GONE);
                //userid, chatid
                String poschan = String.valueOf(pos);
                recyclerViewRoom.setVisibility(GONE);
                ChatInfo.chatpos = poschan;
//                Bundle bundle = new Bundle();
//                bundle.putString("userid", Id);
//                bundle.putString("pos", poschan);

                fg = Fragment1_child1.newInstance();
//                fg.setArguments(bundle);
                setChildFragment(fg);


            }
        });

        //채팅방 어댑터 만들기
        //리사이클러뷰 0~2개

        //채팅방 생성 버튼 -> 채팅방이 2개 이상이면 return 아니면 생성 -> QRcode? randomcode? Uid?
        //채팅방 접속 버튼 -> Fragment_child1 or Fragment_child2 클릭리스너 통해 보내기

        return fv1;
    }

    private void getChat() {
        String sendmsg = "getuserinfo";
        String result; //전체출력 result;
        String[] oj;
        int cut;

        try{
            result  = new ConnectDB(sendmsg).execute("getuserinfo", UserInfo.userid).get();// 사용자의 채팅방 정보 가져오기
            oj = result.split("--"); //
            cut = oj.length;


            for(int i = 0; i < cut/3; i++) {

                Log.d("test", oj[i*3]+oj[(i*3)+1]+ Integer.parseInt(oj[i*3+2]));
                DataType data = new DataType(oj[i*3], oj[i*3+1], Integer.parseInt(oj[i*3+2]));
                adapter_room.addItem(data);
                adapter_room.notifyDataSetChanged();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childF3 = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childF3.replace(R.id.fragment1child, child); //프레임레이아웃
            childF3.addToBackStack(null);
            childF3.commit();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addRoom:
                //Bundle bundle = new Bundle();
                //bundle.putString("userid", UserInfo.userid);

                Fragment1_Dialog1 dialog1 = new Fragment1_Dialog1();
                //dialog1.setArguments(bundle);
                dialog1.show(this.getActivity().getSupportFragmentManager(), "tag");
                break;
            case R.id.makeRoom:
                Fragment1_Dialog2 dialog2 = new Fragment1_Dialog2();
                dialog2.show(this.getActivity().getSupportFragmentManager(), "tag2");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }


    }

}
