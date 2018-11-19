package edu.purdue.raj5.apartmate;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DummyTestActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Yo","b");
        //setContentView(R.layout.activity_dummy_test2);
        if(DummyTestActivity.mIsInForegroundMode){
            Log.e("Yo","bb");
            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            i.putExtra("Email","raj5@purdue.edu");
            startActivity(i);
        }
        else{
            Log.e("Yo","cc");
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }

    }
}