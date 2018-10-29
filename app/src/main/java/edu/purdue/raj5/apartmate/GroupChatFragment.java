package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GroupChatFragment extends Fragment {
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
    String groupName = "friends";
    String friend1 = "friend1";
    String friend2 = "friend2";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_chat_tab, container, false);
        ChatBubbles = new ArrayList<>();

        listView = (ListView) getView().findViewById(R.id.list_msg);
        btnSend = getView().findViewById(R.id.btn_chat_send);
        editText = (EditText) getView().findViewById(R.id.msg_type);

        //set ListView adapter first
        adapter = new MessageAdapter(getActivity(), R.layout.left_chat_bubble, ChatBubbles);
        listView.setAdapter(adapter);
        /*File directory = getFilesDir();
        File[] files = directory.listFiles();
        new File(groupName).delete();*/
        try {
            FileOutputStream fos = getActivity().openFileOutput(groupName, Context.MODE_APPEND);
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
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                   Log.e("msg","Message is empty");//, Toast.LENGTH_SHORT).show();
                } else {
                    ChatBubbles.clear();
                    try {
                        FileOutputStream fos = getActivity().openFileOutput(groupName, Context.MODE_APPEND);
                        LoginActivity.sock.send("SEND_MESSAGE "+ LoginActivity.currentUser + " " + friend1+" "+editText.getText().toString());
                        fos.write(("Myself " + editText.getText().toString() + "\n").getBytes());
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        FileInputStream fis = getActivity().openFileInput(groupName);
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
        return rootView;
    }

}