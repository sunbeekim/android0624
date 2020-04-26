package com.example.go;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull MyItemView holder, int position) {
        if(holder instanceof ViewHolderStudy){
            ViewHolderStudy viewHolderStudy = (ViewHolderStudy) holder;
            viewHolderStudy.onBind(items.get(position));
        }else if(holder instanceof ViewHolderSurvey){
            ViewHolderSurvey viewHolderSurvey = (ViewHolderSurvey) holder;
            viewHolderSurvey.onBind(items.get(position));
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
