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

public class EmergencyContactActivity extends AppCompatActivity {
    RecyclerView rv_emergencyContacts;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mEmergencyContacts = new ArrayList<>();
    FloatingActionButton fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        initializeRecyclerView();
        initiaizeAddButton();

    }
    private void initializeRecyclerView(){
        rv_emergencyContacts = (RecyclerView)findViewById(R.id.rv_emergencyContacts);
        mNames.add("Siddi");
        mNames.add("Adri");
        mEmergencyContacts.add("read");
        mEmergencyContacts.add("write");
        EmergencyContactAdapter adaptor = new EmergencyContactAdapter(this, mNames, mEmergencyContacts);
        rv_emergencyContacts.setAdapter(adaptor);
        rv_emergencyContacts.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabInterestItem);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmergencyContactActivity.this, "Add interest",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyContactActivity.this);
                View viewDialog = LayoutInflater.from(EmergencyContactActivity.this).inflate(R.layout.add_emergency_contact,null);
                final EditText et_interest = (EditText) viewDialog.findViewById(R.id.et_emergencyContactAdd);
                final Button bt_interestAdd = (Button) viewDialog.findViewById(R.id.bt_emergencyContactAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_interestAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String interest = et_interest.getText().toString();
                        Toast.makeText(EmergencyContactActivity.this, "The interest has been added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

}
