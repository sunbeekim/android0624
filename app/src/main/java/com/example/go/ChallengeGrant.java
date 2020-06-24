package com.example.go;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ChallengeGrant extends AppCompatActivity {

    String set_date;
    String set_user;
    String formatDate_pre, formatDate_cur;
    ArrayList<String> user_id = new ArrayList<>();

    TextView textView_name;
    EditText editTextView_title;
    EditText editTextView_contents;
    EditText editTextView_reward;
    TextView textView_date;

    FirebaseAuth mAuth = null;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_grant);

        mAuth = FirebaseAuth.getInstance();
        final String userId = mAuth.getCurrentUser().getDisplayName();
        this.userId = userId;

        String my_name = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();
        user_id.add(my_name);
        String result;
        String sendmsg = "spinner";
        String[] oj;
        try{
            result = new ConnectDB(sendmsg).execute("spinner", userId).get();
            oj = result.split("--");
            oj[0] = oj[0].replaceAll("\t", "");
            for (int i = 0; i < oj.length; i++) {
                user_id.add(oj[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        this.setTitle("도전");


        textView_name = findViewById(R.id.textView_nameDisplay_grant);
        editTextView_title = findViewById(R.id.editText_titleDisplay_grant);
        editTextView_contents = findViewById(R.id.editText_contentDisplay_detail);
        editTextView_reward = findViewById(R.id.editText_rewardDisplay_grant);
        textView_date = findViewById(R.id.textView_periodDisplay_grant);

//        달력 이미지 버튼 클릭
        ImageButton imageButton = findViewById(R.id.imageButton_calendar_grant);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivityForResult(intent, 100);
            }
        });

//        스피너 설정
        Spinner spinner = findViewById(R.id.spinner_grant);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, user_id);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position != 0) {
                    textView_name.setText((CharSequence) parent.getItemAtPosition(position));
                    set_user = textView_name.getText().toString();
                }
                else
                    textView_name.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button = findViewById(R.id.button_ok_grant);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView_name.getText().toString().isEmpty()
                        || textView_date.getText().toString().isEmpty()
                        || editTextView_reward.getText().toString().isEmpty()
                        || editTextView_title.getText().toString().isEmpty()) {

//                    if문 실행 내용
                    Toast.makeText(ChallengeGrant.this, "비어있는 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
//                    ...까지

                }

                String name = textView_name.getText().toString();
                name = name.replaceAll("\t","");
                String title = editTextView_title.getText().toString();
                String contents = editTextView_contents.getText().toString();
                String reward = editTextView_reward.getText().toString();

            String sendmsg = "userdisplayname";
            String result; //전체출력 result;
            String[] oj;
            // sendstringSum = userId + "-" + name + "-" + title + "-" + contents + "-" + formatDate_pre + "-" + formatDate_cur + "-" + reward;
            // result  = new ConnectDB(sendmsg).execute("userdisplayname", sendstringSum).get();
            // 1번 2번 connectDB  3번 이클립스 androidDB.jsp 4번 클래스
            try{
                result  = new ConnectDB(sendmsg).execute("userdisplayname", userId, name, title, contents, formatDate_pre, formatDate_cur, reward).get();


            }catch (Exception e){
                e.printStackTrace();
            }
                Intent intent = new Intent(ChallengeGrant.this, Fragment2.class);
                intent.putExtra("name", textView_name.getText().toString());
                intent.putExtra("title", editTextView_title.getText().toString());
                intent.putExtra("contents", editTextView_contents.getText().toString());
                intent.putExtra("reward", editTextView_reward.getText().toString());
                intent.putExtra("pre_date", formatDate_pre);
                intent.putExtra("cur_date", formatDate_cur);
                setResult(112, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101) {
            set_date = data.getStringExtra("date");
            formatDate_pre = data.getStringExtra("pre_date");
            formatDate_cur = data.getStringExtra("cur_date");
            textView_date.setText(set_date);
        }
    }
}
