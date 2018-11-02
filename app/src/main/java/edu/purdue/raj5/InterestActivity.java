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

public class InterestActivity extends AppCompatActivity {
    RecyclerView rv_interest;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mInterests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        initializeRecyclerView();

    }
    private void initializeRecyclerView(){
        rv_interest = (RecyclerView)findViewById(R.id.rv_interests);
        mNames.add("Bananas");
        mNames.add("Maggi");
        mInterests.add("1 dozen");
        mInterests.add("10");
        InterestAdapter adaptor = new InterestAdapter(this, mNames, mInterests);
        rv_interest.setAdapter(adaptor);
        rv_interest.setLayoutManager(new LinearLayoutManager(this));
    }
}
