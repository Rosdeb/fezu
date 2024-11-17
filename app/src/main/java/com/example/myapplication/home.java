package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    ImageSlider imageSlider;
    ArrayList<SlideModel> slideModels;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView imageView;
    MaterialButton login;
    TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        imageSlider=findViewById(R.id.image_slider);
        slideModels=new ArrayList<SlideModel>();
        slideModels.add(new SlideModel(R.drawable.shop1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.shop2,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.shop4,ScaleTypes.FIT));
        imageSlider.setSlideAnimation(AnimationTypes.CUBE_OUT);
        imageSlider.setImageList(slideModels);

        drawerLayout=findViewById(R.id.drawer_layout);
        imageView=findViewById(R.id.drower_show);

        navigationView=findViewById(R.id.navigation_view);
       /* toolbar=findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        toggle=new ActionBarDrawerToggle(home.this,drawerLayout,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        navigationView.bringToFront();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);


            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               int id=item.getItemId();
               if(id==R.id.profile){
                 startActivity(new Intent(home.this, My_profile.class));

               } else if (id==R.id.profiles) {

               } else if (id==R.id.abouts) {
                   startActivity(new Intent(home.this, Abouts_us.class));


               } else if (id==R.id.subscripion) {
                   startActivity(new Intent(home.this, Subscripion.class));


               } else if (id==R.id.change) {
                   startActivity(new Intent(home.this, Changepass.class));

               }  else if (id==R.id.howitwork) {
                   startActivity(new Intent(home.this, How_it_work.class));


               } else if (id==R.id.reset_password) {
                   startActivity(new Intent(home.this, reset.class));


               }
               drawerLayout.closeDrawers();
               return true;
           }
       });

        textView=findViewById(R.id.profile_use);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, My_profile.class));
            }
        });

        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login.setText("Login");
                SharedPreferences sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("flag",false);
                editor.apply();


            }
        });





    }

}