package edu.purdue.raj5.apartmate;


        import android.graphics.Color;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.TypedValue;
        import android.view.Gravity;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.List;

public class BirthdayActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    String groupName;
    String[] members;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        groupName = getIntent().getExtras().getString("GroupName");
        linearLayout = findViewById(R.id.linear_layout);
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference storageRef = storage.getReference("Groups/"+groupName+"/Members");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if(dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                members = message.split(";");
                for(int i = 0; i < members.length; i++) {
                    final int x = i;
                    FirebaseDatabase storage = FirebaseDatabase.getInstance();
                    DatabaseReference storageRef = storage.getReference("Login/" + members[i].split("@")[0] + "/Birthday");
                    storageRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String message;
                            if (dataSnapshot.getValue() == null)
                                message = "";
                            else
                                message = dataSnapshot.getValue().toString();
                            TextView nameView = new TextView(BirthdayActivity.this);
                            nameView.setText(members[x].split("@")[0]);
                            nameView.setTextColor(Color.BLACK);
                            nameView.setPadding(10,30,10,5);
                            nameView.setTextSize(22);
                            linearLayout.addView(nameView);

                            TextView bdayView = new TextView(BirthdayActivity.this);
                            bdayView.setText("Wish me on: "+ message); //TODO: Get the date
                            bdayView.setTextColor(Color.DKGRAY);
                            bdayView.setPadding(5,0,10,20);
                            bdayView.setTextSize(16);
                            linearLayout.addView(bdayView);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
