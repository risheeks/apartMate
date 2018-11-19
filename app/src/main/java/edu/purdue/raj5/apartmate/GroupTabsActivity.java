package edu.purdue.raj5.apartmate;

import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
