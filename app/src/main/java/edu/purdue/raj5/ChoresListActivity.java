package com.example.dell.apartmate;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoresListActivity extends AppCompatActivity {
    RecyclerView rv_chores;
    FloatingActionButton fab2;
    private ArrayList<String> mNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);
        initializeRecyclerView();
        initiaizeAddButton();
    }
    private void initiaizeAddButton(){
        fab2 = (FloatingActionButton) findViewById(R.id.fabGroceryAddItem);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChoresListActivity.this, "Add grocery item",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ChoresListActivity.this);
                View viewDialog = LayoutInflater.from(ChoresListActivity.this).inflate(R.layout.add_chores_item,null);
                final EditText et_choreName = (EditText) viewDialog.findViewById(R.id.et_choreNameAdd);
                final EditText et_choreWho = (EditText) viewDialog.findViewById(R.id.et_choreByWhoAdd);
                final EditText et_choreDescription = (EditText) viewDialog.findViewById(R.id.et_choreDescriptionAdd);
                final EditText et_choreDate = (EditText) viewDialog.findViewById(R.id.et_choreDateAdd);
                final EditText et_choreTime = (EditText) viewDialog.findViewById(R.id.et_choreTimeAdd);
                final Button bt_choreItemAdd = (Button) viewDialog.findViewById(R.id.bt_choreAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();
                bt_choreItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String choreItem = et_choreName.getText().toString();
                        String choreWho = et_choreWho.getText().toString();
                        String choreDescription = et_choreDescription.getText().toString();
                        String choreDate = et_choreDate.getText().toString();
                        String choreTime = et_choreTime.getText().toString();
                        Toast.makeText(ChoresListActivity.this, "The item has been added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
    }

    private void initializeRecyclerView(){
        rv_chores = (RecyclerView)findViewById(R.id.rv_groupNames);
        ChoresAdaptor adaptor = new ChoresAdaptor(this, mNames);
        rv_chores.setAdapter(adaptor);
        rv_chores.setLayoutManager(new LinearLayoutManager(this));
    }
}
