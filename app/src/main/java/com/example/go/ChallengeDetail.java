package com.example.go;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeDetail extends AppCompatActivity {
    String title;
    String contents;
    String name;
    String reward;
    String period;

    TextView textView_name;
    TextView textView_title;
    TextView textView_contents;
    TextView textView_reward;
    TextView textView_period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_challenge_detail);

        textView_name = findViewById(R.id.textView_nameDisplay_detail);
        textView_contents = findViewById(R.id.textView_contentDisplay_detail);
        textView_title = findViewById(R.id.textView_titleDisplay_detail);
        textView_reward = findViewById(R.id.textView_rewardDisplay_detail);
        textView_period = findViewById(R.id.textView_periodDisplay_detail);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name", "NULL");
        title = intent.getExtras().getString("title", "NULL");
        contents = intent.getExtras().getString("contents", "NULL");
        reward = intent.getExtras().getString("reward", "NULL");
        period = intent.getExtras().getString("period", "NULL");

        textView_name.setText(name);
        textView_title.setText(title);
        textView_contents.setText(contents);
        textView_reward.setText(reward);
        textView_period.setText(period);
    }
}
