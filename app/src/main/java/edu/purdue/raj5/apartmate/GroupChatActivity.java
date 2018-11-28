package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class GroupChatActivity extends AppCompatActivity {
    private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean myMessage = true;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    private String sdefault = "Hey! How's Life?";
    //FileOutputStream outputStream;
    BufferedWriter bw;
    FileWriter fw;
    String groupName;
    String email;
    GroupTabsActivity groupTabsActivity;
    String friend1 = "friend1";
    String friend2 = "friend2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        ChatBubbles = new ArrayList<>();
        groupName = getIntent().getExtras().getString("GroupName");
        email = getIntent().getExtras().getString("Email");
        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);

        //set ListView adapter first
        adapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);
        listView.setAdapter(adapter);
        LoginActivity.sock.setClientCallback(new Client.ClientCallback(){

            @Override
            public void onMessage(String mess) {
                Log.e("Redcd",mess);
                try {

                    FileOutputStream fos = openFileOutput(groupName, Context.MODE_APPEND);
                    fos.write((mess.split(";")[1] + Calendar.getInstance().getTime().toString()+" "+ mess.substring(15) +"\n").getBytes());
                    fos.flush();
                    fos.close();
                    FileInputStream fis = openFileInput(groupName);
                    Scanner scanner = new Scanner(fis);
                    ChatBubble chatBubble;
                    String sender;
                    String message;
                    while (scanner.hasNext()) {
                        sender = scanner.next();
                        message = scanner.nextLine();
                        chatBubble = new ChatBubble(sender, message);
                        ChatBubbles.add(chatBubble);}
                    adapter.notifyDataSetChanged();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onConnect(Socket socket) throws IOException {

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {

            }

            @Override
            public void onConnectError(Socket socket, String message) {

            }
        });
        /*File directory = getFilesDir();
        File[] files = directory.listFiles();
        new File(groupName).delete();*/
        try {
            FileOutputStream fos = openFileOutput(groupName, Context.MODE_APPEND);
            fos.write((friend1 + " Hello!\n").getBytes());
            fos.write((friend2 + " Hello other friend!\n").getBytes());
            fos.flush();
            fos.close();
//            FileInputStream fis = openFileInput(friendName);
//            Scanner scanner = new Scanner(fis);
//            String sender = scanner.next();
//            scanner.close();
//            Toast.makeText(ChatActivity.this, sender, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChatBubbles.add(new ChatBubble(friend1, sdefault));
        ChatBubbles.add(new ChatBubble(friend2, sdefault));
        adapter.notifyDataSetChanged();
        //event for button SEND
        FirebaseDatabase stor = FirebaseDatabase.getInstance();
        DatabaseReference storageRe = stor.getReference("Groups/"+groupName+"/Chat");
        storageRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                try {

                    FileOutputStream fos = openFileOutput(groupName, Context.MODE_APPEND);
                    fos.write((message.split(";")[0] + Calendar.getInstance().getTime().toString()+" "+ message.substring(15) +"\n").getBytes());
                    fos.flush();
                    fos.close();
                    FileInputStream fis = openFileInput(groupName);
                    Scanner scanner = new Scanner(fis);
                    ChatBubble chatBubble;
                    String sender;
                    String mess;
                    while (scanner.hasNext()) {
                        sender = scanner.next();
                        mess = scanner.nextLine();
                        chatBubble = new ChatBubble(sender, mess);
                        ChatBubbles.add(chatBubble);}
                    adapter.notifyDataSetChanged();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Log.e("msg","Message is empty");//, Toast.LENGTH_SHORT).show();
                } else {
                    ChatBubbles.clear();
                    try {
                        FileOutputStream fos = openFileOutput(groupName, Context.MODE_APPEND);
                      //  LoginActivity.sock.send("SEND_GROUPM "+ email+ " " + groupName+" "+editText.getText().toString());
                        FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/Chat");
                        storageRef.setValue(email+";"+editText.getText().toString() + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });

    }

}