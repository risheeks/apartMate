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
    FloatingActionButton fab2;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mShareablePossessions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareable_possessions);
        initializeRecyclerView();
        initiaizeAddButton();
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
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabShareablePossession);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShareablePossessionsActivity.this, "Add Shareable Possessions",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ShareablePossessionsActivity.this);
                View viewDialog = LayoutInflater.from(ShareablePossessionsActivity.this).inflate(R.layout.add_shareable_possessions,null);
                final EditText et_shareablePossessionAdd = (EditText) viewDialog.findViewById(R.id.et_shareablePossessionsAdd);
                final Button bt_shareablePossessionAdd = (Button) viewDialog.findViewById(R.id.bt_shareablePossessionsAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_shareablePossessionAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String shareablePossession = et_shareablePossessionAdd.getText().toString();
                        Toast.makeText(ShareablePossessionsActivity.this, "A Shareable Possession has been added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}
