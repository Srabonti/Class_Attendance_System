package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUsername, editTextEmail, editTextPhone, editTextPassword;
    private Button buttonRegister, buttonSignin;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }


        editTextUsername = (EditText) findViewById(R.id.inpUserName);
        editTextEmail = (EditText) findViewById(R.id.inpUserEmail);
        editTextPhone = (EditText) findViewById(R.id.inpUserPhone);
        //editTextAddress = (EditText) findViewById(R.id.inpUserAddress);
        editTextPassword = (EditText) findViewById(R.id.inpUserPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);

        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        buttonSignin.setOnClickListener(this);
    }


    private void registerUser(){

        final String admin_name = editTextUsername.getText().toString().trim();
        final String admin_email = editTextEmail.getText().toString().trim();
        final String admin_contact = editTextPhone.getText().toString().trim();
        //final String admin_address = editTextAddress.getText().toString().trim();
        final String admin_pass = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){
                               /* SharedPrefManager.getInstance(getApplicationContext())
                                        .adminRegistration(jsonObject.getString("admin_name"), jsonObject.getString("admin_email"), jsonObject.getString("admin_contact"), jsonObject.getString("admin_address"));*/
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("admin_name", admin_name);
                params.put("admin_email", admin_email);
                params.put("admin_contact", admin_contact);
                //params.put("admin_address", admin_address);
                params.put("admin_pass", admin_pass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        //RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
        if(v == buttonSignin){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
