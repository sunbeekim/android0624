package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SuperAdapter extends RecyclerView.Adapter<MyItemView>{
    ArrayList<MyItem> items = new ArrayList<>();

    CaseSelected sel_type;

    public SuperAdapter(CaseSelected sel_type){
        this.sel_type = sel_type;

    }



    @NonNull
    @Override
    public MyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(sel_type == CaseSelected.STUDY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_item, parent, false);
            return new ViewHolderStudy(view);
        }else if(sel_type == CaseSelected.SURVEY){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_item, parent, false);
            return new ViewHolderSurvey(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull MyItemView holder, final int position) {
        if(holder instanceof ViewHolderStudy) {
            final ViewHolderStudy viewHolderStudy = (ViewHolderStudy) holder;
            viewHolderStudy.onBind(items.get(position));
            viewHolderStudy.setOnViewHolderStudyClickListener(new OnViewHolderStudyClickListener() {
                @Override
                public void OnViewHolderStudyClickListener() {


                }
            });

        }else if(holder instanceof ViewHolderSurvey){
            ViewHolderSurvey viewHolderSurvey = (ViewHolderSurvey) holder;
            viewHolderSurvey.onBind(items.get(position));
            viewHolderSurvey.setOnViewHolderRadioClickListener(new OnViewHolderRadioClickListener() {
                @Override
                public void onViewHolderRadioClickListener() {
                    Log.d("TAG","라디오버튼이 클릭 되었습니다.");

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItem(MyItem data){
        items.add(data);
    }
}
