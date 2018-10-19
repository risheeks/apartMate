package com.example.sid.apartmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ScheduleActivity extends AppCompatActivity {
    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        //SharedPreferences stored user data which can be retrieved later on
        SharedPreferences preferences = this.getSharedPreferences("MyTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("theme", theme);
        editor.commit();

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl_schedule);
        String s = preferences.getString("theme", "");
        if(s.equals("dark")) {

            rl.setBackgroundColor(Color.DKGRAY);
        }else {
            rl.setBackgroundColor(Color.WHITE);
        }


    }
    public void alerV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add event");

// Set up the input

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add a TextView here for the "Title" label, as noted in the comments
        final EditText titleBox = new EditText(this);
        titleBox.setHint("Subject");
        titleBox.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(titleBox); // Notice this is an add method

        // Add another TextView here for the "Description" label
        final EditText descriptionBox = new EditText(this);
        descriptionBox.setHint("Description");
        descriptionBox.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(descriptionBox); // Another add method

        final EditText nameBox = new EditText(this);
        nameBox.setHint("Name");
        nameBox.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(nameBox); // Another add method

        final EditText dateBox = new EditText(this);
        dateBox.setHint("Date(YYYY-MM-DD)");
        dateBox.setInputType(InputType.TYPE_CLASS_DATETIME);
        layout.addView(dateBox); // Another add method

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Add event to the Calender
                HomeCollection.date_collection_arr.add( new HomeCollection(dateBox.getText().toString() ,nameBox.getText().toString(),titleBox.getText().toString(),descriptionBox.getText().toString()));
                refreshCalendar();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        String theme="";
        HomeCollection.date_collection_arr=new ArrayList<HomeCollection>();
        try {
            FileInputStream fis = openFileInput("theme");
            Scanner scanner = new Scanner(fis);
            theme = scanner.next();
            scanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(theme.contains("dark"))
            getAppTheme("dark");

        else
            getAppTheme("light");

        Button addButton = (Button) findViewById(R.id.add_to_schedule);
        addButton.setOnClickListener(new View.OnClickListener() { //set the OnClickListener on the left arrow
            @Override
            public void onClick(View v) {
                alerV();

            }
        });

        HomeCollection.date_collection_arr.add( new HomeCollection("2018-07-08" ,"Diwali","Chore","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-07-08" ,"Holi","Chore","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-07-08" ,"Statehood Day","Holiday","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-08-08" ,"Republic Unian","Holiday","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-07-09" ,"ABC","Holiday","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-06-15" ,"demo","Holiday","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-09-26" ,"weekly off","Holiday","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-01-08" ,"Events","Holiday","this is holiday"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-11-11" ,"307 Meeting","IMPORTANT","307 group meeting"));
        HomeCollection.date_collection_arr.add( new HomeCollection("2018-11-09" ,"Akshay's bday","Party","Call Akshay"));



        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(this, cal_month,HomeCollection.date_collection_arr); //call HwAdapter

        tv_month = (TextView) findViewById(R.id.tv_month);//find tv_month id inside the activity
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() { //set the OnClickListener on the left arrow
            @Override
            public void onClick(View v) {
                //set minimum date of the calender
                if (cal_month.get(GregorianCalendar.MONTH) == 4&&cal_month.get(GregorianCalendar.YEAR)==2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(ScheduleActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        //set the OnClickListener on the rigth arrow
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //set maximum date of the calender
                if (cal_month.get(GregorianCalendar.MONTH) == 2&&cal_month.get(GregorianCalendar.YEAR)==2019) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(ScheduleActivity.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                }
                else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, ScheduleActivity.this);
            }

        });
    }
    /*
    * Set the next month inside the calender
    */
    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }
    /*
    * Set the previous month inside the calender
    */
    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }
    /*
    * Refresh the calender
    */
    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }
}
