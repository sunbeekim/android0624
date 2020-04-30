package com.example.go;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AfterActivity extends AppCompatActivity { //프래그먼트
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);


//        Intent intent = getIntent();
//        String userId = intent.getStringExtra("userId");
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getUid();
        Bundle bundle = new Bundle();
        bundle.putString("userId",userId);

        String sendmsg = "userId";
        String result = userId; //자신이 보내고싶은 값을 보내시면됩니다
        try{
            String rst = new ConnectDB(sendmsg).execute(result,"userId").get();
        }catch (Exception e){
            e.printStackTrace();
        }

//https://fluorite94.tistory.com/29 프래그먼트 값 전달



        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.tab1:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                                return true;
                            case R.id.tab2:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                                return true;
                            case R.id.tab3:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3.newInstance()).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch(curId){
            case R.id.action_logout:
                signOut();
                //finishAffinity(); //해당 앱의 루트 액티비티를 종료
                Intent intent = new Intent(this, Firebase_test.class);
                startActivity(intent);
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();

    }
}
