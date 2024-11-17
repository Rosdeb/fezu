package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    RequestQueue requestQueue;
    private TextInputEditText code,email;

    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        code=findViewById(R.id.code);
        email=findViewById(R.id.email);

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();

            }
        });






    }


    private void  load(){

        String st=code.getText().toString().trim();
        String email1=email.getText().toString().trim();

        String URL="http://192.168.31.96:8081/v1/auth/verify-email";

        requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    if (jsonObject.getInt("code") == 200) {
                        Toast.makeText(MainActivity2.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                    } else if (jsonObject.getInt("code")==404) {
                        Toast.makeText(MainActivity2.this," code not match", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity2.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(MainActivity2.this, "Login error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Send email and password as POST parameters
                Map<String, String> paramsd = new HashMap<>();
                paramsd.put("email", email1);
                paramsd.put("oneTimeCode", st);
                return paramsd;
            }
        };
        requestQueue.add(request);



    }
}