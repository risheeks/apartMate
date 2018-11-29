package edu.purdue.raj5.apartmate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


// This acts as the home page in the project. 

public class MenuActivity extends AppCompatActivity {
    ImageView optionsButton; // This is the options button in this page.
    private ArrayList<String> mNames = new ArrayList<>(); // This contains the group names
    private ArrayList<String> mImages = new ArrayList<>(); // This contains the group images
    ImageView iv_chatSearch; // This is the search image. This is how you converse with other people.
    TextView et_chatSearch; // This is where you type the email of the person you want to message
    TextView tv_message; // This is used if there are any errors.
    TextView tv_bill;
    String email;
    String groupName="a";
    private ArrayList<String> mMatches = new ArrayList<>();
    static final FirebaseDatabase dbStorage = FirebaseDatabase.getInstance();

    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        SharedPreferences preferences = this.getSharedPreferences("MyTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("theme", theme);
        editor.commit();
        iv_chatSearch = (ImageView) findViewById(R.id.iv_menuChatSearch);
        et_chatSearch = (TextView) findViewById(R.id.et_chatSearch);
        optionsButton = (ImageView) findViewById(R.id.iv_menuOptions);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
// The following code is used for theme preferences.
        String s = preferences.getString("theme", "");
        if (s.equals("dark")) {
            ll.setBackgroundColor(Color.DKGRAY);
            iv_chatSearch.setBackgroundColor(Color.WHITE);
            optionsButton.setBackgroundColor(Color.WHITE);

        } else {
            ll.setBackgroundColor(Color.WHITE);
            iv_chatSearch.setBackgroundColor(Color.GRAY);
            optionsButton.setBackgroundColor(Color.GRAY);
        }


    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        DummyTestActivity.mIsInForegroundMode = false;
        Log.e("Yo: ", "onDestroy: Well Menu ");
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
     //   LoginActivity.sock.send("SEND_NOTIFICATION;"+FirebaseInstanceId.getInstance().getToken()+";Welcome to ApartMate;Login Successful;");

        DummyTestActivity.mIsInForegroundMode = true;
        Log.e("Yo: ", "onCreate: Menu ");
        initializeTheme();
        initializeErrorMessage();
        Intent intent = getIntent();
        email = intent.getExtras().getString("Email");
        tv_bill = (TextView) findViewById(R.id.list_size);
        //  Toolbar mToolBar = (Toolbar)findViewById(R.id.tb_menu);
        //  setSupportActionBar(mToolBar);
        initializeMatchesList();
        final FirebaseDatabase storage = FirebaseDatabase.getInstance();
        final DatabaseReference storageRef = storage.getReference("Login/" + email.split("@")[0] + "/TotalAmountDue");
        Log.e("GN", email);
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                String remainingPossessions = "";
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();

                tv_bill.setText("Total due: $" + message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        LoginActivity.sock.setClientCallback(new Client.ClientCallback() {
            @Override
            public void onMessage(String message) {
                Log.e("Resp", message);
                if (message.contains("CREATE_GROUP;SUCCESS")) {
                    mImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
                    mNames.add(groupName);
                    Intent i = new Intent(MenuActivity.this, MenuActivity.class);
                    startActivity(i);
                } else if (message.contains("GET_GROUP;SUCCESS")) {
                    String groupName = message.split(";")[2];
                    mImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
                    mNames.add(groupName);
                    initializeRecyclerView();
                }
            }

            @Override
            public void onConnect(Socket socket) {
                LoginActivity.sock.send("Connected");

                //sock.disconnect();
            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {
                //  socket.close();
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Group", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                View viewDialog = LayoutInflater.from(MenuActivity.this).inflate(R.layout.add_group_item, null);
                final EditText et_groupName = (EditText) viewDialog.findViewById(R.id.et_groupNameAdd);
                final Button bt_choreItemAdd = (Button) viewDialog.findViewById(R.id.bt_groupCreate);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_choreItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        groupName = et_groupName.getText().toString();
                        // Toast.makeText(MenuActivity.this, "The item has been added", Toast.LENGTH_SHORT).show();
                        LoginActivity.sock.send("CREATE_GROUP;" + email + ";" + groupName);
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });
       // LoginActivity.sock.send("GET_GROUP;" + email);
        initializeGroup();
        initializeBitMaps();
        initializeRecyclerView();
        initializeOptions();
        initializeChatSearchComponents();
        createGroceryReminder();
        createEndOfLeaseReminder();
        createRoommateSearchReminder();

    }

    private void initializeGroup() {
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Login/"+email.split("@")[0]+"/Group");

        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                mImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
                mNames.add(message);
                initializeRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
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
        int year = 2018;
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
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE, 16);
            cal.set(Calendar.SECOND,30);
            createWeeklyReminders("EndOfLease",cal);
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
        int year = 2018;
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
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,23);
            cal.set(Calendar.MINUTE, 15);
            cal.set(Calendar.SECOND,25);
            createWeeklyReminders("RoommateSearch",cal);
        }

    }

    private void createGroceryReminder() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE, 14);
        calendar.set(Calendar.SECOND,30);
        createWeeklyReminders("Grocery",calendar);
    }

    private void createWeeklyReminders(String intentPurpose, Calendar calendar) {

        //calendar.set(Calendar.DAY_OF_WEEK,5);
        Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
        intent.putExtra("Class",intentPurpose);
        int x = (int)System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),x/Integer.MAX_VALUE,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),/*AlarmManager.INTERVAL_DAY*7*/ 90000,pendingIntent);
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

    // This method initializes the textView for chatText and the search Image. If a valid email was entered in the search, he should
// indent to chat Activity
    private void initializeChatSearchComponents() {
        iv_chatSearch = (ImageView) findViewById(R.id.iv_menuChatSearch);
        et_chatSearch = (TextView) findViewById(R.id.et_chatSearch);

        iv_chatSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, String.valueOf(et_chatSearch.getText()), Toast.LENGTH_SHORT).show();
                LoginActivity.sock.send("CHECK_USER " + et_chatSearch.getText().toString());
            }
        });
    }

    // This is an item in the recyclerView. It will include all the groups the user is part of.
    private void initializeBitMaps() {
        mImages.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Groups");
        mMatches.add("Matches");

    }
    private void initializeMatchesList()
    {
        final DatabaseReference majorRef = dbStorage.getReference("Login/"+email.split("@")[0]+"/Matches");
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                String[] arr = message.split(";");
                for(int i = 0; i < arr.length; i++)
                {
                    mMatches.add(arr[i]);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    // This initializes all the recyclerViews.
    private void initializeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_groupNames);
        MenuRecyclerViewAdaptor adaptor = new MenuRecyclerViewAdaptor(this, mNames, mImages, email);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView rv_matches = (RecyclerView)findViewById(R.id.rv_matches);
        MatchesAdapter adapter = new MatchesAdapter(this, mMatches);
        rv_matches.setAdapter(adapter);
        rv_matches.setMinimumHeight(10);
        rv_matches.setLayoutManager(new LinearLayoutManager(this));
    }

    // This initializes all the options. A pop-up menu is included in this.
    private void initializeOptions() {
        optionsButton = (ImageView) findViewById(R.id.iv_menuOptions);
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
                        if (item.getTitle().toString().equals("Profile")) {
                            Intent i = new Intent(getBaseContext(), ProfileActivity.class);
                            i.putExtra("Email", email);
                            startActivity(i);
                        }
                        if (item.getTitle().toString().equals("Schedule")) {
                            Intent i = new Intent(getBaseContext(), ScheduleActivity.class);
                            i.putExtra("Email", email);
                            startActivity(i);
                        }
                        if (item.getTitle().toString().equals("Dummy Test")) {
                            Intent i = new Intent(getBaseContext(), DummyTestActivity.class);
                            i.putExtra("Email", email);
                            startActivity(i);
                        }
                        if (item.getTitle().toString().equals("Logout")) {
                            Intent i = new Intent(getBaseContext(), LoginActivity.class);
                            i.putExtra("Email", email);
                            try {
                                LoginActivity.sock.disconnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            startActivity(i);
                        }
                        if (item.getTitle().toString().equals("Location")) {
                            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

                            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                double latitude=location.getLatitude();
                                double longitude=location.getLongitude();
                                Log.e("GPS","lat :  "+latitude);
                                Log.e("GPS","long :  "+longitude);
                                Geocoder geocoder;
                                List<Address> addresses = null;
                                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                String city = addresses.get(0).getLocality();
                                String state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName();
                                String fullAddress =  address+", "+city+", "+state+", "+country+" "+postalCode;
                                Toast.makeText(
                                        MenuActivity.this, "Address: "+fullAddress,
                                        Toast.LENGTH_SHORT
                                ).show();
                                LoginActivity.sock.send("SEND_LOCATION;" +email+";"+groupName+";"+fullAddress);

                            }
                        }
                        if (item.getTitle().toString().equals("Roommate Search")) {
                            Intent i = new Intent(getBaseContext(), FiltersActivity.class);
                            i.putExtra("Email",email);
                            startActivity(i);
                        }
                        return true;
                    }
                });

                popup.show(); //s
            }
        });
    }
// If there are any error messages, it will be posted in this textView.
    private void initializeErrorMessage(){
        tv_message = (TextView) findViewById(R.id.tv_menuMessage);
    }


}
