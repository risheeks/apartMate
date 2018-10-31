package edu.purdue.raj5.apartmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class GroceryListActivity extends AppCompatActivity {
    RecyclerView rv_grocery;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mNumItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        initializeRecyclerView();
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
