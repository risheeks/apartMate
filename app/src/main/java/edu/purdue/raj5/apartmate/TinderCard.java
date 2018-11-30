package edu.purdue.raj5.apartmate;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import java.io.ByteArrayOutputStream;

@Layout(R.layout.tinder_car_view)
public class TinderCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;
    private Bitmap bmp;
    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    private String email;
    public TinderCard(Context context, Profile profile, SwipePlaceHolderView swipeView, Bitmap bmp) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
        this.bmp = bmp;
      //  profileImageView.setImageBitmap(bmp);
    }

    @Resolve
    private void onResolved(){
      // Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
        profileImageView.setImageBitmap(bmp);
        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
        locationNameTxt.setText(mProfile.getLocation());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        Log.d("Name:",this.mProfile.getName());
        String email = this.mProfile.getEmail();

        LoginActivity.sock.send("ADD_LIKED_USERS;"+TinderActivity.currentUserEmail+";"+email);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
        TinderActivity.currentCardEmail = this.mProfile.getEmail();
        TinderActivity.updateSmoke();
        TinderActivity.updateDrink();
        TinderActivity.updateInterests();
        TinderActivity.updateRating();
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}