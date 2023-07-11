package com.example.finalcpn;


import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Splash extends AppCompatActivity {
    Animation anim;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();





        imageView = findViewById(R.id.imageView2); // Declare an imageView to show the animation
        // .

//        R mean resource .anim mean folder .anime mean file name
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim); // Create the animation.



        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent second = new Intent(getApplicationContext(), login.class);
                        startActivity(second);

                        finish(); // Finish the current activity after starting the next one.
                    }
                }, 3000);

                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        imageView.startAnimation(anim);




        Glide.with(this)
                .load(R.drawable.logo) // your image resource id
                .apply(new RequestOptions().override(1000, 1000))
                // set image size to 600x200 pixels
                .into((ImageView) findViewById(R.id.imageView2));
    }

}