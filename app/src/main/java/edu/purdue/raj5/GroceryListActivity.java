package com.example.dell.apartmate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class GroceryListActivity extends AppCompatActivity {
    RecyclerView rv_grocery;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mNumItems = new ArrayList<>();
    FloatingActionButton fab;
    FloatingActionButton fab2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        initializeRecyclerView();
        initiaizeAddButton();
    }


    private void initiaizeAddButton(){
        fab = (FloatingActionButton) findViewById(R.id.fabGrocerySetReminderTime);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroceryListActivity.this, "Set Time for reminder",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(GroceryListActivity.this);
                View viewDialog = LayoutInflater.from(GroceryListActivity.this).inflate(R.layout.add_grocery_reminder_time,null);
                //final TextView tv_groceryItem = (EditText) viewDialog.findViewById(R.id.et_groceryNameAdd);
                final EditText et_groceryReminder = (EditText) viewDialog.findViewById(R.id.et_groceryReminderTimeAdd);
                final Button bt_groceryItemAdd = (Button) viewDialog.findViewById(R.id.bt_groceryReminderTimeAdd);
                builder.setView(viewDialog);
                builder.setTitle("Reminder Time");
                final AlertDialog dialog = builder.create();
                bt_groceryItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String groceryRemainderTime = et_groceryReminder.getText().toString();
                        Toast.makeText(GroceryListActivity.this, groceryRemainderTime, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

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
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }
    private void initializeRecyclerView(){
        rv_grocery = (RecyclerView)findViewById(R.id.rv_groceryItems);
        mNames.add("Bananas");
        mNames.add("Maggi");
        mNumItems.add("1 dozen");
        mNumItems.add("10");
        GroceryAdaptor adaptor = new GroceryAdaptor(this, mNames, mNumItems);
        rv_grocery.setAdapter(adaptor);
        rv_grocery.setLayoutManager(new LinearLayoutManager(this));
    }
}
