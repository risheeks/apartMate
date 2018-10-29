package edu.purdue.raj5.apartmate;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ChoresListActivity extends AppCompatActivity {
    RecyclerView rv_chores;
    private ArrayList<String> mNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);
        initializeRecyclerView();
    }
    private void initializeRecyclerView(){
        rv_chores = (RecyclerView)findViewById(R.id.rv_choresItems);

        ChoresAdaptor adaptor = new ChoresAdaptor(this, mNames);
        rv_chores.setAdapter(adaptor);
        rv_chores.setLayoutManager(new LinearLayoutManager(this));
    }
}