package com.example.go;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

//tab2 서비스 구현
public class Fragment1_Dialog2 extends DialogFragment implements View.OnClickListener{
    Fragment1_Dialog2(){

    }
    Fragment fragment;
    String userid;
    EditText chatname, uname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d1 = inflater.inflate(R.layout.n_dialogset, container, false);
        EditText chatname, uname;

        chatname = (EditText) d1.findViewById(R.id.chatName);
        uname = (EditText) d1.findViewById(R.id.setName2);


        this.chatname = chatname;
        this.uname = uname;
        //Bundle bundle = getArguments();
        //String userid = bundle.getString("userid");
        this.userid = UserInfo.userid;
        Log.d("userid : ", userid);
        Button done, cancel;
        done = (Button)d1.findViewById(R.id.done2);
        done.setOnClickListener(this);
        cancel = (Button)d1.findViewById(R.id.cancel2);
        cancel.setOnClickListener(this);
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag2");

        return d1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done2 :
                //conn chat
                //chatinfo input +1 usercount
                //userinfo input userid, chatid, username, ct, chatcount
                String sendmsg = "chatmake";
                String chatName, username;
                chatName = chatname.getText().toString();
                username = uname.getText().toString();
                try{
                    String result = new ConnectDB(sendmsg).execute("chatmake", userid, chatName, username).get();
                    Log.d("data : ", userid+chatName+username);
                    Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                    dismiss();

                }catch (Exception e){

                }
                fragmentRefresh();
                break;
            case R.id.cancel2:
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.dismiss();
                break;
        }
    }
    void fragmentRefresh(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, Fragment1.newInstance()).commit();
    }
}
