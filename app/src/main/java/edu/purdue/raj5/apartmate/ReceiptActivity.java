<<<<<<< HEAD
package edu.purdue.raj5.apartmate;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
=======
package com.example.risheek.apartmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ReceiptActivity extends AppCompatActivity {

    ArrayList<String> spinnerValues = new ArrayList<>();// = { "Blur", "NFS", "Burnout","GTA IV", "Racing", };
=======

public class ReceiptActivity extends AppCompatActivity {

    String[] spinnerValues = { "Blur", "NFS", "Burnout","GTA IV", "Racing", };
>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff
    private View btnSend;
    private View btnCancel;
    private EditText title;
    private EditText amount;
    private MultiSelectionSpinner spinner;
<<<<<<< HEAD
    String groupName;
    String email;
=======
>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
<<<<<<< HEAD
        groupName = getIntent().getExtras().getString("GroupName");
        email = getIntent().getExtras().getString("Email");
=======

>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff
        btnSend = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        title = findViewById(R.id.receiptTitle);
        amount = findViewById(R.id.receiptAmount);
        spinner = findViewById(R.id.members);
<<<<<<< HEAD
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        // Create a storage reference from our app
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/Members");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue().toString();
                int j = 0;
                String[] temp = message.split(";");
                Log.e("Temp",temp[0]);
                for(int i = 0; i < temp.length; i++)
                {
                    Log.e("member",String.valueOf(temp[i]));
                    spinnerValues.add(temp[i]);
                }
                spinner.setItems(spinnerValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

=======
        spinner.setItems(spinnerValues);
>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mytitle = title.getText().toString();
                String myamount = amount.getText().toString();
                String members= spinner.getSelectedItemsAsString();
<<<<<<< HEAD
               // Toast.makeText(ReceiptActivity.this, mytitle + "\n" + myamount + "\n" + members, Toast.LENGTH_SHORT).show();
                LoginActivity.sock.send("ADD_RECEIPT;"+groupName+";"+email+";"+mytitle+";"+myamount+";"+members);
                Intent i = new Intent(ReceiptActivity.this, GroupTabsActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email",email);
                startActivity(i);
=======
                Toast.makeText(ReceiptActivity.this, mytitle + "\n" + myamount + "\n" + members, Toast.LENGTH_SHORT).show();
>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                //Toast.makeText(ReceiptActivity.this, "Cancel and redirect to the tabs activity in the group", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ReceiptActivity.this, GroupTabsActivity.class);
                startActivity(i);
            }
        });
    }
}
=======
                Toast.makeText(ReceiptActivity.this, "Cancel and redirect to the tabs activity in the group", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
>>>>>>> cc35bd6d648bb2cba0320706723c527d3be71aff
