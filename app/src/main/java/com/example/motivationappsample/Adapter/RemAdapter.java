package com.example.motivationappsample.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.motivationappsample.Model.ReminderModel;
import com.example.motivationappsample.R;
import com.example.motivationappsample.Reminder;

import java.util.List;

public class RemAdapter extends RecyclerView.Adapter<RemAdapter.ViewHolder> {

    private List<ReminderModel> remList;
    private Reminder activity;

    public RemAdapter(Reminder activity){
        this.activity=activity;
    }

    public RemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.rem_layout,parent,false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder (ViewHolder holder, int position){
         ReminderModel item = remList.get(position);
         holder.taskTitle.setText(item.getTaskTitle());
//         holder.taskDesc.setText(item.getTaskDesc());
//         holder.taskTime.setText(item.getTaskTime());
//         holder.taskDate.setText(item.getTaskDate());
         holder.task.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount(){
         return remList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setReminder(List<ReminderModel> remList){
        this.remList = remList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle;
        TextView taskDesc;
        Button taskTime;
        Button taskDate;
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.rem_checkbox);
            taskTitle = view.findViewById(R.id.taskTitle);

        }
    }




}
