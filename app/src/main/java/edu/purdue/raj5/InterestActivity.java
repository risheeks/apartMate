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
    FloatingActionButton fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        initializeRecyclerView();
        initiaizeAddButton();

    }
    private void initializeRecyclerView(){
        rv_interest = (RecyclerView)findViewById(R.id.rv_interests);
        mNames.add("Siddi");
        mNames.add("Adri");
        mInterests.add("read");
        mInterests.add("write");
        InterestAdapter adaptor = new InterestAdapter(this, mNames, mInterests);
        rv_interest.setAdapter(adaptor);
        rv_interest.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabInterestItem);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InterestActivity.this, "Add interest",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(InterestActivity.this);
                View viewDialog = LayoutInflater.from(InterestActivity.this).inflate(R.layout.add_interest_item,null);
                final EditText et_interest = (EditText) viewDialog.findViewById(R.id.et_interestAdd);
                final Button bt_interestAdd = (Button) viewDialog.findViewById(R.id.bt_interestAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_interestAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String interest = et_interest.getText().toString();
                        Toast.makeText(InterestActivity.this, "The interest has been added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }

}
