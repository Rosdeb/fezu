package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class SlashScreen extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView,lottieAnimationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);

        lottieAnimationView=findViewById(R.id.animationView);
        lottieAnimationView1=findViewById(R.id.animationViews);
        lottieAnimationView1.playAnimation();
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                Boolean check=sharedPreferences.getBoolean("flag",false);

                Intent intent;
                if (check){//user is login
                    intent=new Intent(SlashScreen.this, home.class);

                }else {
//user not login
                    intent=new Intent(SlashScreen.this, login.class);
                }
                startActivity(intent);
                finish();
            }
        },4000);
    }
}