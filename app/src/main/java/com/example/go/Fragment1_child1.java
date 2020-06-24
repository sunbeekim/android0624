package com.example.go;

import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.example.go.AfterActivity.mainBN;


public class Fragment1_child1 extends Fragment implements OnBackPressedListener,BottomNavi {


    RecyclerView recyclerViewChat;
    SuperAdapter adapter_chat;

    public static Fragment1_child1 newInstance() {

        return new Fragment1_child1();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //SuperAdapter adapter_chat;
    //RecyclerView recyclerViewChat;
    int backValue = 0;
    AfterActivity activity;

    @Override
    public void onBackPressed() {
        fragmentRemove();
    }

    @Override
    public void visibleBottomNavi() {

    }


    class BackThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadexit) {
                backValue++;
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(3500);
                } catch (Exception e) {

                }
            }
        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    String result;

                    int chatnum = 0;
                    String[] oj;
                    try {

                            result = new ConnectDB("polling").execute("polling").get();
                            oj = result.split("--");
                            chatnum = Integer.parseInt(oj[1]);
                            Log.d("result2", result);

                        if(ChatInfo.chatnum < chatnum){
                            getdata(Id, pos);
                            ChatInfo.chatnum = chatnum;

                        }else{
                            //Toast.makeText(getContext(), "chatnum 변화 없음", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }



    boolean threadexit = true;
    NestedScrollView mScroll;
    EditText inputmsg;
    String Id, pos, ifchid;
    TextView space;
    SoftKeyboard softKeyboard;
    LinearLayout chating;
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.drawer, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
    ActionBar actionBar;
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment1_child1, container, false);
        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();


        ActionBar actionBar = ((AfterActivity)getActivity()).getSupportActionBar();
        this.actionBar = actionBar;
        actionBar.hide();

        activity = (AfterActivity) getActivity();



        mScroll = (NestedScrollView) v.findViewById(R.id.mScroll);

        recyclerViewChat = v.findViewById(R.id.recyclerViewChat);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewChat.setLayoutManager(linearLayoutManager);

        adapter_chat = new SuperAdapter(CaseSelected.CHAT);
        recyclerViewChat.setAdapter(adapter_chat);


        inputmsg = (EditText) v.findViewById(R.id.inputMsg);
        final Button sendmsg = v.findViewById(R.id.sendMsg);
        final Button sendmsg2 = v.findViewById(R.id.sendMsg2);

        final String Id = UserInfo.userid;
        this.Id = Id;
        final String pos = ChatInfo.chatpos;
        this.pos = pos;

        getdata(Id, pos);
        scrollDown();

        space = (TextView)v.findViewById(R.id.space);


        inputmsg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    AfterActivity.mainBN.setVisibility(View.GONE);
//                    space.setVisibility(View.GONE);
                    scrollDown();
                    sendmsg2.setVisibility(View.GONE);
                    sendmsg.setVisibility(View.VISIBLE);

                }

                return false;
            }
        });


        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //채팅입력
                adapter_chat.items.clear();
                String context = inputmsg.getText().toString();

                Log.d("null체크", context);
                inputmsg.setText("");
                String sendmsg2 = "inputmsg";
                String result; //전체출력 result;
                String[] oj;
                int cut;
                SimpleDateFormat sd = new SimpleDateFormat("yyyy.mm.dd.hh:mm");
                Date date;
                Log.d("id, pos", context + Id + pos);

                try {
                    if (!context.equals("")) {
                        result = new ConnectDB(sendmsg2).execute("inputmsg", Id, context, pos).get();// 채팅id를 가져와서 채팅 입력
                        oj = result.split("--");

                        getdata(Id, pos);

                        adapter_chat.notifyDataSetChanged();
                        //https://wikang.tistory.com/entry/ScrollView-%EC%8A%A4%ED%81%AC%EB%A1%A4%EB%B7%B0-%ED%8F%AC%EC%BB%A4%EC%8A%A4%EB%A5%BC-%EC%A0%9C%EC%9D%BC-%EB%B0%91%EC%9C%BC%EB%A1%9C-%EB%82%B4%EB%A6%AC%EA%B8%B0
                        scrollDown();
                        editFocus();
                    } else {
                        Log.d("null", "Chatnull");
                    }
                    //https://dreamaz.tistory.com/249
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        });
        Button outchat = (Button)v.findViewById(R.id.outchat);
        outchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendmsg = "outchat";

                try {
                    if(pos.equals("0")) {
                        new ConnectDB(sendmsg).execute("outchat", Id, ChatInfo.chatid_1).get();
                    }else if(pos.equals("1")){
                        new ConnectDB(sendmsg).execute("outchat", Id, ChatInfo.chatid_2).get();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                fragmentRemove();
            }
        });
        TextView conncount = (TextView)v.findViewById(R.id.conncount);
        TextView chid = (TextView)v.findViewById(R.id.chid);
        TextView chatname = (TextView)v.findViewById(R.id.chatname);

        if(pos.equals("0")){
             chid.setText(ChatInfo.chatid_1);
             ifchid = chid.getText().toString();
             conncount.setText("참여인원 : "+ChatInfo.chatuser_1);
             chatname.setText(ChatInfo.chatname_1);
        }else if(pos.equals("1")){
            chid.setText(ChatInfo.chatid_2);
            ifchid = chid.getText().toString();
            conncount.setText("참여인원 : "+ChatInfo.chatuser_2);
            chatname.setText(ChatInfo.chatname_2);
        }else{
            chid.setText("~~~");
        }
        chid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) { //눌렀을 때 동작

                        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("ID", ifchid); //클립보드에 ID라는 이름표로 id 값을 복사하여 저장
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getContext(), "Chat ID가 복사되었습니다.", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });


        chating = (LinearLayout) v.findViewById(R.id.chating);
        InputMethodManager controlManager = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        softKeyboard = new SoftKeyboard(chating, controlManager);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {
            @Override
            public void onSoftKeyboardHide() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        space.setVisibility(View.INVISIBLE);
                        mainBN.setVisibility(View.VISIBLE);

                    }
                });
            }

            @Override
            public void onSoftKeyboardShow() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        space.setVisibility(View.GONE);
                        mainBN.setVisibility(View.GONE);

                    }
                });
            }
        });

        return v;
    }

    void fragmentRemove(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, Fragment1.newInstance()).commit();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.threadexit = false;
        actionBar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }

    private void editFocus() {
        inputmsg.post(new Runnable() {
            @Override
            public void run() {
                inputmsg.setFocusableInTouchMode(true);
                inputmsg.requestFocus();


            }
        });
    }


    public void scrollDown() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void getdata(String Id, String pos) {//채팅출력

        adapter_chat.items.clear();
        adapter_chat.notifyDataSetChanged();
        String sendmsg = "selectchat";
        String result;
        String[] oj;
        int cut;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy.mm.dd.hh:mm");
        Date date;
        String newdate = null;

        try {
            result = new ConnectDB(sendmsg).execute("selectchat", Id, pos).get();// 사용자의 채팅방 정보 가져오기
            oj = result.split("--"); //
            oj[0] = oj[0].replaceAll("\t", "");
            for (int i = 0; i < oj.length / 5; i++) {

                date = sd.parse(oj[i * 5 + 3]);
                SimpleDateFormat fd = new SimpleDateFormat("hh:mm");
                oj[i * 5 + 3] = fd.format(date);
                ChatInfo.chatnum = Integer.parseInt(oj[i*5+4]);
                DataType data = new DataType(i + 1, oj[i * 5], oj[i * 5 + 1], oj[i * 5 + 2], oj[i * 5 + 3]);
                adapter_chat.addItem(data);
                adapter_chat.notifyDataSetChanged();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollDown();
    }


}
