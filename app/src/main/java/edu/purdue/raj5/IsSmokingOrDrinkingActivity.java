package com.example.dell.apartmate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class IsSmokingOrDrinkingActivity extends AppCompatActivity {
    RecyclerView rv_isSmokingOrDrinking;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Boolean> mIsSmoking = new ArrayList<>();
    private ArrayList<Boolean> mIsDrinking = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        initializeRecyclerView();

    }
    private void initializeRecyclerView(){
        rv_isSmokingOrDrinking= (RecyclerView)findViewById(R.id.rv_roommateRating);
        mNames.add("Siddi");
        mNames.add("Adri");
        mIsSmoking.add(true);
        mIsDrinking.add(false);
        mIsSmoking.add(false);
        mIsDrinking.add(true);
        IsSmokingOrDrinkingAdapter adaptor = new IsSmokingOrDrinkingAdapter(this, mNames, mIsSmoking, mIsDrinking);
        rv_isSmokingOrDrinking.setAdapter(adaptor);
        rv_isSmokingOrDrinking.setLayoutManager(new LinearLayoutManager(this));
    }
}
