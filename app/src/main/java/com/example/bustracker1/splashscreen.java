package com.example.bustracker1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2000;

    TextView title;
    ImageView logo;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        title = (TextView) findViewById(R.id.title);
        logo = (ImageView) findViewById(R.id.logo);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animations);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animations);

     //Set animation to elements

        logo.setAnimation(topAnim);
        title.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splashscreen.this,login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }
}