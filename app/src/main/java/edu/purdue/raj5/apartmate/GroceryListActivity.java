package edu.purdue.raj5.apartmate;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroceryListActivity extends AppCompatActivity {
    RecyclerView rv_grocery;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mNumItems = new ArrayList<>();
    private ArrayList<String> mTempName = new ArrayList<>();
    FloatingActionButton fab2;
    String groupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        Intent intent = getIntent();
        groupName = intent.getExtras().getString("GroupName");
        //initializeRecyclerView();
        initiaizeAddButton();
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/GroceryList");

        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                Log.e("GroceryList",message);
                String groceryList[] = message.split(";");
                mNames.clear();
                mNumItems.clear();

                for(int i = 0; i < groceryList.length;i++)
                {
                    String[] groceryItem = groceryList[i].split(":");
                    mNames.add(groceryItem[0]);
                    mNumItems.add(groceryItem[1]);
                }
                Log.e("Size",String.valueOf(mNames.size()));
                Log.e("val","change");
                initializeRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabGroceryAddItem);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroceryListActivity.this, "Add grocery item",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(GroceryListActivity.this);
                View viewDialog = LayoutInflater.from(GroceryListActivity.this).inflate(R.layout.add_grocery_item,null);
                final EditText et_groceryItem = (EditText) viewDialog.findViewById(R.id.et_groceryNameAdd);
                final EditText et_groceryNumItems = (EditText) viewDialog.findViewById(R.id.et_groceryNumItemsAdd);
                final Button bt_groceryItemAdd = (Button) viewDialog.findViewById(R.id.bt_groceryAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_groceryItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String groceryItem = et_groceryItem.getText().toString();
                        String numGroceryItems = et_groceryNumItems.getText().toString();
                        Toast.makeText(GroceryListActivity.this, "The item has been added", Toast.LENGTH_SHORT).show();
                        final String grocery = et_groceryItem.getText().toString();
                        final String num = et_groceryNumItems.getText().toString();
                        LoginActivity.sock.send("ADD_GROCERYITEM;"+groupName+";"+grocery+";"+num);
                        mNames.add(grocery);
                        mNumItems.add(num);
                        initializeRecyclerView();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
    private void initializeRecyclerView(){
        rv_grocery = (RecyclerView)findViewById(R.id.rv_groceryItems);
        mTempName = new ArrayList<>(mNames);
        GroceryAdaptor adaptor = new GroceryAdaptor(this, mNames, mNumItems, groupName,mTempName);
        rv_grocery.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
        rv_grocery.setLayoutManager(new LinearLayoutManager(this));
    }
}