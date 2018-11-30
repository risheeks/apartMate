package edu.purdue.raj5.apartmate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class GroupOtherFragment extends Fragment{
    Button chores;
    Button grocery;
    Button schedules;
    Button receipt;
    GroupTabsActivity groupTabsActivity;
    String groupName;
    String email;
    Button interest;
    Button birthday;
    Button shareablePossessions;
    Button unshareablePossessions;
    Button emergencyContact;
    Button roommateRating;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other_tab, container, false);
        groupTabsActivity = (GroupTabsActivity) getActivity();
        groupName = groupTabsActivity.groupName;
        email = groupTabsActivity.email;
        initializeTheme();
       // initializationChores(rootView);
        // initializationGroceries(rootView);
        //initializationSchedules(rootView);
        //initializeReceipt(rootView);
        //initializationBirthdays(rootView);
        //initializationInterests(rootView);
        //initializationShareablePossessions(rootView);
        //initializationUnShareablePosessions(rootView);
        //initializeEmergencyContact(rootView);
        //initializationRoommateRating(rootView);
        initializationRecyclerView(rootView);
        return rootView;
    }

    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.other_tab);
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

    private void initializationRecyclerView(View view) {
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_otherTab);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        //Log.d("debugMode", "The application stopped after this");
        mRecyclerView.setLayoutManager(mLayoutManager);

        OtherFragmentAdapter mAdapter = new OtherFragmentAdapter(getActivity(), getNames(),groupName,email);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<String> getNames() {
        ArrayList<String> names =new ArrayList<>();
        names.add("Chores");
        names.add("Grocery");
        names.add("Interest");
        names.add("Shareable Possessions");
        names.add("Unshareable Possessions");
        names.add("Birthdays");
        names.add("Schedule");
        names.add("Receipt");
        names.add("Emergency Contact");
        names.add("Rate Roommate");
        return names;
    }
}