package com.example.motivationappsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class Imageadapter extends RecyclerView.Adapter<Imageadapter.ViewHolder>{
    Context context;
    int[] Images;

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView rowImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowImage=itemView.findViewById(R.id.imageView4);
        }
    }
    public Imageadapter(Context context,int[] Images){
        this.context=context;
        this.Images=Images;

    }


    @NonNull
    @Override
    public Imageadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        //inflate the custom Layout
        View view= inflater.inflate(R.layout.homepageitems,parent,false);
        //return a new holder instances
        ViewHolder viewHolder=new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Imageadapter.ViewHolder holder, int position) {
        //replace the content of a view to be invoke by a LayoutManager
        holder.rowImage.setImageResource(Images[position]);

    }

    @Override
    public int getItemCount() {
        return Images.length;
    }
}