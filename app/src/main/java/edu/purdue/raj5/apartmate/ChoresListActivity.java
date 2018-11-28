package edu.purdue.raj5.apartmate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.net.Socket;
import java.util.ArrayList;

public class ChoresListActivity extends AppCompatActivity {
    RecyclerView rv_chores;
    FloatingActionButton fab2;
    private ArrayList<String> mChoreNames = new ArrayList<>();
    private ArrayList<String> mChoreAssignee = new ArrayList<>();
    private ArrayList<String> mChoreDescription = new ArrayList<>();
    private ArrayList<String> mChoreDate = new ArrayList<>();
    private ArrayList<String> mChoreTime = new ArrayList<>();
    private ArrayList<String> mTempChoreNames = new ArrayList<>();
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);
        Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        mChoreNames.add("a");
        mChoreDescription.add("b");
        mChoreAssignee.add("l");
        mChoreDate.add("k");
        mChoreTime.add("0");
      //  initializeRecyclerView();
        initiaizeAddButton();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/Chores");

        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                Log.e("Chores",message);
                String chores[] = message.split(";");
                mChoreNames.clear();
                mChoreAssignee.clear();
                mChoreDescription.clear();
                mChoreDate.clear();
                mChoreTime.clear();

                for(int i = 0; i < chores.length;i++)
                {
                    String[] chore = chores[i].split(":");
                    mChoreNames.add(chore[0]);
                    mChoreAssignee.add(chore[1]);
                    mChoreDescription.add(chore[2]);
                    mChoreDate.add(chore[3]);
                    mChoreTime.add(chore[4]);
                }
                Log.e("Size",String.valueOf(mChoreNames.size()));
                Log.e("val","change");
                initializeRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabChoresAddItem);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChoresListActivity.this, "Add chore",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ChoresListActivity.this);
                View viewDialog = LayoutInflater.from(ChoresListActivity.this).inflate(R.layout.add_chores_item,null);
                final EditText et_choreName = (EditText) viewDialog.findViewById(R.id.et_choreNameAdd);
                final EditText et_choreWho = (EditText) viewDialog.findViewById(R.id.et_choreByWhoAdd);
                final EditText et_choreDescription = (EditText) viewDialog.findViewById(R.id.et_choreDescriptionAdd);
                final EditText et_choreDate = (EditText) viewDialog.findViewById(R.id.et_choreDateAdd);
                final EditText et_choreTime = (EditText) viewDialog.findViewById(R.id.et_choreTimeAdd);
                final Button bt_choreItemAdd = (Button) viewDialog.findViewById(R.id.bt_choreAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_choreItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String choreItem = et_choreName.getText().toString();
                        final String choreWho = et_choreWho.getText().toString();
                        final String choreDescription = et_choreDescription.getText().toString();
                        final String choreDate = et_choreDate.getText().toString();
                        final String choreTime = et_choreTime.getText().toString();
                        Toast.makeText(ChoresListActivity.this, "The item has been added", Toast.LENGTH_SHORT).show();
                        LoginActivity.sock.send("ADD_CHORE;"+groupName+";"+choreItem+";"+choreWho+";"+choreDescription+";"+choreDate+";"+choreTime);
                        LoginActivity.sock.send("ADD_EVENT;"+choreWho+";"+choreDate+";"+choreItem+";"+choreDescription+";"+choreTime);
                        mChoreNames.add(choreItem);
                        mChoreAssignee.add(choreWho);
                        mChoreDescription.add(choreDescription);
                        mChoreDate.add(choreDate);
                        mChoreTime.add(choreTime);
                        initializeRecyclerView();
                        dialog.dismiss();



                    }
                });

                dialog.show();
            }
        });
    }

    private void initializeRecyclerView(){
        rv_chores = (RecyclerView)findViewById(R.id.rv_choresItems);
        mTempChoreNames = new ArrayList<>(mChoreNames);
        ChoresAdaptor adaptor = new ChoresAdaptor(this, mChoreNames, mChoreAssignee,mChoreDescription,mChoreDate,mChoreTime,groupName,mTempChoreNames);
        rv_chores.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
        rv_chores.setLayoutManager(new LinearLayoutManager(this));
    }
}