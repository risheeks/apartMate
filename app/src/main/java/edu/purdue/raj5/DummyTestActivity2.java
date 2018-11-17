package com.example.dell.apartmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DummyTestActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_test2);
        if(DummyTestActivity.mIsInForegroundMode){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

    }
}
