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

public class AddingCourses extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextCourseId, editTextCourseTitle, editTextCourseCredit, editTextCourseType, editTextTotalClass, editTextSemester;
    private Button buttonSaveCrsData;
    private ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_courses);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        editTextCourseId = (EditText) findViewById(R.id.inpCourseId);
        editTextCourseTitle = (EditText) findViewById(R.id.inpCourseTitle);
        editTextCourseCredit = (EditText) findViewById(R.id.inpCourseCredit);
        editTextCourseType = (EditText) findViewById(R.id.inpCourseType);
        editTextTotalClass = (EditText) findViewById(R.id.inpTotalClasses);
        editTextSemester = (EditText) findViewById(R.id.inpSemester);

        buttonSaveCrsData = (Button) findViewById(R.id.buttonSaveCrsData);

        progressDialog = new ProgressDialog(this);

        buttonSaveCrsData.setOnClickListener(this);

    }

    private void insertCourses(){
        final String course_id = editTextCourseId.getText().toString().trim();
        final String course_title = editTextCourseTitle.getText().toString().trim();
        final String course_credit = editTextCourseCredit.getText().toString().trim();
        final String course_type = editTextCourseType.getText().toString().trim();
        final String total_class_no = editTextTotalClass.getText().toString().trim();
        final String semester_id = editTextSemester.getText().toString().trim();

        int c_credit = Integer.parseInt(course_credit);
        int total_class = Integer.parseInt(total_class_no);

        progressDialog.setMessage("Saving course data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SAVINGCOURSEDATA,
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
                params.put("course_id", course_id);
                params.put("course_title", course_title);
                params.put("course_credit", course_credit);
                params.put("course_type", course_type);
                params.put("total_class_no", total_class_no);
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
        if(v == buttonSaveCrsData){
            insertCourses();
        }
    }
}
