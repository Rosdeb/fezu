package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Changepass extends AppCompatActivity {

    TextInputEditText oldpass,nepass,confrim;
    MaterialButton changes;
    private Dialog loading;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);


        oldpass=findViewById(R.id.old_password);
        nepass=findViewById(R.id.new_password);
        confrim=findViewById(R.id.confrim_password);

        changes=findViewById(R.id.changes_pass);
        changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepass();
            }
        });



    }

    private void  changepass(){
        String old=oldpass.getText().toString();
        String newspass=nepass.getText().toString();
        String confrimpasss=confrim.getText().toString();
        if (old.isEmpty()) {
            oldpass.setError("Current password required");
            return;
        }
        if (newspass.isEmpty()) {
            nepass.setError("New password required");
            return;
        }
        if (!newspass.equals(confrimpasss)) {
            confrim.setError("Passwords do not match");
            return;
        }
        showDailog();


        //get access token -----------
         SharedPreferences prefs = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
         String token=prefs.getString("KEY_ACCESS_TOKEN",null);

//url load change password
        String URL="http://192.168.31.96:8081/v1/auth/change-password";


        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("oldPassword",old);
            requestBody.put("newPassword", confrimpasss);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hidedailog();
                try {
                    int code = response.getInt("code");
                    String message = response.getString("message");

                    if (code == 200) {
                        // Password change was successful
                       Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Changepass.this, home.class));
                    } else {
                        // Handle other codes as needed
                        System.out.println("Error: " + message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidedailog();
                Log.e("Volley Error", error.toString());
                Toast.makeText(Changepass.this, "error null", Toast.LENGTH_SHORT).show();

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
        requestQueue.add(request);

    }
    private void showDailog(){

        if (loading==null){
            loading=new Dialog(this);
            loading.setContentView(R.layout.dialog_loading);
            loading.setCancelable(false);
            loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        }
        loading.show();

    }

    private void hidedailog(){
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }
}