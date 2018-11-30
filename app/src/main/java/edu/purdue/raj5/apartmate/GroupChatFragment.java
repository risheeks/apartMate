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
        initializeTheme();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),GroupChatActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.chat_tab);
        // The following code is used for theme preferences.
        if (groupTabsActivity.s.equals("dark")) {
            ll.setBackgroundColor(Color.DKGRAY);

        } else {
            ll.setBackgroundColor(Color.WHITE);
        }


    }

    // This method is called in the onCreate. This is used to set theme according to the user's preferences.
    private void initializeTheme() {
        String theme = "";
        try {
            FileInputStream fis = openFileInput("theme");
            Scanner scanner = new Scanner(fis);
            theme = scanner.next();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (theme.contains("dark"))
            getAppTheme("dark");

        else
            getAppTheme("light");
    }

}