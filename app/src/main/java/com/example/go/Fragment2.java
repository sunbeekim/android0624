package com.example.go;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

//tab2 서비스 구현
public class Fragment2 extends Fragment implements OnBackPressedListener{

    String name, title, contents, reward, period;
    String formatDate_pre, formatDate_cur;

    RecyclerView recyclerView;

    Switch aSwitch;

    Fragment2_ContentsAdapter adapter;
    LinearLayoutManager layoutManager;

    FirebaseAuth mAuth ;

    Toast toast;
    AfterActivity activity;
    View fv2;
    String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fv2 = inflater.inflate(R.layout.fragment2, container, false);

        ActionBar actionBar = ((AfterActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("도전");
        actionBar.setDisplayHomeAsUpEnabled(false);
        activity = (AfterActivity) getActivity();
        toast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        final String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();
        this.userId = userId;

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = fv2.findViewById(R.id.recyclerView_fragment2_grant);
        recyclerView.setLayoutManager(layoutManager);

        switchCheck();

        Button button = fv2.findViewById(R.id.button_grant_fragment2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendmsg = "dusername";
                String result; //전체출력 result;
                try{
                    result  = new ConnectDB(sendmsg).execute("dusername", userId).get();

                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(getContext(), ChallengeGrant.class);
                startActivityForResult(intent, 111);
            }
        });
        return fv2;
    }

    public void switchCheck() {
        aSwitch = fv2.findViewById(R.id.switch1);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String result;
                    String sendmsg = "grant";
                    String[] oj;
                    aSwitch.setText("제시한 목록");

                    adapter = new Fragment2_ContentsAdapter();
                    recyclerView.setAdapter(adapter);
                    adapter.clearItems();

                    try{
                        result = new ConnectDB(sendmsg).execute("grant", userId).get();
                        oj = result.split("--");

                        for (int i = 0; i < oj.length / 6; i++) {
                            adapter.addItem(new Fragment2_Contents(oj[i * 6], oj[i * 6 + 1], oj[i * 6 + 2], oj[i * 6 + 3], oj[i * 6 + 4] + "~" + oj[i * 6 + 5]));
                        }
                        adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    String result;
                    String sendmsg = "receiver";
                    String[] oj;
                    aSwitch.setText("받은 목록");

                    adapter = new Fragment2_ContentsAdapter();
                    recyclerView.setAdapter(adapter);
                    adapter.clearItems();

                    try{
                        result = new ConnectDB(sendmsg).execute("receiver", userId).get();
                        oj = result.split("--");

                        for (int i = 0; i < oj.length; i++) {
                            adapter.addItem(new Fragment2_Contents(oj[i * 6], oj[i * 6 + 1], oj[i * 6 + 2], oj[i * 6 + 3], oj[i * 6 + 4] + "~" + oj[i * 6 + 5]));
                        }
                        adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        if (aSwitch.isChecked()) {
            String result;
            String sendmsg = "grant";
            String[] oj;
            aSwitch.setText("제시한 목록");

            adapter = new Fragment2_ContentsAdapter();
            recyclerView.setAdapter(adapter);
            adapter.clearItems();

            try{
                result = new ConnectDB(sendmsg).execute("grant", userId).get();
                oj = result.split("--");

                for (int i = 0; i < oj.length / 6; i++) {
                    adapter.addItem(new Fragment2_Contents(oj[i * 6], oj[i * 6 + 1], oj[i * 6 + 2], oj[i * 6 + 3], oj[i * 6 + 4] + "~" + oj[i * 6 + 5]));
                }
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            String result;
            String sendmsg = "receiver";
            String[] oj;
            aSwitch.setText("받은 목록");

            adapter = new Fragment2_ContentsAdapter();
            recyclerView.setAdapter(adapter);
            adapter.clearItems();

            try{
                result = new ConnectDB(sendmsg).execute("receiver", userId).get();
                oj = result.split("--");

                for (int i = 0; i < oj.length; i++) {
                    adapter.addItem(new Fragment2_Contents(oj[i * 6], oj[i * 6 + 1], oj[i * 6 + 2], oj[i * 6 + 3], oj[i * 6 + 4] + "~" + oj[i * 6 + 5]));
                }
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 112) {
            assert data != null;
            name = data.getStringExtra("name");
            title = data.getStringExtra("title");
            contents = data.getStringExtra("contents");
            reward = data.getStringExtra("reward");
            String sort_date = data.getStringExtra("pre_date");

            String[] time = sort_date.split(",");
            formatDate_pre = sort_date;
            String year = time[0];
            String month = time[1];
            String day = time[2];

            period = year + "년 " + month + "월 " + day + "일";
            period += " ~ ";

            sort_date = data.getStringExtra("cur_date");
            formatDate_cur = sort_date;
            time = sort_date.split(",");
            year = time[0];
            month = time[1];
            day = time[2];

            period += year + "년 " + month + "월 " + day + "일";

            switchCheck();
        }
    }
    long backKeyPressedTime;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        } if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            getActivity().finish();
            toast.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }
}
