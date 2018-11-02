package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class GroupChatFragment extends Fragment {
    private ListView listView;
    private View btnSend;
    //private EditText editText;
    //boolean myMessage = true;
    //private List<ChatBubble> ChatBubbles;
   // private ArrayAdapter<ChatBubble> adapter;
    //private String sdefault = "Hey! How's Life?";
    //FileOutputStream outputStream;
    //BufferedWriter bw;
    //FileWriter fw;
    String groupName;
    GroupTabsActivity groupTabsActivity;
    //String friend1 = "friend1";
    //String friend2 = "friend2";
    private Button b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_group_chat_tab, container, false);
        b = (Button) rootView.findViewById(R.id.bt_groupChat);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),GroupChatActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

}