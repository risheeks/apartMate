package edu.purdue.raj5.apartmate;

import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GroupOtherFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other_tab, container, false);
        return rootView;
    }
}