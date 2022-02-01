package com.example.motivationappsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Quotes extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter Imageadapter;
    RecyclerView.LayoutManager layoutManager;
    int[] Images ={R.drawable.quote1,R.drawable.quote2,
            R.drawable.quote3,R.drawable.quote4,R.drawable.quote5,
            R.drawable.quote6,R.drawable.quote7,R.drawable.quote8,
            R.drawable.quote9,R.drawable.quote10

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        recyclerView=findViewById(R.id.rcprogram);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //creat an instance of Imageadapter  ,pass context and  all array elements to the constructor
        Imageadapter= new Imageadapter(this,Images);
        //attach the adapter with Recyclerview
        recyclerView.setAdapter(Imageadapter);
    }
}