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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other_tab, container, false);
        initializationChores(rootView);
        initializationGroceries(rootView);
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
}