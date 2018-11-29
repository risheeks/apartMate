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

    /*private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean myMessage = true;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    private String sdefault = "Hey! How's Life?";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();
    //FileOutputStream outputStream;
    BufferedWriter bw;
    FileWriter fw;
    String friendName;
    CircleImageView profilePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        profilePhoto = (CircleImageView)findViewById(R.id.civ_chatImage);
        friendName = b.getString("Email");
        StorageReference ref = storageRef.child("profile"+friendName+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                profilePhoto.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        ChatBubbles = new ArrayList<>();
        LoginActivity.sock.setClientCallback(new Client.ClientCallback(){

            @Override
            public void onMessage(String mess) {
                try {
                    if(mess.contains("CHECK_USER ACCOUNT_EXISTS")){
                        Intent i = new Intent(ChatActivity.this,ChatActivity.class);
                        i.putExtra("Email",friendName);
                        startActivity(i);
                    }
                    FileOutputStream fos = openFileOutput(friendName, Context.MODE_APPEND);
                    fos.write((friendName + Calendar.getInstance().getTime().toString()+" "+ mess.substring(15) +"\n").getBytes());
                    fos.flush();
                    fos.close();
                    FileInputStream fis = openFileInput(friendName);
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

        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);

        //set ListView adapter first
        adapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);
        listView.setAdapter(adapter);
        File directory = getFilesDir();
        File[] files = directory.listFiles();
        new File(friendName).delete();

        ChatBubbles.add(new ChatBubble(friendName, sdefault));
        adapter.notifyDataSetChanged();
        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "Message is empty", Toast.LENGTH_SHORT).show();
                } else {
                    ChatBubbles.clear();
                    try {
                        FileOutputStream fos = openFileOutput(friendName, Context.MODE_APPEND);
                        Toast.makeText(getBaseContext(), LoginActivity.currentUser, Toast.LENGTH_LONG).show();
                        LoginActivity.sock.send("SEND_MESSAGE "+ LoginActivity.currentUser + " " + friendName+" "+editText.getText().toString());
                        Thread.sleep(150);
                        fos.write(("Myself " + Calendar.getInstance().getTime().toString()+" "+ editText.getText().toString() + "\n").getBytes());
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        FileInputStream fis = openFileInput(friendName);
                        Scanner scanner = new Scanner(fis);
                        ChatBubble chatBubble;
                        String sender;
                        String message;
                        while (scanner.hasNext()) {
                            sender = scanner.next();
                            message = scanner.nextLine();
                            chatBubble = new ChatBubble(sender, message);
                            ChatBubbles.add(chatBubble);
                        }
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });
    }*/

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
    //String groupName;
    String email;
    //GroupTabsActivity groupTabsActivity;
    String friend1 = "friend1";
    String friend2 = "friend2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ChatBubbles = new ArrayList<>();
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

                    FileOutputStream fos = openFileOutput(email, Context.MODE_APPEND);
                    fos.write((mess.split(";")[1] + Calendar.getInstance().getTime().toString()+" "+ mess.substring(15) +"\n").getBytes());
                    fos.flush();
                    fos.close();
                    FileInputStream fis = openFileInput(email);
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
            FileOutputStream fos = openFileOutput(email, Context.MODE_APPEND);
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
        DatabaseReference storageRe = stor.getReference(email);
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
                        FileOutputStream fos = openFileOutput(email, Context.MODE_APPEND);
                      //  LoginActivity.sock.send("SEND_GROUPM "+ email+ " " + groupName+" "+editText.getText().toString());
                        FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        DatabaseReference storageRef = storage.getReference(email);
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