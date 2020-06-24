package com.example.go;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomnavigation.BottomNavigationPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.zip.Inflater;


public class AfterActivity extends AppCompatActivity { //프래그먼트
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    private FirebaseAuth mAuth ;

    //https://everyshare.tistory.com/19
    OnBackPressedListener listener;
    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }

    @Override public void onBackPressed() {
        if(listener!=null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
    BottomNavi bn;
    public void setBottomNavi(BottomNavi bn){
        this.bn = bn;
    }

    static BottomNavigationView mainBN;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getUid();
        UserInfo.userid = userId;


//        Bundle bundle = new Bundle();
//        bundle.putString("userId",userId);
//        String sendmsg = "userId";
//        String result = userId; //자신이 보내고싶은 값을 보내시면됩니다


//https://fluorite94.tistory.com/29 프래그먼트 값 전달




        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();



        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        mainBN = findViewById(R.id.bottom_navigation);

        mainBN.setOnNavigationItemSelectedListener(
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }


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
