package com.example.dell.apartmate;



import android.app.AlarmManager;
import android.app.PendingIntent;
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
import java.util.Calendar;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    ImageView optionsButton;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    ImageView iv_chatSearch;
    TextView tv_chatSearch;
    TextView tv_message;
    RecyclerView rv_groups;

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
        createGroceryReminder();
        createRoommateSearchReminder();
        createEndOfLeaseReminder();

    }
    private void createEndOfLeaseReminder() {

        Calendar calendar = Calendar.getInstance();

        // set the calendar to start of today
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // and get that as a Date
        Date today = calendar.getTime();

        // user-specified date which you are testing
        // let's say the components come from a form or something
        int year = 2019;
        int month = 5;
        int dayOfMonth = 20;

        if(month == 1){
            month = 13;
        }
        // reuse the calendar to set user specified date
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        // and get that as a Date
        Date dateSpecified = calendar.getTime();

        // test your condition
        if (today.after(dateSpecified)) {
            //System.err.println("Date specified [" + dateSpecified + "] is before today [" + today + "]");
            createWeeklyReminders("EndOfLease",calendar);
        }


    }

    private void createRoommateSearchReminder() {
        Calendar calendar = Calendar.getInstance();

        // set the calendar to start of today
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // and get that as a Date
        Date today = calendar.getTime();


        // user-specified date which you are testing
        // let's say the components come from a form or something
        int year = 2019;
        int month = 5;
        int dayOfMonth = 20;

        if(month == 1){
            month = 13;
        }
        // reuse the calendar to set user specified date
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        // and get that as a Date
        Date dateSpecified = calendar.getTime();

        // test your condition
        if (today.after(dateSpecified)) {
            //System.err.println("Date specified [" + dateSpecified + "] is before today [" + today + "]");
            //System.out.println("DS: "+dateSpecified.get(Calendar.DAY_OF_MONTH));
            createWeeklyReminders("RoommateSearch",calendar);
        }

    }

    private void createGroceryReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,22);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND,30);
        createWeeklyReminders("Grocery",calendar);
    }

    private void createWeeklyReminders(String intentPurpose, Calendar calendar) {

        //calendar.set(Calendar.DAY_OF_WEEK,5);
        Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
        intent.putExtra("Class",intentPurpose);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),/*AlarmManager.INTERVAL_DAY*7*/ AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);
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
        rv_groups = (RecyclerView)findViewById(R.id.rv_groupNames);
        MenuRecyclerViewAdaptor adaptor = new MenuRecyclerViewAdaptor(this, mNames, mImages);
        rv_groups.setAdapter(adaptor);
        rv_groups.setLayoutManager(new LinearLayoutManager(this));
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