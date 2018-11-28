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

public class InterestActivity extends AppCompatActivity {
    RecyclerView rv_shareablePossessions;
    FloatingActionButton fab2;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mInterests = new ArrayList<>();
    private ArrayList<String> mTempInterests = new ArrayList<>();
    String groupName;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        final Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        email = intent.getExtras().getString("Email");
        //initializeRecyclerView();
        initiaizeAddButton();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/Interests");

        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                Log.e("Interests",message);
                String interests[] = message.split(";");
                mNames.clear();
                mInterests.clear();

                for(int i = 0; i < interests.length;i++)
                {
                    String[] interest = interests[i].split(":");
                    mNames.add(interest[0]);
                    mInterests.add(interest[1]);
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
        rv_shareablePossessions = (RecyclerView)findViewById(R.id.rv_interests);
        mTempInterests = new ArrayList<>(mInterests);
        ShareablePossessionsAdapter adapter = new ShareablePossessionsAdapter(this, mNames, mInterests,mTempInterests, groupName, email);
        rv_shareablePossessions.setAdapter(adapter);
        rv_shareablePossessions.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabInterests);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InterestActivity.this, "Add Interests",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(InterestActivity.this);
                View viewDialog = LayoutInflater.from(InterestActivity.this).inflate(R.layout.add_interest,null);
                final EditText et_interestAdd = (EditText) viewDialog.findViewById(R.id.et_interest);
                final Button bt_interestAdd = (Button) viewDialog.findViewById(R.id.bt_interestsAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_interestAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String interest = et_interestAdd.getText().toString();
                        Toast.makeText(InterestActivity.this, "An Interest has been added", Toast.LENGTH_SHORT).show();
                        LoginActivity.sock.send("ADD_INTEREST;"+groupName+";"+email+";"+interest);
                        mNames.add(email);
                        mInterests.add(interest);
                        initializeRecyclerView();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}