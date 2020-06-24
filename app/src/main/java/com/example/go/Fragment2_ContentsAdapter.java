package com.example.go;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment2_ContentsAdapter extends RecyclerView.Adapter<Fragment2_ContentsAdapter.ViewHolder> {

    public void addItem(Fragment2_Contents item) {
        items.add(item);
    }

    public void setItems(ArrayList<Fragment2_Contents> items) {
        this.items = items;
    }

    public Fragment2_Contents getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Fragment2_Contents item) {
        items.set(position, item);
    }

    public void clearItems() {items.clear();}

    static ArrayList<Fragment2_Contents> items = new ArrayList<Fragment2_Contents>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.fragment2_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Fragment2_Contents item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_Name;
        TextView text_Title;
        TextView text_Contents;
        TextView text_Reward;
        TextView text_Period;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            text_Name = itemView.findViewById(R.id.textView_name);
            text_Title = itemView.findViewById(R.id.textView_Title);
            text_Contents = itemView.findViewById(R.id.textView_Contents);
            text_Reward = itemView.findViewById(R.id.textView_Reward);
            text_Period = itemView.findViewById(R.id.textView_Period);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();

                    Intent intent = new Intent(context, ChallengeDetail.class);

                    intent.putExtra("name", getItem(position).name);
                    intent.putExtra("title", getItem(position).title);
                    intent.putExtra("contents", getItem(position).contents);
                    intent.putExtra("reward", getItem(position).reward);
                    intent.putExtra("period", getItem(position).period);
                    context.startActivity(intent);
                }
            });
        }

        public void setItem(Fragment2_Contents item) {
            text_Name.setText(item.getName());
            text_Title.setText(item.getTitle());
            text_Contents.setText(item.getContents());
            text_Reward.setText(item.getReward());
            text_Period.setText(item.getPeriod());
        }
    }

}
