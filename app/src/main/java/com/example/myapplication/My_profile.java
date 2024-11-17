package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class My_profile extends AppCompatActivity {


    RatingBar ratingBar;
    String accesstoekn;
    ImageView imageView,home;

    NavigationView navigationView_m;
    DrawerLayout drawerLayout_m;

    TextView username,email_get,addresss,phone,viewss;

    RequestQueue requestQueue;
    CardView edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ratingBar=findViewById(R.id.ration_user);

        drawerLayout_m=findViewById(R.id.drawer_layout_m);
        navigationView_m=findViewById(R.id.navigations);
        home=findViewById(R.id.fuez);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(My_profile.this,home.class));
                finish();

            }
        });


        imageView=findViewById(R.id.drower_show);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout_m.openDrawer(navigationView_m);
            }
        });
        navigationView_m.bringToFront();
        navigationView_m.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

                if(id==R.id.profile){
                    startActivity(new Intent(My_profile.this, My_profile.class));

                }else if(id==R.id.home){
                    startActivity(new Intent(My_profile.this, home.class));


                }
                else if (id==R.id.profiles) {

                } else if (id==R.id.abouts) {
                    startActivity(new Intent(My_profile.this, Abouts_us.class));


                } else if (id==R.id.subscripion) {
                    startActivity(new Intent(My_profile.this, Subscripion.class));


                } else if (id==R.id.change) {
                    startActivity(new Intent(My_profile.this, Changepass.class));

                }  else if (id==R.id.howitwork) {
                    startActivity(new Intent(My_profile.this, How_it_work.class));


                } else if (id==R.id.reset_password) {
                    startActivity(new Intent(My_profile.this, reset.class));


                }
                drawerLayout_m.closeDrawers();
                return true;
            }
        });

        username=findViewById(R.id.user_name);
        addresss=findViewById(R.id.location);
        viewss=findViewById(R.id.views);
        phone=findViewById(R.id.phonenumbor);
        email_get=findViewById(R.id.email_get);
        profileshow(this);


        //edit profile and update in role of premium and basic user

        edit=findViewById(R.id.edit_profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileEdit();
            }
        });

    }


    


    /*private void  getshowprofile(){

        String profile_url="http://192.168.31.96:8081/v1/profile";

        SharedPreferences sharedPreferences =getSharedPreferences("name",MODE_PRIVATE);
        String accesstoekn =sharedPreferences.getString("access_token",null);

        if (accesstoekn == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, profile_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getInt("code")==200){

                        JSONObject data = response.getJSONObject("data");
                        JSONObject attributes = data.getJSONObject("attributes");
                        JSONObject user = attributes.getJSONObject("user");
                        String name=user.getString("fullName");
                        String email=user.getString("email");
                        String phonenumbor=user.getString("phoneNumber");

                        username.setText(name);

                        JSONObject review = user.getJSONObject("review");

                        // Get the rating value from JSON
                        float rating = (float) review.getDouble("rating");



                        // Set rating to the RatingBar
                        ratingBar.setRating(rating);


                    }else {
                        Toast.makeText(getApplicationContext(),"404", Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){

                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("Authorization","Bearer"+accesstoekn);
                return hashMap;

            }
        };
        requestQueue.add(jsonObjectRequest);


    }*/




    private void profileshow(Context context){

        String profile_url="http://192.168.31.96:8081/v1/profile";
        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("KEY_ACCESS_TOKEN", null);

        if (accessToken == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest profileRequest = new JsonObjectRequest(
                Request.Method.GET,
                profile_url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("code") == 200) {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject attributes = data.getJSONObject("attributes");
                                JSONObject user = attributes.getJSONObject("user");

                                String fullName = user.optString("fullName", "N/A");
                                String email = user.optString("email", "N/A");
                                String address = user.optString("location", "N/A");
                                String phoneNumber = user.optString("phoneNumber", "N/A");
                                username.setText(fullName);
                                email_get.setText(email);
                                addresss.setText(address);
                                phone.setText(phoneNumber);

                                JSONObject review = user.getJSONObject("review");

                                // Get the rating value from JSON
                                float rating = (float) review.getDouble("rating");
                                int views=review.getInt("total");
                                viewss.setText("Views:"+views);

                                // Set rating to the RatingBar
                                ratingBar.setRating(rating);


                                // Display data
//                            fullNameTextView.setText("Full Name: " + fullName);
//                            emailTextView.setText("Email: " + email);
//                            phoneTextView.setText("Phone: " + phoneNumber);


                            } else {
                                Toast.makeText(My_profile.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(My_profile.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<>();
                header.put("Authorization","Bearer "+accessToken);
                return header;
            }
        };

        requestQueue.add(profileRequest);



    }


    private void ProfileEdit(){

        String URL="http://192.168.31.96:8081/v1/profile";

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("KEY_ACCESS_TOKEN", null);

        if (accessToken==null){
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;

        }
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        JsonObjectRequest requestq = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getInt("code")==200){
                        JSONObject data=response.getJSONObject("data");
                        JSONObject addd=data.getJSONObject("attributes");
                        JSONObject user=addd.getJSONObject("user");

                        String role =user.getString("role");
                        if ("premium".equalsIgnoreCase(role)) {
                            enableProfileedit();

                        }else {
                            disableProfileedit();
                        }

                    }



                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                 HashMap<String,String> hashMap = new HashMap<>();
                 hashMap.put("Authorization","Bearer "+accessToken);
                 return hashMap;
            }
        };

        requestQueue1.add(requestq);



    }

    private void enableProfileedit(){

        edit.setEnabled(true);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
    private void disableProfileedit(){

        edit.setEnabled(false);
        edit.setOnClickListener(null);
        Toast.makeText(getApplicationContext(),"user not premium",Toast.LENGTH_SHORT).show();
    }

}