package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail, editTextPassword;
    private Button buttonRegister, buttonSignIn;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    private TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        //toolbar.setBackgroundColor();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        editTextEmail = (EditText) findViewById(R.id.inpLoginEmail);
        editTextPassword = (EditText) findViewById(R.id.inpLoginPassword);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonRegister = (Button) findViewById(R.id.buttonSignUp);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        buttonSignIn.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

    }

    private void userLogin(){
        final String admin_email = editTextEmail.getText().toString().trim();
        final String admin_pass = editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.e("Login Response",response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                /*SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(jsonObject.getInt("admin_id"), jsonObject.getString("admin_name"), jsonObject.getString("admin_email"), jsonObject.getString("admin_contact"));*/
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("admin_email", admin_email);
                params.put("admin_pass", admin_pass);

                return params;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSignIn){
            userLogin();
        }
        if(v == buttonRegister){
            startActivity(new Intent(this, register.class));
        }
    }
}
