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
    private MultiSelectionSpinner majorSpinner;
    private RangeSeekBar<Integer> age;
    private View btnSend;
    private View btnCancel;
    private EditText interests;
    private EditText zip;
    private TextView dispage;
    Integer min = 18;
    Integer max = 22;

    String[] agevalues = {"<18", "19", "20", "21", "22", ">22"};
    String[] majorvalues = {"Computer Science", "Engineering", "CGT", "Construction Management", "Philosophy", "English"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myinterests = interests.getText().toString();
                String majors = majorSpinner.getSelectedItemsAsString();
                String myzip = zip.getText().toString();
                Toast.makeText(FiltersActivity.this, myinterests + "\n" + myzip + "\n" + min + "-" + max + "\n" + majors, Toast.LENGTH_LONG).show();


            }
        });

        age.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now we have the minValue and maxValue of your RangeSeekbar
                min = minValue;
                max = maxValue;
                dispage.setText(minValue + "-" + maxValue);
            }
        });

        age.setNotifyWhileDragging(true);

    }
}
