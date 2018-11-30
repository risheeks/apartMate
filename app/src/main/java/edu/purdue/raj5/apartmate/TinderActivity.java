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

import java.net.Socket;

import static edu.purdue.raj5.apartmate.LoginActivity.sock;

public class TinderActivity extends AppCompatActivity {
    static final FirebaseDatabase dbStorage = FirebaseDatabase.getInstance();
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    static String currentCardEmail;
    static String currentUserEmail;
    String filterInterests;
    int filterAgeMin;
    int filterAgeMax;
    String filterZipcode;
    String filterMajors;
    String filterGenders;
    Bitmap bmp;


    static String currentCardSmoke;
    static String currentCardDrink;
    static String currentCardInterests;
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
        filterGenders = intent.getExtras().getString("Gender");
        mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

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
                    getMajor(users[i],profile);
                    getInterests(users[i],profile);
                    getGender(users[i],profile);
                    getCity(users[i],profile);
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
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
                                    if(applyFilters(profile) )//&& !currentUserEmail.equals(profile.getEmail()))
                                    {
                                        Log.e("Added",profile.getEmail());
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        findViewById(R.id.infoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerV();
            }
        });
    }

    private void getCity(String user, final Profile profile) {
        final DatabaseReference majorRef = dbStorage.getReference("Login/"+user+"/City");
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                profile.setLocation( message);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void getGender(String user,final Profile profile) {
        final DatabaseReference majorRef = dbStorage.getReference("Login/"+user+"/Gender");
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                profile.setGender( message);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private boolean applyAgeFilter(final Profile profile)
    {
        if((filterAgeMin<= profile.getAge() &&  profile.getAge() <=filterAgeMax))
        {
            return true;
        }
        return false;
    }

    private void getMajor(final String user, final Profile profile)
    {
        final DatabaseReference majorRef = dbStorage.getReference("Login/"+user+"/Major");
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                profile.setMajor(message);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private boolean applyMajorFilter(final Profile profile)
    {
     //  getMajor(profile);
        return profile.getMajor().toLowerCase().contains(filterMajors.toLowerCase()) || filterMajors.toLowerCase().contains(profile.getMajor().toLowerCase());
    }

    private boolean applyCityFilter(final Profile profile)
    {
        //  getMajor(profile);
        return profile.getLocation().trim().equalsIgnoreCase(filterZipcode.trim()) ;
    }


    private boolean applyFilters(final Profile profile) {
        boolean age = applyAgeFilter(profile);
        boolean major;
        boolean interests;
        boolean gender;
        boolean city;
        if(filterMajors.isEmpty())
        {
            major = true;
        }
        else
        {
            major = applyMajorFilter(profile);
        }

        if(filterInterests.isEmpty())
        {
            interests = true;
        }
        else
        {
            interests = applyInterestsFilter(profile);
        }

        if(filterGenders.isEmpty())
        {
            gender = true;
        }
        else
        {
            gender = applyGenderFilter(profile);
        }
        if(filterZipcode.isEmpty())
        {
            city = true;
        }
        else
        {
            city = applyCityFilter(profile);
        }
        Log.e("age: ",String.valueOf(age));
        Log.e("major: ",String.valueOf(major)+"   "+profile.getMajor());
        Log.e("interests: ",String.valueOf(interests));
        Log.e("gender: ",String.valueOf(gender)+"  "+profile.getGender());



        return age && major && interests && gender && city;
    }

    private boolean applyGenderFilter(Profile profile) {
       // getGender(profile);
        return profile.getGender().toLowerCase().equalsIgnoreCase(filterGenders);
    }

    private void getInterests(final String user,final Profile profile)
    {
        final DatabaseReference majorRef = dbStorage.getReference("Login/"+user+"/Interests");
        majorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                profile.setInterests(message);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    private boolean applyInterestsFilter(Profile profile) {
        return profile.getInterests().toLowerCase().contains(filterInterests.toLowerCase()) || filterInterests.toLowerCase().contains(profile.getInterests());
    }

    public static void updateSmoke()
    {
        DatabaseReference interestRef = dbStorage.getReference("Login/"+currentCardEmail.split("@")[0]+"/Smoke");
        interestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                currentCardSmoke = message;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public static void updateDrink()
    {
        DatabaseReference interestRef = dbStorage.getReference("Login/"+currentCardEmail.split("@")[0]+"/Drink");
        interestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                currentCardDrink = message;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public static void updateInterests()
    {
        DatabaseReference interestRef = dbStorage.getReference("Login/"+currentCardEmail.split("@")[0]+"/Interests");
        interestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                currentCardInterests = message;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void alerV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Me");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView smoke_text = new TextView(this );
        smoke_text.setText("Smoke: "+currentCardSmoke);
        smoke_text.setTextSize(15);
        smoke_text.setPadding(10,10,0,0);
        layout.addView(smoke_text);

        TextView drink_text = new TextView(this );
        drink_text.setText("Drink: "+currentCardDrink);
        drink_text.setTextSize(15);
        drink_text.setPadding(10,10,0,0);
        layout.addView(drink_text);

        TextView interest_text = new TextView(this );
        interest_text.setText("Interests: "+currentCardInterests);
        interest_text.setTextSize(15);
        interest_text.setPadding(10,10,0,0);
        layout.addView(interest_text);

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