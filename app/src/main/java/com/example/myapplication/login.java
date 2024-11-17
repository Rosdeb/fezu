package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    RequestQueue requestQueue;
    int code;
    ImageView backbuttom;
    String message,accessToken;

    TextView sinq_up,forget_password;
    private Dialog loading;
    MaterialButton materialButton;
    TextInputEditText email1,password1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        materialButton=findViewById(R.id.singhin);
        backbuttom=findViewById(R.id.backbutton);
        backbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        forget_password=findViewById(R.id.forget_email);
        sinq_up=findViewById(R.id.sing_up);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(login.this,Forgetpass.class));
            }
        });


        sinq_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(login.this,home.class));
            }
        });
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();

            }
        });



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
                    code = jsonResponse.getInt("code");
                    message = jsonResponse.getString("message");
                    if (code == 200) { // Check for success code
                        // Navigate through the JSON structure to get the access token
                          JSONObject data = jsonResponse.getJSONObject("data");
                            JSONObject attributes = data.getJSONObject("attributes");
                            JSONObject tokens = attributes.getJSONObject("tokens");
                           JSONObject access = tokens.getJSONObject("access");
                           String accessToken = access.getString("token");

                        // Save access token in SharedPreferences

                        // Display success message


                        Toast.makeText(login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);


                    } else if (code==400) {
                        // Handle failure message

                        Toast.makeText(login.this, "Login failed: " + message, Toast.LENGTH_SHORT).show();

                    } else if (code==404) {
                        // Handle failure message
                        Toast.makeText(login.this, "Login failed: " + message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                      e.printStackTrace();
                    Toast.makeText(login.this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (code==401){
                    Log.e("Volley Error", error.toString());
                    Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                }
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
    private void loginuser(){


        email1=findViewById(R.id.email_input);
        password1=findViewById(R.id.password_input);

        String email=email1.getText().toString();
        String password=password1.getText().toString();
        showLoadingDialog();

            String url = "http://192.168.31.96:8081/v1/auth/login"; // Replace with your API URL
           /* String email = "rosdekoc12@gmail.com";
            String password = "1qazxsw2";
*/

            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            hideLoadingDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                String message = jsonObject.getString("message");
                                if (code==200) {
                                    // Handle successful login, e.g., go to main activity
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    JSONObject attributes = data.getJSONObject("attributes");
                                    JSONObject tokens = attributes.getJSONObject("tokens");
                                    JSONObject access = tokens.getJSONObject("access");
                                    accessToken = access.getString("token");
                                    saveAccessToken(accessToken);

                                    startActivity(new Intent(login.this,home.class));

                                    Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(login.this, "Login Failed: " + message, Toast.LENGTH_SHORT).show();

                                }
                                // Handle login failure

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideLoadingDialog();
                            Toast.makeText(login.this, "email and password incorect", Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() {
                    // Send email and password as POST parameters
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };


            // Add request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }






//    public static void clearAccessToken(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove(KEY_ACCESS_TOKEN);
//        editor.apply();
//    }

   /* public static void saveAccessToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences("name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("KEY_ACCESS_TOKEN", token);
        editor.apply(); // Save asynchronously
    }*/


    private void showLoadingDialog() {
        if (loading == null) {
            loading = new Dialog(this);
            loading.setContentView(R.layout.dialog_loading);
            loading.setCancelable(false); // Prevent closing the dialog
            loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        loading.show();
    }
    private void hideLoadingDialog() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }


    private void saveAccessToken(String token) {
        // Get the SharedPreferences editor
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Save the token
        editor.putString("KEY_ACCESS_TOKEN", token);
        editor.putBoolean("flag",true);
        editor.apply(); // Apply changes asynchronously
    }
}