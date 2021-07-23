package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddingStudents extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextStudentId, editTextName, editTextStdContact, editTextEmail, editTextAddress, editTextGrdName, editTextGrdContact, editTextRelation, editTextSemesterId;
    private Button buttonStdDataSave;
    private ProgressDialog progressDialog;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_students);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        editTextStudentId = (EditText) findViewById(R.id.inpStudentId);
        editTextName = (EditText) findViewById(R.id.inpName);
        editTextStdContact = (EditText) findViewById(R.id.inpContact);
        editTextEmail = (EditText) findViewById(R.id.inpEmail);
        editTextAddress = (EditText) findViewById(R.id.inpAddress);
        editTextGrdName = (EditText) findViewById(R.id.inpGrdName);
        editTextGrdContact = (EditText) findViewById(R.id.inpGrdContact);
        editTextRelation = (EditText) findViewById(R.id.inpRelation);
        editTextSemesterId = (EditText) findViewById(R.id.inpSemesterIdSTD);

        buttonStdDataSave = (Button) findViewById(R.id.stdDataSave);

        progressDialog = new ProgressDialog(this);

        buttonStdDataSave.setOnClickListener(this);

    }

    private void insertStudentData(){
        final String student_id = editTextStudentId.getText().toString();
        final String student_name = editTextName.getText().toString().trim();
        final String student_contact = editTextStdContact.getText().toString().trim();
        final String student_email = editTextEmail.getText().toString().trim();
        final String student_address = editTextAddress.getText().toString();
        final String guardian_name = editTextGrdName.getText().toString().trim();
        final String guardian_contact = editTextGrdContact.getText().toString().trim();
        final String relation = editTextRelation.getText().toString().trim();
        final String semester_id = editTextSemesterId.getText().toString().trim();

        progressDialog.setMessage("Saving student data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SAVINGSTUDENTDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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
                params.put("student_id", student_id);
                params.put("student_name", student_name);
                params.put("student_contact", student_contact);
                params.put("student_email", student_email);
                params.put("student_address", student_address);
                params.put("guardian_name", guardian_name);
                params.put("guardian_contact", guardian_contact);
                params.put("relation", relation);
                params.put("semester_id", semester_id);

                return params;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

    @Override
    public void onClick(View v) {
        if(v == buttonStdDataSave){
            insertStudentData();
        }
    }
}
