package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 *   The Java class that supports the chat component
 */
public class ChatActivity extends AppCompatActivity {


    private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean myMessage = true;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;

    //FileOutputStream outputStream;
    BufferedWriter bw;
    FileWriter fw;
    //String groupName;
    String email;
    String myEmail;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        i.putExtra("Email",myEmail);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ChatBubbles = new ArrayList<>();
        email = getIntent().getExtras().getString("ToEmail");
        myEmail = getIntent().getExtras().getString("MyEmail");
        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);

        //set ListView adapter first
        adapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);
        listView.setAdapter(adapter);
        FileInputStream fis = null;
        try {
            fis = openFileInput(email);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(fis != null) {
            Scanner scanner = new Scanner(fis);
            ChatBubble chatBubble;
            String sender;
            String mess;
            while (scanner.hasNext()) {
                sender = scanner.next().split("@")[0];
                mess = scanner.nextLine().split(";")[1];
                chatBubble = new ChatBubble(sender, mess);
                ChatBubbles.add(chatBubble);
            }
            adapter.notifyDataSetChanged();
        }
        /*File directory = getFilesDir();
        File[] files = directory.listFiles();
        new File(groupName).delete();*/

        //event for button SEND
        FirebaseDatabase stor = FirebaseDatabase.getInstance();
        DatabaseReference storageRe = stor.getReference("Login/"+email.split("@")[0]+"/To"+myEmail.split("@")[0]);
        storageRe.setValue("");
        storageRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                try {

                    FileOutputStream fos = openFileOutput(email, Context.MODE_APPEND);
                    fos.write((message.split(";")[0] + Calendar.getInstance().getTime().toString()+" "+ message.substring(15) +"\n").getBytes());
                    fos.flush();
                    fos.close();
                    FileInputStream fis = openFileInput(email);
                    Scanner scanner = new Scanner(fis);
                    ChatBubble chatBubble;
                    String sender;
                    String mess;
                    while (scanner.hasNext()) {
                        sender = scanner.next().split("@")[0];
                        mess = scanner.nextLine().split(";")[1];
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

        FirebaseDatabase stor1 = FirebaseDatabase.getInstance();
        DatabaseReference storageRe1 = stor1.getReference("Login/"+myEmail.split("@")[0]+"/To"+email.split("@")[0]);
        storageRe1.setValue("");
        storageRe1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                try {

                    FileOutputStream fos = openFileOutput(email, Context.MODE_APPEND);
                    fos.write((message.split(";")[0] + Calendar.getInstance().getTime().toString()+" "+ message.substring(15) +"\n").getBytes());
                    fos.flush();
                    fos.close();
                    FileInputStream fis = openFileInput(email);
                    Scanner scanner = new Scanner(fis);
                    ChatBubble chatBubble;
                    String sender;
                    String mess;
                    while (scanner.hasNext()) {
                        sender = scanner.next().split("@")[0];
                        mess = scanner.nextLine().split(";")[1];
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
                    try {
                        //  LoginActivity.sock.send("SEND_GROUPM "+ email+ " " + groupName+" "+editText.getText().toString());
                        FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        DatabaseReference storageRef = storage.getReference("Login/"+myEmail.split("@")[0]+"/To"+email.split("@")[0]);
                        storageRef.setValue(myEmail+";"+editText.getText().toString() + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editText.setText("");
                }
            }
        });

    }




}