package com.example.motivationappsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchSample extends AppCompatActivity {
    ListView lst;
    String[] categories = {"Science & Technology", "Literature", "Sports", "Art"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sample);
        lst = findViewById(R.id.list);

        ArrayAdapter<String> arr = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categories);

        lst.setAdapter(arr);
    }
}