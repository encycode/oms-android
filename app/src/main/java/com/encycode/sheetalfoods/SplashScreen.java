package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation topAnimation, bottomAnimation;
    ImageView logoTop;
    TextView logoBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation);

        logoTop = findViewById(R.id.splash_logo_top);
        logoBottom = findViewById(R.id.splash_logo_bottom);

        logoTop.setAnimation(topAnimation);
        logoBottom.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}