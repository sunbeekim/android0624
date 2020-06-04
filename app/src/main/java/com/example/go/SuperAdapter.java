package com.example.go;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class SuperAdapter extends RecyclerView.Adapter<MyItemView>{
    ArrayList<MyItem> items = new ArrayList<>();

    CaseSelected sel_type;
    Context context;
    public SuperAdapter(CaseSelected sel_type){
        this.sel_type = sel_type;

    }

    public SuperAdapter(Context context, CaseSelected sel_type){
        this.sel_type = sel_type;
        this.context = context;
    }


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
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
        }else if(sel_type == CaseSelected.ROOM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view, parent, false);
            return new ViewHolderRoom(view);
        }else if(sel_type == CaseSelected.CHAT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
            return new ViewHolderChat(view);
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
            final ViewHolderSurvey viewHolderSurvey = (ViewHolderSurvey) holder;
            viewHolderSurvey.onBind(items.get(position));
            viewHolderSurvey.setOnViewHolderRadioClickListener(new OnViewHolderRadioClickListener() {
                @Override
                public void onViewHolderRadioClickListener() {
                    Log.d("TAG","라디오버튼이 클릭 되었습니다.");


                }
            });
        }else if(holder instanceof ViewHolderRoom){
        final ViewHolderRoom viewHolderRoom = (ViewHolderRoom) holder;
        viewHolderRoom.onBind(items.get(position));
        viewHolderRoom.setOnViewHolderRoomClickListener(new OnViewHolderRoomClickListener() {
            @Override
            public void onViewHolderRoomClickListener(View v) {

                mListener.onItemClick(v, position);
            }
        });
        }else if(holder instanceof ViewHolderChat) {
            final ViewHolderChat viewHolderChat = (ViewHolderChat) holder;
            viewHolderChat.onBind(items.get(position));

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
