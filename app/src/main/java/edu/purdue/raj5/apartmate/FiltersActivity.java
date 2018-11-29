package edu.purdue.raj5.apartmate;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FiltersActivity extends AppCompatActivity {

    //private MultiSelectionSpinner ageSpinner;
    private MultiSelectionSpinner majorSpinner;
    private RangeSeekBar<Integer> age;
    private View btnSend;
    private View btnCancel;
    private EditText interests;
    private EditText zip;
    private TextView dispage;
    private TextView interestL;
    private TextView majorL;
    private TextView dispageL;
    private TextView zipL;
    Integer min = 18;
    Integer max = 22;

    String[] agevalues = {"<18", "19", "20", "21", "22", ">22"};
    String[] majorvalues = {"Computer Science", "Engineering", "CGT", "Construction Management", "Philosophy", "English", "Philosophy", "Business Management", "Accounting", "Communication Sciecnce", "Nursing", "History", "Math", "Economics", "Marketing", "Music", "Physics", "Political Science", "Theatre"};

    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        SharedPreferences preferences = this.getSharedPreferences("MyTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("theme", theme);
        editor.commit();
//        interests = (EditText) findViewById(R.id.Interests);
//        et_chatSearch = (TextView) findViewById(R.id.et_chatSearch);
//        optionsButton = (ImageView) findViewById(R.id.iv_menuOptions);
        LinearLayout ll = (LinearLayout) findViewById(R.id.filterll);
        // The following code is used for theme preferences.
        String s = preferences.getString("theme", "");
        if (s.equals("dark")) {
            ll.setBackgroundColor(Color.DKGRAY);
            interests.setTextColor(Color.WHITE);
            zip.setTextColor(Color.WHITE);
            dispage.setTextColor(Color.WHITE);
            majorSpinner.setBackgroundColor(Color.GRAY);
            //age.setBackgroundColor(Color.WHITE);
            interestL.setTextColor(Color.WHITE);
            dispageL.setTextColor(Color.WHITE);
            majorL.setTextColor(Color.WHITE);
            zipL.setTextColor(Color.WHITE);
            age.setBackgroundColor(Color.DKGRAY);

        } else {
            ll.setBackgroundColor(Color.WHITE);
            interests.setTextColor(Color.BLACK);
            zip.setTextColor(Color.BLACK);
            dispage.setTextColor(Color.BLACK);
            majorSpinner.setBackgroundColor(Color.WHITE);
            //age.setBackgroundColor(Color.WHITE);
            interestL.setTextColor(Color.BLACK);
            dispageL.setTextColor(Color.BLACK);
            majorL.setTextColor(Color.BLACK);
            zipL.setTextColor(Color.BLACK);
            age.setBackgroundColor(Color.WHITE);
        }


    }

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Intent i  = getIntent();
        email = i.getExtras().getString("Email");

        age = findViewById(R.id.age);
        majorSpinner = findViewById(R.id.major);
        btnSend = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        interests = findViewById(R.id.interests);
        zip = findViewById(R.id.zip);
        dispage = findViewById(R.id.dispage);
        interestL = findViewById(R.id.interestsL);
        zipL = findViewById(R.id.zipL);
        dispageL = findViewById(R.id.dispageL);
        majorL = findViewById(R.id.majorL);
        age.setRangeValues(18, 30);
        majorSpinner.setItems(majorvalues);
        dispage.setText("18-30");

        initializeTheme();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String myinterests = interests.getText().toString();
//                String majors = majorSpinner.getSelectedItemsAsString();
//                String myzip = zip.getText().toString();
                //Toast.makeText(FiltersActivity.this, myinterests + "\n" + myzip + "\n" + min + "-" + max + "\n" + majors, Toast.LENGTH_LONG).show();
                Toast.makeText(FiltersActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myinterests = interests.getText().toString();
                String majors = majorSpinner.getSelectedItemsAsString();
                String myzip = zip.getText().toString();
                Toast.makeText(FiltersActivity.this, myinterests + "\n" + myzip + "\n" + min + "-" + max + "\n" + majors, Toast.LENGTH_LONG).show();
                Log.e("Filters",myinterests + "\n" + myzip + "\n" + min + "-" + max + "\n" + majors);
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                Intent i  = new Intent(getApplicationContext(), TinderActivity.class);
                i.putExtra("Email",email);
                i.putExtra("Interests",myinterests);
                i.putExtra("Zipcode",myzip);
                i.putExtra("AgeMin",min);
                i.putExtra("AgeMax",max);
                i.putExtra("Majors",majors);
                startActivity(i);
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
    private void initializeTheme() {
        String theme = "";
        try {
            FileInputStream fis = openFileInput("theme");
            Scanner scanner = new Scanner(fis);
            theme = scanner.next();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (theme.contains("dark"))
            getAppTheme("dark");

        else
            getAppTheme("light");
    }

}