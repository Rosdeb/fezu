package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.animations.Toss;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kotlinx.coroutines.scheduling.Task;

public class Forgetpass extends AppCompatActivity {

    TextInputEditText getot;
    MaterialButton send_otp;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        getot =findViewById(R.id.get_otp);
        send_otp =findViewById(R.id.otp_send);
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              resetpasss();
            }
        });

    }
    private void  resetpasss(){

        String email=getot.getText().toString();
        if (TextUtils.isEmpty(email)) {
            getot.findFocus();
            getot.setError("please enter your email");
            return;
        }

        String URL="http://192.168.31.96:8081/v1/auth/forgot-password";

        requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code=jsonObject.getInt("code");
                    if (code==200){
                       Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(Forgetpass.this,Changepass.class));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                    Log.e("Volley Error", error.toString());
                    Toast.makeText(Forgetpass.this, "error null", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Send email and password as POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        requestQueue.add(request);



    }


}