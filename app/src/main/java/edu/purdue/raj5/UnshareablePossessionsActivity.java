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

public class UnshareablePossessionsActivity extends AppCompatActivity {
    RecyclerView rv_unshareablePossessions;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mUnshareablePossessions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unshareable_possessions);
        initializeRecyclerView();

    }
    private void initializeRecyclerView(){
        rv_unshareablePossessions = (RecyclerView)findViewById(R.id.rv_unshareablePossessions);
        mNames.add("Bananas");
        mNames.add("Maggi");
        mUnshareablePossessions.add("1 dozen");
        mUnshareablePossessions.add("10");
        UnshareablePossessionsAdapter adapter = new UnshareablePossessionsAdapter(this, mNames, mUnshareablePossessions);
        rv_unshareablePossessions.setAdapter(adapter);
        rv_unshareablePossessions.setLayoutManager(new LinearLayoutManager(this));
    }
}
