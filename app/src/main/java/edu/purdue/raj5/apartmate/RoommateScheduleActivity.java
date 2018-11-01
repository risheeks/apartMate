package com.example.sid.apartmate;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RoommateScheduleActivity extends AppCompatActivity {
    //TODO add a schedule button in the misc tab to open this activity
    TableLayout mTlayout;
    TableRow tr;
    //TODO get roommates
    String[] mTextofButton = { "Risheek", "Adrian", "Ian", "Akshay", "Sid" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommate_schedule);
        init();
    }

    public void init() {
        mTlayout = (TableLayout) findViewById(R.id.table_main);

        int i = 0;
        while (i < mTextofButton.length) {
            if (i % 3 == 0) {
                tr = new TableRow(this);
                mTlayout.addView(tr);
            }
            Button btn = new Button(this);
            btn.setText(mTextofButton[i]);
            btn.setId(i);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //TODO Add event for the button
                    //System.out.println("v.getid is:- " + v.getId());
                }
            });
            tr.addView(btn);
            i++;
        }
    }
}
