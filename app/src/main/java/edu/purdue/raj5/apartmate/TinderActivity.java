package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

/*
* Active for searching potential roommates
* Roommate search is based on a filter by Major, Age, location etc.
*/

public class TinderActivity extends AppCompatActivity {
    final FirebaseDatabase dbStorage = FirebaseDatabase.getInstance();
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    static String currentCardEmail;
    static String currentUserEmail;
    String filterInterests;
    int filterAgeMin;
    int filterAgeMax;
    String filterZipcode;
    String filterMajors;
    Bitmap bmp;
    static String major;
    //private Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tinder);
        Intent intent = getIntent();
        currentUserEmail = intent.getExtras().getString("Email");
        filterInterests = intent.getExtras().getString("Interests");
        filterAgeMin = intent.getExtras().getInt("AgeMin");
        filterAgeMax = intent.getExtras().getInt("AgeMax");
        filterZipcode = intent.getExtras().getString("Zipcode");
        filterMajors = intent.getExtras().getString("Majors");
        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        //FireBase call to get the list of Users (Potential roommates)
        DatabaseReference storageRef = dbStorage.getReference("Users");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                String[] users = message.split(";");
                for(int i = 0; i < users.length; i++)
                {
                    final Profile profile = new Profile();

                    DatabaseReference usersNameRef = dbStorage.getReference("Login/"+users[i]+"/FirstName");
                    usersNameRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String message;
                            if (dataSnapshot.getValue() == null)
                                message = "";
                            else
                                message = dataSnapshot.getValue().toString();
                            profile.setName(message);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                    Log.e("Check","Login/"+message+"/Age");
                    DatabaseReference usersInterestRef = dbStorage.getReference("Login/"+users[i]+"/Age");
                    usersInterestRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String message;
                            if (dataSnapshot.getValue() == null)
                                message = "";
                            else
                                message = dataSnapshot.getValue().toString();
                            profile.setAge(Integer.parseInt(message));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                    profile.setLocation("West Lafayette");

                    DatabaseReference usersEmailRef = dbStorage.getReference("Login/"+users[i]+"/Email");
                    usersEmailRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String message;
                            if (dataSnapshot.getValue() == null)
                                message = "";
                            else
                                message = dataSnapshot.getValue().toString();
                            profile.setEmail(message);
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            // Create a storage reference from our app
                            StorageReference storageRef = storage.getReference();
                            StorageReference islandRef = storageRef.child("profile"+message+".jpg");

                            final long ONE_MEGABYTE = 1024 * 1024;
                            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    // Data for "images/island.jpg" is returns, use this as needed
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inMutable = true;
                                     bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                                    if(applyFilters(profile))
                                    {
                                        mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView,bmp));
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });

                }
                //If no potential roommates found
                if(mSwipeView.getChildCount() == 0)
                {
                    Toast.makeText(getApplicationContext(),"No users with current filters",Toast.LENGTH_SHORT).show();
                }



            }
            //FireBase error
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        /*
        * set the onClick for reject button
        */
        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });
        /*
        * set the onClick for accept button
        */
        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
        /*
        * set the onClick for info button
        * The info button will display an alert containint the potential user's age, major and various other preferences
        */
        findViewById(R.id.infoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerV();
            }
        });
    }
    /*
    * Apply filters to the potential roommate search
    */
    private boolean applyFilters(final Profile profile) {
        boolean age=false;
        if((filterAgeMin<= profile.getAge() &&  profile.getAge() <=filterAgeMax))
        {
            age=true;
        }
        final DatabaseReference majorRef = dbStorage.getReference("Login/"+profile.getEmail().split("@")[0]+"/Major");
        final boolean finalAge = age;
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                major = message;
                if(major.contains(filterMajors) || filterMajors.contains(major) && finalAge)
                {
                    //mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView,bmp));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        if(age)
            return true;
        else
            return false;
    }
    /*
    * Alert to display the various details about the potential roommate
    * Bool smoke, Bool drink, and Interests
    */
    public void alerV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Me");

        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //Get if the potential roommate smokes or not
        DatabaseReference smokeRef = dbStorage.getReference("Login/"+currentCardEmail.split("@")[0]+"/Smoke");
        smokeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                TextView smoke_text = new TextView(getApplicationContext() );
                smoke_text.setText("Smoke: " + message);
                smoke_text.setTextSize(15);
                smoke_text.setPadding(10,10,0,0);
                layout.addView(smoke_text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //Get if the potential roommate drink or not
        DatabaseReference drinkRef = dbStorage.getReference("Login/"+currentCardEmail.split("@")[0]+"/Drink");
        drinkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                TextView drink_text = new TextView(mContext );
                drink_text.setText("Drink: "+message);
                drink_text.setTextSize(15);
                drink_text.setPadding(10,10,0,0);
                layout.addView(drink_text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //Get the interests of the potential roommate
        DatabaseReference interestRef = dbStorage.getReference("Login/"+currentCardEmail.split("@")[0]+"/Interests");
        interestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                TextView interest_text = new TextView(getApplicationContext() );
                interest_text.setText("Interests: "+message);
                interest_text.setTextSize(15);
                interest_text.setPadding(10,10,0,0);
                layout.addView(interest_text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
