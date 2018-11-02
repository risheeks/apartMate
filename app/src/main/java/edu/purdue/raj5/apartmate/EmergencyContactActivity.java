package edu.purdue.raj5.apartmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmergencyContactActivity extends AppCompatActivity {
    RecyclerView rv_emergencyContacts;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mEmergencyContacts = new ArrayList<>();
    private ArrayList<String> mTempEmergencyContacts = new ArrayList<>();
    String groupName;
    String email;

    FloatingActionButton fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        email = intent.getExtras().getString("Email");
        initializeRecyclerView();
        initiaizeAddButton();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/EmergencyContacts");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                Log.e("Emergency",message);
                String emergencyContacts[] = message.split(";");
                mNames.clear();
                mEmergencyContacts.clear();

                for(int i = 0; i < emergencyContacts.length;i++)
                {
                    String[] contact = emergencyContacts[i].split(":");
                    mNames.add(contact[0]);
                    mEmergencyContacts.add(contact[1]);
                }
                Log.e("Size",String.valueOf(mNames.size()));
                Log.e("val","change");
                initializeRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    private void initializeRecyclerView(){
        rv_emergencyContacts = (RecyclerView)findViewById(R.id.rv_emergencyContacts);
        mTempEmergencyContacts = new ArrayList<>(mEmergencyContacts);
        EmergencyContactAdapter adaptor = new EmergencyContactAdapter(this, mNames, mEmergencyContacts, mTempEmergencyContacts, groupName, email);
        rv_emergencyContacts.setAdapter(adaptor);
        rv_emergencyContacts.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabEmergencyContact);
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
                        String emergency = et_interest.getText().toString();
                        Toast.makeText(EmergencyContactActivity.this, "The interest has been added", Toast.LENGTH_SHORT).show();
                        LoginActivity.sock.send("ADD_EMERGENCY;"+groupName+";"+email+";"+emergency);
                        mNames.add(email);
                        mEmergencyContacts.add(emergency);
                        initializeRecyclerView();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

}