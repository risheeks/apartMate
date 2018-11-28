package com.example.risheek.apartmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.w3c.dom.Text;

public class FiltersActivity extends AppCompatActivity {

    //private MultiSelectionSpinner ageSpinner;
    private MultiSelectionSpinner majorSpinner; //major specifier
    private RangeSeekBar<Integer> age; //a bar for picking age
    private View btnSend; //a button to send
    private View btnCancel; // a button to cancel
    private EditText interests; // interests to match for
    private EditText zip; // zip code to search potential roommates in
    private TextView dispage; // The age range on the age seekbar
    Integer min = 18; //default minimum age
    Integer max = 22; //default maximum age

    //String[] agevalues = {"<18", "19", "20", "21", "22", ">22"};

    //List of majors to pick from
    String[] majorvalues = {"Computer Science", "Engineering", "CGT", "Construction Management", "Philosophy", "English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) { //on launching of this activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        age = findViewById(R.id.age);
        majorSpinner = findViewById(R.id.major);
        btnSend = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        interests = findViewById(R.id.interests);
        zip = findViewById(R.id.zip);
        dispage = findViewById(R.id.dispage);
        age.setRangeValues(18, 30);
        majorSpinner.setItems(majorvalues);


        //the function called when the cancel button is clicked
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myinterests = interests.getText().toString();
                String majors = majorSpinner.getSelectedItemsAsString();
                String myzip = zip.getText().toString();
                Toast.makeText(FiltersActivity.this, myinterests + "\n" + myzip + "\n" + min + "-" + max + "\n" + majors, Toast.LENGTH_LONG).show();


            }
        });

        //the function called when the submit button is clicked
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myinterests = interests.getText().toString();
                String majors = majorSpinner.getSelectedItemsAsString();
                String myzip = zip.getText().toString();
                Toast.makeText(FiltersActivity.this, myinterests + "\n" + myzip + "\n" + min + "-" + max + "\n" + majors, Toast.LENGTH_LONG).show();


            }
        });

        //called whenever a chnage is made to the age seekbar
        age.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now we have the minValue and maxValue of your RangeSeekbar
                min = minValue;
                max = maxValue;
                dispage.setText(minValue + "-" + maxValue);
            }
        });

        //notifies while the ae seekbar is being dragged
        age.setNotifyWhileDragging(true);

    }
}
