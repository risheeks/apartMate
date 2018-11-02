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
    FloatingActionButton fab2;
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
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabInterestItem);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UnshareablePossessionsActivity.this, "Add Unshareable Possession",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(UnshareablePossessionsActivity.this);
                View viewDialog = LayoutInflater.from(UnshareablePossessionsActivity.this).inflate(R.layout.add_unshareable_possessions,null);
                final EditText et_unshareablePossessionsAdd = (EditText) viewDialog.findViewById(R.id.et_unshareablePossessionAdd);
                final Button bt_unshareablePossessionsAdd = (Button) viewDialog.findViewById(R.id.bt_unshareablePossessionAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_unshareablePossessionsAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String interest = et_unshareablePossessionsAdd.getText().toString();
                        Toast.makeText(UnshareablePossessionsActivity.this, "The interest has been added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}
