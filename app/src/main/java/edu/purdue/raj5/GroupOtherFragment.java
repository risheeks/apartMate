package com.example.dell.apartmate;
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
    Button interest;
    Button birthday;
    Button shareablePossessions;
    Button unshareablePossessions;
    Button schedule;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other_tab, container, false);
        initializationChores(rootView);
        initializationGroceries(rootView);
        initializationInterests(rootView);
        initializationBirthdays(rootView);
        initializationSchedule(rootView);
        initializationShareablePossessions(rootView);
        initializationUnShareablePosessions(rootView);
        return rootView;
    }

    public void initializationChores(View v){
        chores = (Button) v.findViewById(R.id.bt_chores);
        chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChoresListActivity.class);
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
                startActivity(i);
            }
        });
    }
    public void initializationBirthdays(View v){
        birthday = (Button) v.findViewById(R.id.bt_birthdays);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InterestActivity.class);
                startActivity(i);
            }
        });
    }
    public void initializationSchedule(View v){
        schedule = (Button) v.findViewById(R.id.bt_schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InterestActivity.class);
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
                startActivity(i);
            }
        });
    }
}
