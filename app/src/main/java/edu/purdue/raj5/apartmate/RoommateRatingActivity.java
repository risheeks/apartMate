package edu.purdue.raj5.apartmate;


import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoommateRatingActivity extends AppCompatActivity {
    RecyclerView rv_roommateRatings;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mRating = new ArrayList<>();
    private ArrayList<String> mRatingComments = new ArrayList<>();
    String groupName;
    String[] members;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupName = getIntent().getExtras().getString("GroupName");
        email = getIntent().getExtras().getString("Email");
        setContentView(R.layout.activity_roommate_rating);
        initializeRecyclerView();

    }
    private void initializeRecyclerView(){
        rv_roommateRatings= (RecyclerView)findViewById(R.id.rv_roommateRating);
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/Members");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                members = message.split(";");
                for(int i = 0; i < members.length - 1; i++) {
                  if (members[i] != email) {
                      final int x = i;
                      FirebaseDatabase storage = FirebaseDatabase.getInstance();
                      DatabaseReference storageRef = storage.getReference("Login/" + email.split("@")[0] + "/gRating/" + members[i].split("@")[0]);
                      storageRef.addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot dataSnapshot) {
                              Log.e("in","rating");
                              String message;
                              if (dataSnapshot.getValue() == null)
                                  message = "";
                              else
                                  message = dataSnapshot.getValue().toString();
                              Log.e("rating", members[x]+":"+message);
                              mNames.add(members[x]);
                              mRating.add(message);
                              mRatingComments.add("Roommate Rating");
                              if(x == members.length-1) {
                                 init();
                              }
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {
                              System.out.println("The read failed: " + databaseError.getCode());
                          }
                      });
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
    void init()
    {
        RoommateRatingAdapter adaptor = new RoommateRatingAdapter(RoommateRatingActivity.this, mNames, mRating, mRatingComments, groupName, email);
        rv_roommateRatings.setAdapter(adaptor);
        rv_roommateRatings.setLayoutManager(new LinearLayoutManager(RoommateRatingActivity.this));
    }
}
