package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class OtherFragmentAdapter extends RecyclerView.Adapter<OtherFragmentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    String groupName;
    String email;

    public OtherFragmentAdapter(Context mContext, ArrayList<String> mGroupNames, String groupName, String email) {
        this.mContext = mContext;
        this.mNames = mGroupNames;
        this.groupName = groupName;
        this.email = email;
    }

    @NonNull
    @Override
    public OtherFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_other_tabs,viewGroup,false);
        OtherFragmentAdapter.ViewHolder holder = new OtherFragmentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));

        viewHolder.ll_otherTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(mContext, ChoresListActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(mContext, GroceryListActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext, InterestActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(mContext, ShareablePossessionsActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(mContext, UnshareablePossessionsActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(mContext, BirthdayActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(mContext, ScheduleActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(mContext, ReceiptActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(mContext, EmergencyContactActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    case 9:
                        intent = new Intent(mContext, RoommateRatingActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("GroupName",groupName);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        LinearLayout ll_otherTab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_otherTabPurpose);
            ll_otherTab = (LinearLayout) itemView.findViewById(R.id.ll_otherTab);
        }
    }
}