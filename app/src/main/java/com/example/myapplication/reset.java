package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class reset extends AppCompatActivity {

    RequestQueue requestQueue;
    TextInputEditText email_send,newpassword,confrimpassword;
    Dialog loading;
    MaterialButton reset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        email_send =findViewById(R.id.enter_email);
        newpassword=findViewById(R.id.new_password);
        confrimpassword=findViewById(R.id.confrim_password);
        reset=findViewById(R.id.reset_btn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();
            }
        });



    }


    private void resetPassword(){

        String getemial=email_send.getText().toString().trim();
        String newpass=newpassword.getText().toString().trim();
        String confrimpass=confrimpassword.getText().toString().trim();

        if (getemial.isEmpty()){
            email_send.setError("enter your email");
            return;
        }
        if (newpass.isEmpty()){
            newpassword.setError("enter your new password");
            return;
        }if (!newpass.equals(confrimpass)){
            confrimpassword.setError("Passwords do not match");
            return;

        }
        showdialog();
        SharedPreferences prefs = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        String token=prefs.getString("KEY_ACCESS_TOKEN",null);

        String url="http://192.168.31.96:8081/v1/auth/reset-password";

        JSONObject request=new JSONObject();
        try {
            request.put("email",getemial);
            request.put("password",confrimpass);
        }catch (JSONException e){
            e.printStackTrace();
        }

        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hidedialog();
                try {

                    int code =response.getInt("code");
                    String message=response.getString("message");

                    if (code == 200) {
                        // Password change was successful
                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(reset.this, home.class));
                    } else {
                        // Handle other codes as needed
                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();

                    }
                }catch (JSONException e){

                 e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidedialog();
                Log.e("Volley Error", error.toString());
                Toast.makeText(reset.this, "error null", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token); // Add JWT token in header
                headers.put("Content-Type", "application/json"); // Set content type
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);



    }

    private void showdialog(){

        if (loading==null){

            loading = new Dialog(this);
            loading.setCancelable(false);
            loading.setContentView(R.layout.dialog_loading);
            loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        loading.show();


    }

    private void hidedialog(){

        if(loading!=null&& loading.isShowing()){

            loading.dismiss();
        }


    }
}