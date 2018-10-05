package com.example.dell.apartmate;



import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    ImageView optionsButton;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    ImageView iv_chatSearch;
    TextView tv_chatSearch;
    TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //  Toolbar mToolBar = (Toolbar)findViewById(R.id.tb_menu);
        //  setSupportActionBar(mToolBar);

        initializeBitMaps();
        initializeRecyclerView();
        initializeOptions();
        initializeChatSearchComponents();
        initializeErrorMessage();
    }
    private void initializeChatSearchComponents(){
        iv_chatSearch = (ImageView)findViewById(R.id.iv_menuChatSearch);
        tv_chatSearch = (TextView)findViewById(R.id.tv_chatSearch);

        iv_chatSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, String.valueOf(tv_chatSearch.getText()),Toast.LENGTH_SHORT).show();
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
