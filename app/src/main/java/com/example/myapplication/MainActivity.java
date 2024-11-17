package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    private TextInputEditText name,numbor,passport,location,email,password;
    private Button register,login;
    String[] courses = { "Dhaka", "Mymensingh",
            "Rajshahi", "Borisal",
            "Chattrogram", "Rangpur","Sylhet" ,"Khulna"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        name=findViewById(R.id.name);
//          numbor=findViewById(R.id.phonenumbor);
//            passport=findViewById(R.id.passpotnumbor);
//             location=findViewById(R.id.location);
//               email=findViewById(R.id.email);
//             password=findViewById(R.id.password);
//        register=findViewById(R.id.register);
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerUser();
//                SharedPreferences sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.putBoolean("flag",false);
//                editor.apply();
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);
//
//            }
//        });
       // login=findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                load();
//            }
//        });

    }

    private void registerUser() {
        String name1=name.getText().toString().trim();
        String numbor1=numbor.getText().toString().trim();
        String passpot1=passport.getText().toString().trim();
        String location1=location.getText().toString().trim();
        String email1=email.getText().toString().trim();
        String password1=password.getText().toString().trim();

        //intent send other activity---------------
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("email",email1);
        startActivity(intent);

        // Validate input fields
        if (TextUtils.isEmpty(name1)) {
            name.setError("Name not required");
            return;
        }
        if (TextUtils.isEmpty(numbor1)) {
            numbor.setError("numbor not required");
            return;
        }
        if (TextUtils.isEmpty(passpot1)) {
            passport.setError("passportnumbor not required");
            return;
        }
        if (TextUtils.isEmpty(location1)) {
            location.setError("location not required");

            return;
        }
        if (TextUtils.isEmpty(email1)) {
            email.setError("Email not required");
            return;
        }

        if (TextUtils.isEmpty(password1)) {
            password.setError("Password not required");
            return;
        }
        // After validation, you can proceed with registration logic (e.g., send to server)------========
        requestQueue= Volley.newRequestQueue(this);
        String url = "http://192.168.31.96:8081/v1/auth/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the API response
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.getString("message");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "JSON error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", error.toString());
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fullName", name1);
                params.put("email", email1);
                params.put("password", password1);
                params.put("phoneNumber", numbor1);
                params.put("location", location1);
                params.put("idOrPassport", passpot1);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void  load(){
       String email="rosdekoc12@gmail.com";
        String password="1qazxsw2";


        String URL="http://192.168.31.96:8081/v1/auth/login";

        requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try {
                        // Parse JSON response
                        JSONObject jsonResponse = new JSONObject(response);
                        int code = jsonResponse.getInt("code");
                        if (code == 200) { // Check for success code
                            // Navigate through the JSON structure to get the access token
                            JSONObject data = jsonResponse.getJSONObject("data");
                            JSONObject attributes = data.getJSONObject("attributes");
                            JSONObject tokens = attributes.getJSONObject("tokens");
                            JSONObject access = tokens.getJSONObject("access");
                            String accessToken = access.getString("token");

                            // Save access token in SharedPreferences
                           saveAccessToken(accessToken);

                            // Display success message
                            Toast.makeText(MainActivity.this, "Login successful!"+accessToken, Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle failure message
                            String message = jsonResponse.getString("message");
                            Toast.makeText(MainActivity.this, "Login failed: " + message, Toast.LENGTH_SHORT).show();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(MainActivity.this, "Login error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Send email and password as POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(request);



    }
    private void saveAccessToken(String token){


        SharedPreferences sharedPreferences= getSharedPreferences("Rosdeb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("access_token",token);
        editor.apply();
    }
    private String getAccessToken(){
        SharedPreferences sharedPreferences=getSharedPreferences("Rosdeb",Context.MODE_PRIVATE);
        return sharedPreferences.getString("access_token",null);

    }

private void profileshow(){

        String profile_url="http://192.168.31.96:8081/v1/profile";
     SharedPreferences sharedPreferences=getSharedPreferences("Rosdeb",Context.MODE_PRIVATE);
     String accesstoekn =sharedPreferences.getString("access_token",null);

    if (accesstoekn == null) {
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
                            JSONObject userData = response.getJSONObject("data")
                                    .getJSONObject("attributes")
                                    .getJSONObject("user");

                            String fullName = userData.optString("fullName", "N/A");
                            String email = userData.optString("email", "N/A");
                            String phoneNumber = userData.optString("phoneNumber", "N/A");

                            // Display data
//                            fullNameTextView.setText("Full Name: " + fullName);
//                            emailTextView.setText("Email: " + email);
//                            phoneTextView.setText("Phone: " + phoneNumber);
                            Toast.makeText(MainActivity.this, "fullname"+fullName+"email"+email+"phone"+phoneNumber, Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String,String> header=new HashMap<>();
            header.put("Authorization","Bearer "+accesstoekn);
            return header;
        }
    };

    requestQueue.add(profileRequest);



}

}