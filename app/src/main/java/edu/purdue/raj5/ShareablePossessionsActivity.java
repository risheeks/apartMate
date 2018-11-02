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

public class ShareablePossessionsActivity extends AppCompatActivity {
    RecyclerView rv_shareablePossessions;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mShareablePossessions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareable_possessions);
        initializeRecyclerView();

    }
    private void initializeRecyclerView(){
        rv_shareablePossessions = (RecyclerView)findViewById(R.id.rv_shareablePossessions);
        mNames.add("Bananas");
        mNames.add("Maggi");
        mShareablePossessions.add("1 dozen");
        mShareablePossessions.add("10");
        ShareablePossessionsAdapter adapter = new ShareablePossessionsAdapter(this, mNames, mShareablePossessions);
        rv_shareablePossessions.setAdapter(adapter);
        rv_shareablePossessions.setLayoutManager(new LinearLayoutManager(this));
    }
}
