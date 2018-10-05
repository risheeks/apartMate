package com.example.dell.apartmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeTextViews();
        initializeButton();
        takePhoto();
        bt_changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View view = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.profile_editable,null);
                final EditText et_firstName = (EditText) view.findViewById(R.id.et_profile_firstName);
                final EditText et_lastName = (EditText) view.findViewById(R.id.et_profile_lastName);
                final EditText et_latestAchievement = (EditText) view.findViewById(R.id.et_latestAchievement);
                final EditText et_greatestAchievement = (EditText) view.findViewById(R.id.et_greatestAchievement);
                Button bt_applyChanges = (Button) view.findViewById(R.id.bt_profileEdit);

                bt_applyChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProfileActivity.this, ""+et_firstName+" "+et_lastName+" "+et_latestAchievement+" "+et_greatestAchievement+" ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });

        tv_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View view = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.password_change,null);
                final EditText et_oldPassword = (EditText) view.findViewById(R.id.et_profile_firstName);
                final EditText et_newPassword = (EditText) view.findViewById(R.id.et_profile_lastName);
                Button bt_applyChanges = (Button) view.findViewById(R.id.bt_profileEdit);

                bt_applyChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProfileActivity.this, ""+et_oldPassword+" "+et_newPassword+" ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });



    }
    private void initializeButton(){
        bt_changeProfile = (Button)findViewById(R.id.bt_profileChenge);
    }
    private void initializeTextViews(){
        tv_firstName = (TextView)findViewById(R.id.tv_profile_firstName);
        tv_lastName = (TextView)findViewById(R.id.tv_profile_lastName);
        tv_email = (TextView)findViewById(R.id.et_profile_email);
        tv_LatestAachievement = (TextView)findViewById(R.id.tv_latestAchievement);
        tv_GreatestAchievement = (TextView)findViewById(R.id.tv_greatestAchievement);
        tv_changePassword = (TextView)findViewById(R.id.tv_changePassword);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            profilePhoto.setImageBitmap(bitmap);
        }
        else if(requestCode == 1){
            if (resultCode == RESULT_OK){
                imageUri = data.getData();
                profilePhoto.setImageURI(imageUri);

            }
        }

    }
}
