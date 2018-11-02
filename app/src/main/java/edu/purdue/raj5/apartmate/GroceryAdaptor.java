package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.DialogInterface;
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

public class GroceryAdaptor extends RecyclerView.Adapter<GroceryAdaptor.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mGroceryNames = new ArrayList<>();
    private ArrayList<String> mNumItems = new ArrayList<>();
    String groupName;
    private ArrayList<String> mTempName = new ArrayList<>();


    public GroceryAdaptor(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mNumItems, String groupName, ArrayList<String> mTempName) {
        this.mContext = mContext;
        this.mGroceryNames = mGroupNames;
        this.mNumItems = mNumItems;
        this.groupName = groupName;
        this.mTempName = mTempName;
    }

    @NonNull
    @Override
    public GroceryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vie = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grocery,viewGroup,false);
        GroceryAdaptor.ViewHolder holde = new GroceryAdaptor.ViewHolder(vie);
        return holde;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.groceryName.setText(mGroceryNames.get(i));
        viewHolder.groceryNumItems.setText(mNumItems.get(i));
        viewHolder.ll_chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Remove this item: "+mGroceryNames.get(i), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(mContext, "Remove this item: "+mGroceryNames.get(i), Toast.LENGTH_SHORT).show();

                        final FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        final DatabaseReference storageRef = storage.getReference("Groups/" + groupName + "/GroceryList");
                        Log.e("GN",groupName);
                        storageRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String message;
                                String remainingGroceries = "";
                                if (dataSnapshot.getValue() == null)
                                    message = "";
                                else
                                    message = dataSnapshot.getValue().toString();
                                String groceries[] = message.split(";");


                                for(int j = 0; j < groceries.length;j++) {
                                    String[] grocery = groceries[j].split(":");
                                    Log.e("Iny","For");
                                    Log.e("In",String.valueOf(i));
                                    Log.e("In",String.valueOf(j));


                                    if (!grocery[0].equals(mTempName.get(i)))
                                    {
                                        remainingGroceries += groceries[j]+";";
                                    }
                                }
                                Log.e("Remainig",remainingGroceries);
                                storageRef.setValue(remainingGroceries);
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
        return mGroceryNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView groceryName;
        TextView groceryNumItems;
        LinearLayout ll_chores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = (TextView) itemView.findViewById(R.id.tv_groceryName);
            groceryNumItems = (TextView) itemView.findViewById(R.id.tv_groceryNumItems);
            ll_chores = (LinearLayout) itemView.findViewById(R.id.ll_groceries);

        }
    }
}