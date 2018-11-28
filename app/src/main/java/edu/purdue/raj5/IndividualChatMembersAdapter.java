package com.example.dell.apartmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IndividualChatMembersAdapter extends RecyclerView.Adapter<IndividualChatMembersAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Bitmap> mDisplayPictures = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mLastMessages = new ArrayList<>();

    public IndividualChatMembersAdapter(Context mContext, ArrayList<Bitmap> mDisplayPictures, ArrayList<String> mNames, ArrayList<String> mLastMessages) {
        this.mContext = mContext;
        this.mDisplayPictures = mDisplayPictures;
        this.mNames = mNames;
        this.mLastMessages = mLastMessages;
    }


    @NonNull
    @Override
    public IndividualChatMembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_other_tabs,viewGroup,false);
        IndividualChatMembersAdapter.ViewHolder holder = new IndividualChatMembersAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));
        viewHolder.displayPicture.setImageBitmap(mDisplayPictures.get(i));
        viewHolder.lastMessage.setText(mLastMessages.get(i));
        viewHolder.ll_individualChatMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView displayPicture;
        TextView name;
        TextView lastMessage;
        LinearLayout ll_individualChatMembers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayPicture = (CircleImageView) itemView.findViewById(R.id.civ_chatMemberPhoto);
            name = (TextView) itemView.findViewById(R.id.tv_chatPersonName);
            lastMessage = (TextView) itemView.findViewById(R.id.tv_chatlastMessage);
            ll_individualChatMembers = (LinearLayout) itemView.findViewById(R.id.ll_chat_members);
        }
    }
}