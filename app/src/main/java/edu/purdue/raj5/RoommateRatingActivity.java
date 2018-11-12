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

public class RoommateRatingActivity extends AppCompatActivity {
    RecyclerView rv_roommateRatings;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mRating = new ArrayList<>();
    private ArrayList<String> mRatingComments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        initializeRecyclerView();

    }
    private void initializeRecyclerView(){
        rv_roommateRatings= (RecyclerView)findViewById(R.id.rv_roommateRating);
        mNames.add("Siddi");
        mNames.add("Adri");
        mRating.add("4");
        mRatingComments.add("write");
        mRating.add("3.5");
        mRatingComments.add("write");
        RoommateRatingAdapter adaptor = new RoommateRatingAdapter(this, mNames, mRating,mRatingComments);
        rv_roommateRatings.setAdapter(adaptor);
        rv_roommateRatings.setLayoutManager(new LinearLayoutManager(this));
    }
}
