
package edu.purdue.raj5.apartmate;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.purdue.raj5.apartmate.R;

public class ChoresAdaptor extends RecyclerView.Adapter<ChoresAdaptor.ViewHolder>  {
    private Context mContext;
    private ArrayList<String> mChoreNames = new ArrayList<>();
    private ArrayList<String> mChoreAssignee = new ArrayList<>();
    private ArrayList<String> mChoreDescription = new ArrayList<>();
    private ArrayList<String> mChoreDate = new ArrayList<>();
    private ArrayList<String> mChoreTime = new ArrayList<>();
    private  ArrayList <String> mTempChoreNames = new ArrayList<>();
    String groupName;

    public ChoresAdaptor(Context mContext, ArrayList<String> mChoreNames, ArrayList<String> mChoreAssignee, ArrayList<String> mChoreDescription, ArrayList<String> mChoreDate, ArrayList<String> mChoreTime, String groupName, ArrayList<String> mTempChoreNames) {
        this.mContext = mContext;
        this.mChoreNames = mChoreNames;
        this.mChoreAssignee = mChoreAssignee;
        this.mChoreDescription = mChoreDescription;
        this.mChoreDate = mChoreDate;
        this.mChoreTime = mChoreTime;
        this.groupName = groupName;
        this.mTempChoreNames = mTempChoreNames;
    }

    @NonNull
    @Override
    public ChoresAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chores,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChoresAdaptor.ViewHolder viewHolder,final int i) {
        viewHolder.chore.setText(mChoreNames.get(i));
        viewHolder.choreAssignee.setText(mChoreAssignee.get(i));
        viewHolder.choreDescription.setText(mChoreDescription.get(i));
        viewHolder.choreDate.setText(mChoreDate.get(i));
        viewHolder.choreTime.setText(mChoreTime.get(i));

        viewHolder.ll_chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(mContext, "Remove this item: "+mChoreNames.get(i), Toast.LENGTH_SHORT).show();

                        final FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        final DatabaseReference storageRef = storage.getReference("Groups/" + groupName + "/Chores");
                        Log.e("GN",groupName);
                        storageRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String message;
                                String remainingChores = "";
                                if (dataSnapshot.getValue() == null)
                                    message = "";
                                else
                                    message = dataSnapshot.getValue().toString();
                                String chores[] = message.split(";");


                                for(int j = 0; j < chores.length;j++) {
                                    String[] chore = chores[j].split(":");
                                    Log.e("Iny","For");
                                    Log.e("In",String.valueOf(i));
                                    Log.e("In",String.valueOf(j));


                                    if (!chore[0].equals(mTempChoreNames.get(i)))
                                    {
                                        remainingChores += chores[j]+";";
                                    }
                                }
                                Log.e("Remainig",remainingChores);
                                storageRef.setValue(remainingChores);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mChoreNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView chore;
        TextView choreAssignee;
        TextView choreDescription;
        TextView choreDate;
        TextView choreTime;
        LinearLayout ll_chores;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chore = (TextView) itemView.findViewById(R.id.tv_choreName);
            choreAssignee = (TextView) itemView.findViewById(R.id.tv_choreByWho);
            choreDescription = (TextView) itemView.findViewById(R.id.tv_choreDescription);
            choreDate = (TextView) itemView.findViewById(R.id.tv_choreDate);
            choreTime = (TextView) itemView.findViewById(R.id.tv_choreTime);
            ll_chores = (LinearLayout) itemView.findViewById(R.id.ll_chores);
        }
    }
}