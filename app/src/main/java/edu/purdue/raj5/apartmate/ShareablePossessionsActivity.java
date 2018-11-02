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


/*
 * Allows the user to let others in the group know about which possessions of theirs can be
 * shared and which cannot be shared amongst them
 *
 */
public class ShareablePossessionsActivity extends AppCompatActivity {
    RecyclerView rv_shareablePossessions;
    FloatingActionButton fab2;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mShareablePossessions = new ArrayList<>();
    private ArrayList<String> mTempShareablePossessions = new ArrayList<>();
    String groupName;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareable_possessions);
        Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        email = intent.getExtras().getString("Email");
        //initializeRecyclerView();
        initiaizeAddButton();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/ShareablePossessions");

        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                Log.e("ShareablePossessions",message);
                String shareables[] = message.split(";");
                mNames.clear();
                mShareablePossessions.clear();

                for(int i = 0; i < shareables.length;i++)
                {
                    String[] shareable = shareables[i].split(":");
                    mNames.add(shareable[0]);
                    mShareablePossessions.add(shareable[1]);
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
        rv_shareablePossessions = (RecyclerView)findViewById(R.id.rv_shareablePossessions);
        mTempShareablePossessions = new ArrayList<>(mShareablePossessions);
        ShareablePossessionsAdapter adapter = new ShareablePossessionsAdapter(this, mNames, mShareablePossessions,mTempShareablePossessions, groupName, email);
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
                        LoginActivity.sock.send("ADD_SHAREABLEPOSSESSION;"+groupName+";"+email+";"+shareablePossession);
                        mNames.add(email);
                        mShareablePossessions.add(shareablePossession);
                        initializeRecyclerView();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
}
