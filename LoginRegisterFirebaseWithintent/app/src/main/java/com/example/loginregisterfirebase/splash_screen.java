package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    TextView wel,learning;
    private static int Splash_timeout = 5000;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        wel = findViewById(R.id.textView1);
        learning = findViewById(R.id.textView2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent = new Intent(splash_screen.this,Login.class);
                startActivity(splashintent);
                finish();
            }
        },Splash_timeout);
        Animation myanimation = AnimationUtils.loadAnimation(splash_screen.this,R.anim.animation2);
        wel.startAnimation(myanimation);
        Animation myanimation2 = AnimationUtils.loadAnimation(splash_screen.this,R.anim.animation1);
        learning.setAnimation(myanimation2);

    }
}