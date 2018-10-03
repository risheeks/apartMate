package com.example.dell.apartmate;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        intializeBitMaps();
    }

    private void intializeBitMaps(){
        mImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Wassup fellas");
    }
    private void intializeRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.rv_groupNames);
        MenuRecyclerViewAdaptor adaptor = new MenuRecyclerViewAdaptor(this, mNames, mImages);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
