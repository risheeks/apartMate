package com.example.dell.apartmate;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DummyTestActivity extends AppCompatActivity {
    Button bt_notification;
    private NotificationManager notifManager;
    public static boolean mIsInForegroundMode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_test);

        initNotification();

    }
    private void initNotification(){
        bt_notification = (Button) findViewById(R.id.bt_notificationDummy);
        bt_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification("You have a match",DummyTestActivity.this,"Wassup");
            }
        });
    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Yo: ", "onPause: Well ");
        mIsInForegroundMode = false;
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        mIsInForegroundMode = true;
        Log.e("Yo: ", "onResume: Well ");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mIsInForegroundMode = false;
        Log.e("Yo: ", "onPause: Well ");
    }
/*
    @Override
    protected void onStop(){
        super.onStop();
        mIsInForegroundMode = false;
        Log.e("Yo: ", "onPause: Well ");
    }
*/

    public static void setmIsInForegroundMode(Boolean temp){
        mIsInForegroundMode = temp;
    }
    @TargetApi(26)
    public void createNotification(String aMessage, Context context, String smallSizeText) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = context.getString(R.string.app_name); // default_channel_id
        String title = context.getString(R.string.app_name); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            /*if(isInForeground()){

                intent = new Intent(context, MenuActivity.class);
            }
            else{
                intent = new Intent(context, LoginActivity.class);
            }*/

            intent = new Intent(context, DummyTestActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.mipmap.ic_launcher)   // required
                    .setContentText(smallSizeText) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, DummyTestActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.mipmap.ic_launcher)   // required
                    .setContentText(smallSizeText) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}