package com.example.dell.apartmate;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os. Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class GroupTab02Fragment extends Fragment{
    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        initializationRecyclerView(rootView);
        return rootView;
    }

    private void initializationRecyclerView(View view) {
        ArrayList<Bitmap> mDisplayPictures = new ArrayList<>();
        ArrayList<String> mNames = new ArrayList<>();
        ArrayList<String> mLastMessages = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        //Log.d("debugMode", "The application stopped after this");
        mRecyclerView.setLayoutManager(mLayoutManager);

        IndividualChatMembersAdapter mAdapter = new IndividualChatMembersAdapter(getActivity(), mDisplayPictures,mNames,mLastMessages);
        mRecyclerView.setAdapter(mAdapter);
    }

}
