package edu.purdue.raj5.apartmate;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        initializationChores(rootView);
        initializationGroceries(rootView);
        initializationSchedules(rootView);
        initializeReceipt(rootView);
        initializationBirthdays(rootView);
        initializationInterests(rootView);
        initializationShareablePossessions(rootView);
        initializationUnShareablePosessions(rootView);
        initializeEmergencyContact(rootView);
        initializationRoommateRating(rootView);
        return rootView;
    }

    private void initializationRoommateRating(View rootView) {
        roommateRating = (Button) rootView.findViewById(R.id.bt_rateRoommate);
        roommateRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RoommateRatingActivity.class);
                i.putExtra("GroupName",groupName);
                i.putExtra("Email", email);
                startActivity(i);
            }
        });
    }

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