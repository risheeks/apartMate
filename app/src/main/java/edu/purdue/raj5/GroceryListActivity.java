package com.example.dell.apartmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {
    RecyclerView rv_grocery;
    private ArrayList<String> mNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);
        initializeRecyclerView();
    }
    private void initializeRecyclerView(){
        rv_grocery = (RecyclerView)findViewById(R.id.rv_groupNames);
        GroceryAdaptor adaptor = new GroceryAdaptor(this, mNames);
        rv_grocery.setAdapter(adaptor);
        rv_grocery.setLayoutManager(new LinearLayoutManager(this));
    }
}
