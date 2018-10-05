package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuActivity extends AppCompatActivity {
    ImageView optionsButton;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    ImageView iv_chatSearch;
    TextView et_chatSearch;
    TextView tv_message;
    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        SharedPreferences preferences = this.getSharedPreferences("MyTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("theme", theme);
        editor.commit();
        iv_chatSearch = (ImageView)findViewById(R.id.iv_menuChatSearch);
        et_chatSearch = (TextView)findViewById(R.id.et_chatSearch);
        optionsButton = (ImageView)findViewById(R.id.iv_menuOptions);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);

        String s = preferences.getString("theme", "");
        if(s.equals("dark")) {
            ll.setBackgroundColor(Color.DKGRAY);
            iv_chatSearch.setBackgroundColor(Color.WHITE);
            optionsButton.setBackgroundColor(Color.WHITE);

        }else {
            ll.setBackgroundColor(Color.WHITE);
            iv_chatSearch.setBackgroundColor(Color.GRAY);
            optionsButton.setBackgroundColor(Color.GRAY);
        }


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initializeTheme();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeTheme();
        initializeErrorMessage();
      //  Toolbar mToolBar = (Toolbar)findViewById(R.id.tb_menu);
      //  setSupportActionBar(mToolBar);

        LoginActivity.sock.setClientCallback(new Client.ClientCallback () {
            @Override
            public void onMessage(String message) {
                if(message.equals("CHECK_USER ACCOUNT_EXISTS")){
                    Intent i = new Intent(MenuActivity.this, ChatActivity.class);
                   i.putExtra("Email",et_chatSearch.getText().toString());
                    startActivity(i);
                }
                else{
                    tv_message.setText("Account Does Not Exist");
                }
            }

            @Override
            public void onConnect(Socket socket)  {
                LoginActivity.sock.send("Connected");

                //sock.disconnect();
            }

            @Override
            public void onDisconnect(Socket socket, String message)  {


            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });
        initializeBitMaps();
        initializeRecyclerView();
        initializeOptions();
        initializeChatSearchComponents();
    }

    private void initializeTheme() {
        String theme="";
        try {
            FileInputStream fis = openFileInput("theme");
            Scanner scanner = new Scanner(fis);
            theme = scanner.next();
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(theme.contains("dark"))
            getAppTheme("dark");

        else
            getAppTheme("light");
    }

    private void initializeChatSearchComponents(){
        iv_chatSearch = (ImageView)findViewById(R.id.iv_menuChatSearch);
        et_chatSearch = (TextView)findViewById(R.id.et_chatSearch);

        iv_chatSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, String.valueOf(et_chatSearch.getText()),Toast.LENGTH_SHORT).show();
                LoginActivity.sock.send("CHECK_USER "+et_chatSearch.getText().toString());
            }
        });
    }
    private void initializeBitMaps(){
        mImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Wassup fellas");
    }
    private void initializeRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_groupNames);
        MenuRecyclerViewAdaptor adaptor = new MenuRecyclerViewAdaptor(this, mNames, mImages);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initializeOptions(){
        optionsButton = (ImageView)findViewById(R.id.iv_menuOptions);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MenuActivity.this, optionsButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                MenuActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        if(item.getTitle().toString().equals("Profile")){
                            Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                            startActivity(i);
                        }
                        if(item.getTitle().toString().equals("Schedule")){
                            Intent i = new Intent(getBaseContext(), ScheduleActivity.class);
                            startActivity(i);
                        }
                        return true;
                    }
                });

                popup.show(); //s
            }
        });
    }

    private void initializeErrorMessage(){
        tv_message = (TextView) findViewById(R.id.tv_menuMessage);
    }


}