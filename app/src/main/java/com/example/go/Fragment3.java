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
        String studyname;
        int studyscore;
        getData("학습법", "1"); //자동화 필요
        getData("학습법", "2"); //자동화 필요




        //adapter.addItem(edtit.getText().toString()); //버튼을 클릭하면 adapter에 추가 방식(관리자모드)


       return fv3;
    }

    private void getData(String studyname, String studyscore) {
        DataType data = new DataType(studyname, studyscore);
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

//        if(!child.isAdded()){
            childF3.replace(R.id.fragment3child, child); //프레임레이아웃
            childF3.addToBackStack(null);
            childF3.commit();
//        }
    }
}
