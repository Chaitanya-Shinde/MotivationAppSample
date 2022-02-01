package com.example.motivationappsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageView quotess, shorts;
    ImageView btnquote, btnshorts;
    //    ImageButton
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnquote=findViewById(R.id.btnquote);
        btnshorts=findViewById(R.id.btnshorts);
        btnquote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Quotes.class);
                startActivity(intent);


            }


        });
        btnshorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }


        });


        //Navigation Drawer code
        drawerLayout =findViewById(R.id.drawer);
        //passing the open and the close toggle for the drawer layout listener
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //to make the navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Reminder.class);
                startActivity(intent);
            }
        });



        quotess = findViewById(R.id.btnquote);
        Random r = new Random();
        int q = r.nextInt(10);
        switch (q){
            case 1:
                quotess.setImageResource(R.drawable.quote1);
                break;
            case 2:
                quotess.setImageResource(R.drawable.quote2);
                break;
            case 3:
                quotess.setImageResource(R.drawable.quote3);
                break;
            case 4:
                quotess.setImageResource(R.drawable.quote4);
                break;
            case 5:
                quotess.setImageResource(R.drawable.quote5);
                break;
            case 6:
                quotess.setImageResource(R.drawable.quote6);
                break;
            case 7:
                quotess.setImageResource(R.drawable.quote7);
                break;
            case 8:
                quotess.setImageResource(R.drawable.quote8);
                break;
            case 9:
                quotess.setImageResource(R.drawable.quote9);
                break;
            default:
                quotess.setImageResource(R.drawable.quote10);
                break;
        }
        shorts = findViewById(R.id.btnshorts);
        int s = r.nextInt(10);
        switch(s){
            case 1:
                shorts.setImageResource(R.drawable.short_stories1);
                break;
            case 2:
                shorts.setImageResource(R.drawable.short_stories2);
                break;
            case 3:
                shorts.setImageResource(R.drawable.short_stories3);
                break;
            case 4:
                shorts.setImageResource(R.drawable.short_stories4);
                break;
            case 5:
                shorts.setImageResource(R.drawable.short_stories5);
                break;
            case 6:
                shorts.setImageResource(R.drawable.short_stories6);
                break;
            case 7:
                shorts.setImageResource(R.drawable.short_stories7);
                break;
            case 8:
                shorts.setImageResource(R.drawable.short_stories8);
                break;
            case 9:
                shorts.setImageResource(R.drawable.short_stories9);
                break;
            default:
                shorts.setImageResource(R.drawable.short_stories10);
                break;
        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Bell icon code
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuitem,menu);
        return true;
    }



}