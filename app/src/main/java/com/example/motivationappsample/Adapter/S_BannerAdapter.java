package com.example.motivationappsample.Adapter;

import android.bluetooth.BluetoothHidDeviceAppQosSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.motivationappsample.MainActivity;
import com.example.motivationappsample.Model.StoriesBanner;
import com.example.motivationappsample.Quotes;
import com.example.motivationappsample.R;

import java.util.ArrayList;

public class S_BannerAdapter extends PagerAdapter {

    CardView cardView;
    ImageView bannerIMG;
    TextView titleTV;
    TextView descTV;
    private ViewPager viewPager;
    private Context context;
    private ArrayList<StoriesBanner> storiesBannersArrayList;




    //constructor
    public S_BannerAdapter(Context context, ArrayList<StoriesBanner> storiesBannersArrayList) {
        this.context = context;
        this.storiesBannersArrayList = storiesBannersArrayList;
    }




    @Override
    public int getCount() {
        return storiesBannersArrayList.size(); //returns size of items in list
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflate layout story_card.xml
        View view = LayoutInflater.from(context).inflate(R.layout.story_card, container, false);

        //init uid views from story_card.xml
        CardView cardView = view.findViewById(R.id.cardView);
        ImageView bannerIMG = view.findViewById(R.id.bannerIMG);
//        TextView titleTV = view.findViewById(R.id.bannerTITLE);
//        TextView descTV = view.findViewById((R.id.bannerDESC));


        //get data

        StoriesBanner model = storiesBannersArrayList.get(position);
        String title = model.getTitle();
        String description = model.getDescription();
        int image = model.getImage();

        //set data

        bannerIMG.setImageResource(image);
//        titleTV.setText(title);
//        descTV.setText(description);





        //handle card click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, title + "\n" + description, Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(new Intent(view.getContext(), Quotes.class));

            }


        });




        //add view to container
//        container.addView(view, position);
        container.addView(view);

        return view;


    }





    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }



}
