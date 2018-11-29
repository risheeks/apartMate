package edu.purdue.raj5.apartmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

/*
*    Class to represent a profile
 */
public class ProfileActivity extends AppCompatActivity {
    /*
    *
    *   Declare ui components
     */
    TextView tv_message;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();

    ImageButton ib_camera_profile;
    CircleImageView profilePhoto;
    Uri imageUri;
    TextView tv_firstName;
    TextView tv_lastName;
    TextView tv_email;
    TextView tv_LatestAachievement;
    TextView tv_GreatestAchievement;
    Button bt_changeProfile;
    TextView tv_changePassword;
    TextView tv_age;
    TextView tv_gender;
    TextView tv_major;
    TextView tv_birthday;


    Client socket = LoginActivity.sock;
     EditText et_newPassword;
    EditText et_oldPassword;
    ToggleButton tb_theme;

    String email;
    /*
    *
    * Function to change app theme depending on "light" or "dark". Changes background color and updates text color
    *
    *
     */
    final FirebaseDatabase dbStorage = FirebaseDatabase.getInstance();
    public void getAppTheme(String theme) { //theme is "light" or "dark"

        //call this inside every activity
        SharedPreferences preferences = this.getSharedPreferences("MyTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("theme", theme);
        editor.commit();

        String s = preferences.getString("theme", "");
        if(s.equals("dark")) {
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);
            rl.setBackgroundColor(Color.DKGRAY);
            tv_firstName = (TextView)findViewById(R.id.tv_profile_firstName);
            tv_lastName = (TextView)findViewById(R.id.tv_profile_lastName);
            tv_email = (TextView)findViewById(R.id.tv_profile_email);
            tv_LatestAachievement = (TextView)findViewById(R.id.tv_latestAchievement);
            tv_GreatestAchievement = (TextView)findViewById(R.id.tv_greatestAchievement);
            tv_changePassword = (TextView)findViewById(R.id.tv_changePassword);
            tv_email.setTextColor(Color.WHITE);
            tv_firstName.setTextColor(Color.WHITE);
            tv_lastName.setTextColor(Color.WHITE);
            tv_LatestAachievement.setTextColor(Color.WHITE);
            tv_GreatestAchievement.setTextColor(Color.WHITE);
            tv_changePassword.setTextColor(Color.WHITE);
            tv_age.setTextColor(Color.WHITE);
            tv_gender.setTextColor(Color.WHITE);
            tv_major.setTextColor(Color.WHITE);
        }else {
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);
            rl.setBackgroundColor(Color.WHITE);
            tv_firstName = (TextView)findViewById(R.id.tv_profile_firstName);
            tv_lastName = (TextView)findViewById(R.id.tv_profile_lastName);
            tv_email = (TextView)findViewById(R.id.tv_profile_email);
            tv_LatestAachievement = (TextView)findViewById(R.id.tv_latestAchievement);
            tv_GreatestAchievement = (TextView)findViewById(R.id.tv_greatestAchievement);
            tv_changePassword = (TextView)findViewById(R.id.tv_changePassword);
            tv_email.setTextColor(Color.BLACK);
            tv_firstName.setTextColor(Color.BLACK);
            tv_lastName.setTextColor(Color.BLACK);
            tv_LatestAachievement.setTextColor(Color.BLACK);
            tv_GreatestAchievement.setTextColor(Color.BLACK);
            tv_changePassword.setTextColor(Color.BLACK);
            tv_age.setTextColor(Color.BLACK);
            tv_gender.setTextColor(Color.BLACK);
            tv_major.setTextColor(Color.BLACK);
        }


    }
/*
*   Kill activity on back button press
*
 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent i = new Intent(ProfileActivity.this, MenuActivity.class);
            i.putExtra("Email",email);
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
/*
*
* start MenuActivity to update theme
 */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfileActivity.this, MenuActivity.class);
        i.putExtra("Email",email);
        startActivity(i);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = getIntent().getExtras().getString("Email");
        initializeTextViews();
        /*
        *
        * Checks theme
         */
        String theme="";
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
        socket.setClientCallback(new Client.ClientCallback(){
            /*
            *Handles server response
             */
            @Override
            public void onMessage(String mess) {
                Log.e("res", mess);
                if(mess.contains("RECEIVE_PROFILE")) {
                    String[] fields = mess.split(";");
                    tv_email.setText(fields[1]);
                    tv_firstName.setText(fields[2]);
                    tv_lastName.setText(fields[3]);
                    tv_LatestAachievement.setText(fields[4]);
                    tv_GreatestAchievement.setText(fields[5]);
                    StorageReference islandRef = storageRef.child("profile"+fields[1]+".jpg");

                    final long ONE_MEGABYTE = 1024 * 1024;
                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Data for "images/island.jpg" is returns, use this as needed
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inMutable = true;
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                            profilePhoto.setImageBitmap(bmp);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
                else if(mess.contains("CHECK_PASSWORD")){
                    if(mess.contains("SUCCESS")){
                        socket.send("RESET_PASSWORD "+tv_email.getText().toString()+" "+et_newPassword.getText().toString());
                    }
                    else{

                    }
                }
            }

            @Override
            public void onConnect(Socket socket) throws IOException {

            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {

            }

            @Override
            public void onConnectError(Socket socket, String message) {

            }
        });
        //socket.send("GET_PROFILE");


        initializeButton();
        takePhoto();
        /*
        *
        * Button listener for theme switch
         */
        tb_theme.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if(isChecked)
                {
                    try {
                        FileOutputStream fos = openFileOutput("theme",Context.MODE_PRIVATE);
                        fos.write("dark".getBytes());
                        fos.flush();
                        fos.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    getAppTheme("dark");

                }
                else
                {
                    try {
                        FileOutputStream fos = openFileOutput("theme",Context.MODE_PRIVATE);
                        fos.write("light".getBytes());
                        fos.flush();
                        fos.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    getAppTheme("light");
                }
            }
        }) ;
        /*
        *
        * button listener for edit profile
         */
        bt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View view = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.profile_editable,null);
                final EditText et_firstName = (EditText) view.findViewById(R.id.et_profile_firstName);
                final EditText et_lastName = (EditText) view.findViewById(R.id.et_profile_lastName);
                final EditText et_latestAchievement = (EditText) view.findViewById(R.id.et_latestAchievement);
                final EditText et_greatestAchievement = (EditText) view.findViewById(R.id.et_greatestAchievement);
                final EditText et_age = (EditText) view.findViewById(R.id.et_age);
                final EditText et_gender = (EditText) view.findViewById(R.id.et_gender);
                final EditText et_major = (EditText) view.findViewById(R.id.et_major);
                final EditText et_birthday = (EditText) view.findViewById(R.id.et_birthday);



                final Button bt_applyChanges = (Button) view.findViewById(R.id.bt_profileEdit);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                bt_applyChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fName = et_firstName.getText().toString();
                        String lName = et_lastName.getText().toString();
                        String lAchievement = et_latestAchievement.getText().toString();
                        String gAchievement = et_greatestAchievement.getText().toString();
                        String age = et_age.getText().toString();
                        String gender = et_gender.getText().toString();
                        String major = et_major.getText().toString();
                        String birthday = et_birthday.getText().toString();
                        if(fName.isEmpty())
                            fName = tv_firstName.getText().toString();
                        else
                            tv_firstName.setText(fName);
                        if(lName.isEmpty())
                            lName = tv_lastName.getText().toString();
                        else
                            tv_lastName.setText(lName);
                        if(lAchievement.isEmpty())
                            lAchievement = tv_LatestAachievement.getText().toString();
                        else
                            tv_LatestAachievement.setText(lAchievement);
                        if(gAchievement.isEmpty())
                            gAchievement = tv_GreatestAchievement.getText().toString();
                        else
                            tv_GreatestAchievement.setText(gAchievement);
                        if(age.isEmpty())
                            age = tv_age.getText().toString();
                        else
                            tv_age.setText(age);
                        if(gender.isEmpty())
                            gender = tv_gender.getText().toString();
                        else
                            tv_gender.setText(gender);
                        if(major.isEmpty())
                            major = tv_major.getText().toString();
                        else
                            tv_major.setText(major);
                        if(birthday.isEmpty())
                            birthday = tv_birthday.getText().toString();
                        else
                            tv_birthday.setText(birthday);
                        socket.send("EDIT_PROFILE;"+email+";"+fName+";"+lName+";"+lAchievement+";"+gAchievement+";"+birthday+";"+gender+";"+major+";"+age);
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });
/*
*
*     Button listener to reset password
 */
        tv_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View view = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.password_change,null);
                et_oldPassword = (EditText) view.findViewById(R.id.et_changePassword);
                 et_newPassword = (EditText) view.findViewById(R.id.et_changeConfirmPassword);
                Button bt_applyChanges = (Button) view.findViewById(R.id.bt_profileEdit);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                bt_applyChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        socket.send("CHECK_PASSWORD "+tv_email.getText().toString()+" "+et_oldPassword.getText().toString());
                    }
                });
                dialog.show();


            }
        });

        displayFirstName();
        displayLastName();
        displayAge();
        displayGender();
        displayEmail();
        displaylatestAchievement();
        displayGreatestAchievement();
        displayMajor();
        displayPicture();
        displayBirthday();
    }

    private void displayBirthday() {

        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/Birthday");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_birthday.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displayPicture() {
        StorageReference islandRef = storageRef.child("profile"+tv_email.getText().toString()+".jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                profilePhoto.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void displayMajor()
    {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/Major");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_major.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displayGreatestAchievement()
    {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/GreatestAchievement");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_GreatestAchievement.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displaylatestAchievement()
    {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/LatestAchievement");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_LatestAachievement.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displayEmail()
    {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/Email");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_email.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displayAge()
    {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/Age");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_age.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displayGender() {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/Gender");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_gender.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void displayLastName() {
        DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/LastName");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_lastName.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    private void displayFirstName()
    {
         DatabaseReference storageRef = dbStorage.getReference("Login/" + email.split("@")[0] + "/FirstName");
        storageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message;
                if (dataSnapshot.getValue() == null)
                    message = "";
                else
                    message = dataSnapshot.getValue().toString();
                tv_firstName.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


/*
*
* Initializes ui components
 */
    private void initializeButton(){
        tb_theme = (ToggleButton)findViewById(R.id.tb_theme);
        bt_changeProfile = (Button)findViewById(R.id.bt_profileChenge);
    }
    private void initializeTextViews(){
        tv_firstName = (TextView)findViewById(R.id.tv_profile_firstName);
        tv_lastName = (TextView)findViewById(R.id.tv_profile_lastName);
        tv_email = (TextView)findViewById(R.id.tv_profile_email);
        tv_LatestAachievement = (TextView)findViewById(R.id.tv_latestAchievement);
        tv_GreatestAchievement = (TextView)findViewById(R.id.tv_greatestAchievement);
        tv_changePassword = (TextView)findViewById(R.id.tv_changePassword);
        tv_message = (TextView) findViewById(R.id.tv_profileMessage);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_major = (TextView) findViewById(R.id.tv_Major);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
    }
    private void takePhoto(){
        ib_camera_profile = (ImageButton)findViewById(R.id.ib_profilePhotoCamera);
        profilePhoto = (CircleImageView)findViewById(R.id.civ_profilePhoto);

        ib_camera_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



        /*
        ib_camera_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        */
    }
/*
*image upload
 */
    private void selectImage(){
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Update Photo");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }
                else if(items[which].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
                else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            Bitmap bitma = (Bitmap)data.getExtras().get("data");
            profilePhoto.setImageBitmap(bitma);
            // Get the data from an ImageView as bytes
            profilePhoto.setDrawingCacheEnabled(true);
            profilePhoto.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();
            StorageReference mountainsRef = storageRef.child("profile"+tv_email.getText().toString()+".jpg");
            UploadTask uploadTask = mountainsRef.putBytes(datas);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });

        }
        else if(requestCode == 1){
            if (resultCode == RESULT_OK){
                imageUri = data.getData();
                InputStream inputStream;
                    try{
                        inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitma = BitmapFactory.decodeStream(inputStream);
                        profilePhoto.setImageBitmap(bitma);
                        Bitmap bitmap = ((BitmapDrawable) profilePhoto.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] datas = baos.toByteArray();
                        StorageReference mountainsRef = storageRef.child("profile"+tv_email.getText().toString()+".jpg");
                        UploadTask uploadTask = mountainsRef.putBytes(datas);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }

            }
        }

