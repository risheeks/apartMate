package edu.purdue.raj5.apartmate;


import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.net.Socket;
import java.util.ArrayList;

public class RoommateScheduleActivity extends AppCompatActivity {
    //TODO add a schedule button in the misc tab to open this activity
    TableLayout mTlayout;
    TableRow tr;
    //TODO get roommates
    ArrayList<String> mTextofButton = new ArrayList<>();// = { "Risheek", "Adrian", "Ian", "Akshay", "Sid" };
    String groupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommate_schedule);
        Intent i = getIntent();
        groupName = i.getExtras().getString("GroupName");
//        LoginActivity.sock.setClientCallback(new Client.ClientCallback () {
//            @Override
//            public void onMessage(String message) {
//                Log.e("RespSCHEDULE",message);
//
//                int j = 0;
//                String[] temp = message.split(";");
//                Log.e("Temp",temp[0]);
//                for(int i = 2; i < temp.length; i++)
//                {
//                    Log.e("member",String.valueOf(temp[i]));
//                    mTextofButton[j++] = temp[i];
//                    break;
//                }
//                Log.e("Count",String.valueOf(mTextofButton.length));
//              //  init();
//            }
//            @Override
//            public void onConnect(Socket socket)  {
//                LoginActivity.sock.send("Connected");
//                //sock.disconnect();
//            }
//            @Override
//            public void onDisconnect(Socket socket, String message)  {
//
//            }
//            @Override
//            public void onConnectError(Socket socket, String message) {
//
//            }
//        });
//       /* LoginActivity.sock.send("GET_GROUPMEMBERS;"+groupName);
//        LoginActivity.sock.send("GET_GROUPMEMBERS;"+groupName);
//        LoginActivity.sock.send("GET_GROUPMEMBERS;"+groupName);*/
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
                        mTextofButton.add(temp[i]);
                }
                init();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //init();



    }

    public void init() {
        //mTlayout = (TableLayout) findViewById(R.id.table_main);
        Log.e("Test","Call");
        int i = 0;
        mTlayout = (TableLayout) findViewById(R.id.table_main);
        while (i < mTextofButton.size()) {
            if (i % 1 == 0) {
                tr = new TableRow(this);
                mTlayout.addView(tr);
            }
          //  Log.e("buttonsLength", String.valueOf(mTextofButton.length));
            Button btn = new Button(this);
            btn.setText(mTextofButton.get(i));
            btn.setId(i);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //TODO Add event for the button
                    //System.out.println("v.getid is:- " + v.getId());
                    Button btn = (Button)v;
                    Intent i = new Intent(RoommateScheduleActivity.this, ScheduleActivity.class);
                    i.putExtra("Email", btn.getText().toString());
                    startActivity(i);
                }
            });
            tr.addView(btn);
            i++;
        }
    }
}