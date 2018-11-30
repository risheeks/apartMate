package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
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


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
// Adaptor is something that is absolutely needed for a recyclerView
public class MenuRecyclerViewAdaptor extends RecyclerView.Adapter<MenuRecyclerViewAdaptor.ViewHolder> {
    private static final String TAG = "MenuRecyclerViewAdaptor";
    private ArrayList<String> mGroupNames = new ArrayList<>(); // All the group Names as the name says.
    private ArrayList<String> mGroupPhotos = new ArrayList<>(); // All the group photos as the name says.
    private Context mContext;
    String email;
    //Constructor of the MenuRecyclerViewAdaptor class
    public MenuRecyclerViewAdaptor(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mGroupPhotos, String email) {
        this.mGroupNames = mGroupNames;
        this.mGroupPhotos = mGroupPhotos;
        this.mContext = mContext;
        this.email = email;
    }
// These are the three methods that needs to be implemented. 
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
                // Currently just included the next line for 
                Intent intent = new Intent(mContext,GroupTabsActivity.class);
                intent.putExtra("GroupName",mGroupNames.get(i));
                intent.putExtra("Email",email);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mGroupNames.size();
    }
    // This viewholder is used to assist the xml components.
    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView groupPhoto;
        TextView groupName;
        LinearLayout llMenuGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupPhoto = itemView.findViewById(R.id.iv_groupPhoto);
            groupName = itemView.findViewById(R.id.tv_groupName);
            llMenuGroup = itemView.findViewById(R.id.ll_menuGroup);
            if (MenuActivity.s.equals("dark")) {
                llMenuGroup.setBackgroundColor(Color.DKGRAY);
                groupName.setTextColor(Color.WHITE);
            } else {
                llMenuGroup.setBackgroundColor(Color.WHITE);
                groupName.setTextColor(Color.BLACK);
            }
        }
    }
}
