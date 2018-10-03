package com.example.dell.apartmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.apartmate.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuRecyclerViewAdaptor extends RecyclerView.Adapter<MenuRecyclerViewAdaptor.ViewHolder> {
    private static final String TAG = "MenuRecyclerViewAdaptor";
    private ArrayList<String> mGroupNames = new ArrayList<>();
    private ArrayList<String> mGroupPhotos = new ArrayList<>();
    private Context mContext;

    public MenuRecyclerViewAdaptor(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mGroupPhotos) {
        this.mGroupNames = mGroupNames;
        this.mGroupPhotos = mGroupPhotos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_list,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext).asBitmap().load(mGroupPhotos.get(i)).into(viewHolder.groupPhoto);
        viewHolder.groupName.setText(mGroupNames.get(i));
        viewHolder.llMenuGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+mGroupNames.get(i));
                Toast.makeText(mContext, mGroupNames.get(i), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mGroupNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView groupPhoto;
        TextView groupName;
        LinearLayout llMenuGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupPhoto = itemView.findViewById(R.id.iv_groupPhoto);
            groupName = itemView.findViewById(R.id.tv_groupName);
            llMenuGroup = itemView.findViewById(R.id.ll_menuGroup);
        }
    }
}
