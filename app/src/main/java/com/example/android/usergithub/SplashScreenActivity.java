package com.example.android.usergithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    Animation top, bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animasi);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animasi);

        ImageView imgSplash = findViewById(R.id.img_splash);
        TextView tvSplash = findViewById(R.id.tv_splash);

        imgSplash.setAnimation(top);
        tvSplash.setAnimation(bottom);

        Thread going = new Thread(){
            public void run(){
                try {
                    sleep(4100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        };
        going.start();
    }
}