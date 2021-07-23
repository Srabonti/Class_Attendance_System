package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendSMS extends AppCompatActivity {
    EditText editTextSendSMS;
    private Button buttonSendSMS;
    ProgressDialog progressDialog;
    Spinner spinner;
    RequestQueue requestQueue;
    List<String> arrayList, listContact;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        spinner = (Spinner) findViewById(R.id.spin);
        editTextSendSMS = (EditText) findViewById(R.id.sms);
        buttonSendSMS = (Button) findViewById(R.id.sendingSMS);

        listContact = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        semesterPick();

    }

    private void semesterPick() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting Student Data...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GETSEMESTERDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        progressDialog.dismiss();
                        updateUI(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("Error response", error.toString());
                    }
                });

        requestQueue.add(stringRequest);
    }

    private void updateUI(String response) {
        try {
            JSONObject sem = new JSONObject(response);
            JSONArray jsonArray = sem.getJSONArray("semesters");

            arrayList = new ArrayList<>();
            arrayList.add("Select Semester");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject student = jsonArray.getJSONObject(i);
                String semesterID = student.getString("semester_id");
                arrayList.add(semesterID);
            }
            Log.e("Response", "" + arrayList.size());

            spinner.setAdapter(new ArrayAdapter<>(SendSMS.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, arrayList.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {

                        String semesterName = spinner.getSelectedItem().toString();
                        getContact(semesterName);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
            //e.printStackTrace();
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        }


    }

    private void getContact(final String semesterName) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETCONTACT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Contact response", response);
                        parsingContact(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("semester_id", semesterName);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void parsingContact(String response) {

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");

            for (int i=0; i<jsonArray.length(); i++){
                JSONObject contactG = jsonArray.getJSONObject(i);
                String number = contactG.getString("guardian_contact");

                listContact.add(number);
            }

            Log.e("Response", "" + arrayList.size());

            //Sending sms through button click...
            buttonSendSMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editTextSendSMS.getText().toString();
                    boolean flag = true;
                    int i;

                    for(i=0; i<listContact.size(); i++){
                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(listContact.get(i), null, message, null, null);
                        } catch (Exception e) {
                            flag = false;
                            e.printStackTrace();
                        }
                    }
                    if(flag && (i == listContact.size())){
                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(!flag){
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.menuAbout:
                startActivity(new Intent(this, About.class));
                break;
            case R.id.menuDocumentation:
                startActivity(new Intent(this, Documentation.class));
                break;
            case R.id.menuLogout:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return true;
    }



}
