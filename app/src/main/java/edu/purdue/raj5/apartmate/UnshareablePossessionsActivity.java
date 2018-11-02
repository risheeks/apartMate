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

public class UnshareablePossessionsActivity extends AppCompatActivity {
    RecyclerView rv_unshareablePossessions;
    FloatingActionButton fab2;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mUnshareablePossessions = new ArrayList<>();
    private ArrayList<String> mTempUnshareablePossessions = new ArrayList<>();
    String groupName;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unshareable_possessions);
        Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        email = intent.getExtras().getString("Email");
        //initializeRecyclerView();
        initiaizeAddButton();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/UnshareablePossessions");

        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                Log.e("UnshareablePossessions",message);
                String unshareables[] = message.split(";");
                mNames.clear();
                mUnshareablePossessions.clear();
                    for (int i = 0; i < unshareables.length; i++) {
                        String[] unshareable = unshareables[i].split(":");
                        mNames.add(unshareable[0]);
                        mUnshareablePossessions.add(unshareable[1]);

                    Log.e("Size", String.valueOf(mNames.size()));
                    Log.e("val", "change");
                }
                initializeRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    private void initializeRecyclerView(){
        rv_unshareablePossessions = (RecyclerView)findViewById(R.id.rv_unshareablePossessions);
        mTempUnshareablePossessions = new ArrayList<>(mUnshareablePossessions);
        UnshareablePossessionsAdapter adapter = new UnshareablePossessionsAdapter(this, mNames, mUnshareablePossessions, mTempUnshareablePossessions, groupName, email);
        rv_unshareablePossessions.setAdapter(adapter);
        rv_unshareablePossessions.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabUnshareablePossession);
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
                        String unshareablePossession = et_unshareablePossessionsAdd.getText().toString();
                        Toast.makeText(UnshareablePossessionsActivity.this, "Add Unshareable Possessio", Toast.LENGTH_SHORT).show();
                        LoginActivity.sock.send("ADD_UNSHAREABLEPOSSESSION;"+groupName+";"+email+";"+unshareablePossession);
                        mNames.add(email);
                        mUnshareablePossessions.add(unshareablePossession);
                        initializeRecyclerView();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}