package edu.purdue.raj5.apartmate;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GroupTabsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public String groupName;
    public String email;
    public Client sock = LoginActivity.sock;
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        i.putExtra("Email",email);
        startActivity(i);
    }

    static String s;

    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        SharedPreferences preferences = this.getSharedPreferences("MyTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("theme", theme);
        editor.commit();
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.main_content);
// The following code is used for theme preferences.
        s = preferences.getString("theme", "");
        if (s.equals("dark")) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        email = intent.getExtras().getString("Email");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_group_tabs);
        tabLayout.setupWithViewPager(mViewPager,true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        initializeTheme();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroupTabsActivity.this,GroupChatActivity.class);
                i.putExtra("GroupName", groupName);
                i.putExtra("Email",email);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_add_to_group){
            final AlertDialog.Builder builder = new AlertDialog.Builder(GroupTabsActivity.this);
            View viewDialog = LayoutInflater.from(GroupTabsActivity.this).inflate(R.layout.add_member,null);
            final EditText et_addMember = (EditText) viewDialog.findViewById(R.id.et_memberAdd);
            final Button bt_memberAdd = (Button) viewDialog.findViewById(R.id.bt_memberAdd);
            builder.setView(viewDialog);
            final AlertDialog dialog = builder.create();
            bt_memberAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newMember = et_addMember.getText().toString();
                    Toast.makeText(GroupTabsActivity.this, "A new group member has been added", Toast.LENGTH_SHORT).show();
                    LoginActivity.sock.send("ADD_GROUP;"+newMember+";"+groupName);
                    dialog.dismiss();

                }
            });

            dialog.show();
        }
        if (id == R.id.action_leave_group) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GroupTabsActivity.this);

            builder.setTitle("Confirm");
            LinearLayout layout = new LinearLayout(this);
            TextView tv = new TextView(GroupTabsActivity.this);
            tv.setText("Are you sure you want to leave group?");
            layout.addView(tv);//"Are you sure you want to leave group?"));

            builder.setView(layout);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //server call to leave group. switch intent
                    LoginActivity.sock.send("LEAVE_GROUP;" + groupName + ";" + email);
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    i.putExtra("Email",email);
                    startActivity(i);
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            builder.show();
        }
        if (item.getTitle().toString().equals("Share Location")) {
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
                        GroupTabsActivity.this, "Address: "+fullAddress,
                        Toast.LENGTH_SHORT
                ).show();
                LoginActivity.sock.send("SEND_LOCATION;" +email+";"+groupName+";"+fullAddress);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    GroupChatFragment tab1 = new GroupChatFragment();
                    return tab1;
                case 1:
                    GroupTab02Fragment tab2 = new GroupTab02Fragment();
                    return tab2;
                case 2:
                    GroupOtherFragment tab3 = new GroupOtherFragment();
                    return tab3;
                default:
                    GroupChatFragment tab4 = new GroupChatFragment();
                    return tab4;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        @Override
        public CharSequence getPageTitle( int position){
            switch (position){
                case 0:
                    return "Group Chat";
                case 1:
                    return "Something";
                case 2:
                    return "Others";
                default:
                    return "Group Chat";

            }
        }
    }
}
