package com.example.sazzad.cas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Settings extends AppCompatActivity implements View.OnClickListener{
    private Button buttonResetCourses, buttonResetStudents, buttonResetSemesters, buttonResetAttendance;
    RequestQueue requestQueue;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        buttonResetCourses = (Button) findViewById(R.id.resetCourses);
        buttonResetStudents  = (Button) findViewById(R.id.resetStudents);
        buttonResetSemesters = (Button) findViewById(R.id.resetSemesters);
        buttonResetAttendance = (Button) findViewById(R.id.resetAttendance);

        buttonResetCourses.setOnClickListener(this);
        buttonResetStudents.setOnClickListener(this);
        buttonResetSemesters.setOnClickListener(this);
        buttonResetAttendance.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonResetCourses){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

            builder.setTitle("Warning!!!");
            builder.setMessage("Are you sure you want to reset courses???\nResetting courses will delete all the records of courses permanently.");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do the other function
                    //Toast.makeText(getApplicationContext(), "Resetting courses have been done successfully!", Toast.LENGTH_LONG).show();
                    backgroundTask(Constants.URL_DELETECOURSE);
                }
            });

            builder.setNegativeButton("No", null);

            builder.create();
            builder.show();
        }
        if(v == buttonResetStudents){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

            builder.setTitle("Warning!!!");
            builder.setMessage("Are you sure you want to reset students???\nResetting students will delete all the records of students permanently.");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do the other function
                    //Toast.makeText(getApplicationContext(), "Resetting students have been done successfully!", Toast.LENGTH_LONG).show();
                    backgroundTask(Constants.URL_DELETESTUDENTS);
                }
            });

            builder.setNegativeButton("No", null);

            builder.create();
            builder.show();
        }
        if(v == buttonResetSemesters){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

            builder.setTitle("Warning!!!");
            builder.setMessage("Are you sure you want to reset semesters???\nResetting semesters will delete all the records of semesters permanently.");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do the other function
                    //Toast.makeText(getApplicationContext(), "Resetting semesters have been done successfully!", Toast.LENGTH_LONG).show();
                    backgroundTask(Constants.URL_DELETESEMESTERS);
                }
            });

            builder.setNegativeButton("No", null);

            builder.create();
            builder.show();
        }

        if(v == buttonResetAttendance){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

            builder.setTitle("Warning!!!");
            builder.setMessage("Are you sure you want to reset attendance records???\nResetting attendance records will delete all the records of attendance permanently.");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Do the other function
                    //Toast.makeText(getApplicationContext(), "Resetting attendance records have been done successfully!", Toast.LENGTH_LONG).show();
                    backgroundTask(Constants.URL_DELETEATTENDANCE);
                }
            });

            builder.setNegativeButton("No", null);

            builder.create();
            builder.show();
        }
    }

    private void backgroundTask(String url){
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            else{
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
                        Log.e("Error response: ", error.toString());
                    }
                });
        requestQueue.add(stringRequest);
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
                Toast.makeText(this, "You are in this context!", Toast.LENGTH_LONG).show();
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
