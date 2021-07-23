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

public class AddingSemester extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextSemesterId, editTextSemesterName;
    private Button buttonSemesterDataSave;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_semester);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        editTextSemesterId = (EditText) findViewById(R.id.inpSmstrId);
        editTextSemesterName = (EditText) findViewById(R.id.inpSmstrName);

        buttonSemesterDataSave = (Button) findViewById(R.id.smstrDataSave);

        progressDialog = new ProgressDialog(this);

        buttonSemesterDataSave.setOnClickListener(this);

    }

    private void insertSemesterData(){
        final String semester_id = editTextSemesterId.getText().toString().trim();
        final String semester_name = editTextSemesterName.getText().toString().trim();

        progressDialog.setMessage("Saving semester data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SAVINGSEMESTERDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                /*SharedPrefManager.getInstance(getApplicationContext())
                                        .insertSemesterRecord(jsonObject.getString("semester_id"), jsonObject.getString("semester_name"));*/
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
                params.put("semester_id", semester_id);
                params.put("semester_name", semester_name);

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
        if(v == buttonSemesterDataSave){
            insertSemesterData();
        }
    }

}
