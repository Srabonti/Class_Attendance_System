package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Attendance extends AppCompatActivity {
    EditText editText;
    Button buttonAttendance;
    ProgressDialog progressDialog;
    Spinner spinner, spinner2;
    RequestQueue requestQueue;
    List<String> arrayList, listCourseCode;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        editText = (EditText) findViewById(R.id.takingID);
        buttonAttendance = (Button) findViewById(R.id.submitAttendance);

        requestQueue = Volley.newRequestQueue(this);

        String time = dateSet();
        if (time != null) {
            TextView textView = (TextView) findViewById(R.id.dateTime);
            textView.setText(time);
            textView.setTextSize(20);

            semesterPick();

        }


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

            spinner.setAdapter(new ArrayAdapter<>(Attendance.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, arrayList.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {

                        String semesterName = spinner.getSelectedItem().toString();

                        getCourseC(semesterName);
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


    private void getCourseC(String semesterName) {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GETCOURSE + "?sem=" + semesterName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        progressDialog.dismiss();
                        populateCourseSpinner(response);
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

    private void populateCourseSpinner(String response) {
        try {
            JSONObject cs = new JSONObject(response);
            JSONArray jsonArray = cs.getJSONArray("courseIds");

            //arrayList = new ArrayList<SemesterModel>();

            listCourseCode = new ArrayList<>();
            listCourseCode.add("Select Course Code");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject csId = jsonArray.getJSONObject(i);
                String csCode = csId.getString("course_id");
                listCourseCode.add(csCode);
            }
            Log.e("Response", "" + arrayList.size());

            spinner2.setAdapter(new ArrayAdapter<>(Attendance.this, android.R.layout.simple_spinner_dropdown_item, listCourseCode));
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, listCourseCode.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {
                        //Toast.makeText(Attendance.this, listCourseCode.get(position), Toast.LENGTH_LONG).show();

                        submitAttendance(editText.getText().toString());
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

    private String dateSet() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        //DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        date.setTimeZone(TimeZone.getTimeZone("GMT"));
        String localTime = date.format(currentLocalTime);

        return localTime;
    }

    private void submitAttendance(final String s) {
        /*progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Submitting attendance...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();*/



        buttonAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert data to attendance table
                final String string = "P";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_TATTENDANCE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //progressDialog.dismiss();
                                Log.e("Response Attendance:", response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (!jsonObject.getBoolean("error")) {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("attendance_date", dateSet());
                        params.put("attendance_state", string);
                        params.put("course_id", spinner2.getSelectedItem().toString());
                        params.put("semester_id", spinner.getSelectedItem().toString());
                        params.put("student_id", s);

                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
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
