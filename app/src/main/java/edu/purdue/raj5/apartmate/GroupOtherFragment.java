package edu.purdue.raj5.apartmate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
// This is where all the buttons are present
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other_tab, container, false);
        groupTabsActivity = (GroupTabsActivity) getActivity();
        groupName = groupTabsActivity.groupName;
        email = groupTabsActivity.email;
        // As the methods say, we are intializing all the recyclerviews
        // Each method will intent to different activities to perform their work.
        // Each class shows us a list of information
        // It is also separated by group members
        initializationChores(rootView);
        initializationGroceries(rootView);
        initializationSchedules(rootView);
        initializeReceipt(rootView);
        initializationBirthdays(rootView);
        initializationInterests(rootView);
        initializationShareablePossessions(rootView);
        initializationUnShareablePosessions(rootView);
        initializeEmergencyContact(rootView);
        return rootView;
    }
    // This is used for the emergency contacts. 
    // When we click the button, we are also sending group name and email to other activities. 
    private void initializeEmergencyContact(View rootView) {
        emergencyContact = (Button) rootView.findViewById(R.id.bt_emergency);
        emergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EmergencyContactActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email", email);
                startActivity(i);
            }
        });
    }

    // This is used for the interests. 
    // When we click the button, we are also sending group name and email to other activities. 
    public void initializationInterests(View v){
        interest = (Button) v.findViewById(R.id.bt_interest);
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InterestActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email", email);
                startActivity(i);
            }
        });
    }
    
    // This is used for the birthdays. 
    // When we click the button, we are also sending group name and email to other activities. 
    public void initializationBirthdays(View v){
        birthday = (Button) v.findViewById(R.id.bt_birthdays);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BirthdayActivity.class);
                i.putExtra("GroupName",groupName);
                startActivity(i);
            }
        });
    }
    
    // This is used for the Shareable Possessions. 
    // When we click the button, we are also sending group name and email to other activities. 
    public void initializationShareablePossessions(View v){
        shareablePossessions = (Button) v.findViewById(R.id.bt_sharePossessions);
        shareablePossessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShareablePossessionsActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email", email);
                startActivity(i);
            }
        });
    }
    
    // This is used for the unshareable possessions. 
    // When we click the button, we are also sending group name and email to other activities. 
    public void initializationUnShareablePosessions(View v){
        unshareablePossessions = (Button) v.findViewById(R.id.bt_sharePossessionsNo);
        unshareablePossessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UnshareablePossessionsActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email", email);
                startActivity(i);
            }
        });
    }

    // This is used for the receipt. 
    // When we click the button, we are also sending group name and email to other activities. 
    private void initializeReceipt(View rootView) {
        receipt = (Button) rootView.findViewById(R.id.bt_receipt);
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ReceiptActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email", email);
                startActivity(i);
            }
        });
    }

    // This is used for the schedule. 
    // When we click the button, we are also sending group name and email to other activities. 
    private void initializationSchedules(View rootView) {
        schedules = (Button) rootView.findViewById(R.id.bt_schedules);
        schedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RoommateScheduleActivity.class);
                i.putExtra("GroupName",groupName);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(i);
            }
        });
    }
    
    // This is used for the chores. 
    // When we click the button, we are also sending group name and email to other activities. 
    public void initializationChores(View v){
        chores = (Button) v.findViewById(R.id.bt_chores);
        chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChoresListActivity.class);
                i.putExtra("GroupName",groupName);
                startActivity(i);
            }
        });
    }
    
    // This is used for the groceries. 
    // When we click the button, we are also sending group name and email to other activities. 
    public void initializationGroceries(View v){
        grocery = (Button) v.findViewById(R.id.bt_grocery);
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GroceryListActivity.class);
                i.putExtra("GroupName",groupName);
                startActivity(i);
            }
        });
    }
}
