package com.example.go;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.view.View.GONE;

//tab3 서비스 구현
public class Fragment3 extends Fragment implements View.OnClickListener{

    public static Fragment3 newInstance(){
        return  new Fragment3();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //https://nuggy875.tistory.com/7 시간 구하기
    //https://d4emon.tistory.com/60
    SuperAdapter adapter_study;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View fv3 = inflater.inflate(R.layout.fragment3, container, false);

        Button survey;
        survey = (Button) fv3.findViewById(R.id.survey);
        survey.setOnClickListener(this);

        if(getArguments()!=null) {//데이터 정의
            String Id = getArguments().getString("userId");
        }

        recyclerView = fv3.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter_study = new SuperAdapter(CaseSelected.STUDY);
        recyclerView.setAdapter(adapter_study);
        getSurveyCechk();
        getData("학습법","1","sf", "88","987"); //자동화 필요 Fragment3_child 참고





        //adapter.addItem(edtit.getText().toString()); //버튼을 클릭하면 adapter에 추가 방식(관리자모드)


       return fv3;
    }

    private void getSurveyCechk() {

    }

    private void getData(String studyname, String studyadress, String mytype, String percent, String clicknum) {
        DataType data = new DataType(studyname, studyadress, mytype, percent, clicknum);
        adapter_study.addItem(data);
    }

    @Override
    public void onClick(View v) {
        Fragment fg;
        switch (v.getId()){
            case R.id.survey:
                recyclerView.setVisibility(GONE);
                v.setVisibility(GONE); //버튼 뷰 안보이기(영역도 없앰)
                Toast.makeText(getContext(), "설문지 작성", Toast.LENGTH_SHORT).show();
                fg = Fragment3_child.newInstance();
                setChildFragment(fg);
                break;
        }
    }

    private void setChildFragment(Fragment child){
        FragmentTransaction childF3 = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()){
            childF3.replace(R.id.fragment3child, child); //프레임레이아웃
            childF3.addToBackStack(null);
            childF3.commit();
        }
//        sAdded()를 사용하여 Fragment가 존재하는 지 확인 후 작업.
//        FragmentTransaction 에 add(int containerViewId, Fragment, fragment) 를 사용하면
//        버튼을 누를 때마다 계속 Fragment가 생겨나서
//        replace(int containerViewId, Fragment, fragment) 하여 container가 재사용되도록 했다.
//        addToBackStack(String name)을 호출하면 생성하는 childFragment 들이 차곡차곡 쌓여
//        back 버튼을 누를 때마다 이전 Fragment로 순차적으로 돌아간다.
    }
}
