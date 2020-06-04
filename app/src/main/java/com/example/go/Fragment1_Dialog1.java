package com.example.go;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

//tab2 서비스 구현
public class Fragment1_Dialog1 extends DialogFragment implements View.OnClickListener{
    Fragment1_Dialog1(){

    }
    Fragment fragment;
    String userid;
    EditText chid, uname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View d1 = inflater.inflate(R.layout.c_n_dialogset, container, false);
        EditText chid;
        EditText uname;
        chid = (EditText) d1.findViewById(R.id.roomCode);
        uname = (EditText) d1.findViewById(R.id.setName);


        this.chid = chid;
        this.uname = uname;
        //Bundle bundle = getArguments();
        //String userid = bundle.getString("userid");
        this.userid = UserInfo.userid;
        Log.d("chatid : ", userid);
        Button done, cancel;
        done = (Button)d1.findViewById(R.id.done);
        done.setOnClickListener(this);
        cancel = (Button)d1.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");

        return d1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done :
                //conn chat
                //chatinfo input +1 usercount
                //userinfo input userid, chatid, username, ct, chatcount
                String sendmsg = "userinfo";
                String chatid, username;
                chatid = chid.getText().toString();
                username = uname.getText().toString();
                try{
                    String result = new ConnectDB(sendmsg).execute("userinfo", userid, chatid, username).get();
                    Log.d("data : ", userid+chatid+username);
                    Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                    
                    dismiss();
                }catch (Exception e){

                }

                break;
            case R.id.cancel:
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.dismiss();
                break;
        }
    }
}
