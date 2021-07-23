package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class AttendanceSheet extends AppCompatActivity implements AttendanceClickListener {
    List<AttendanceModel> attnList, aList;
    private RecyclerView arecyclerView;
    private ProgressDialog aprogressDialog;
    AttendanceAdapter attnAdapter;
    Spinner myspinner, myspinner2;
    RequestQueue requestQueue;
    List<String> arrayList, listCourseCode, listContact;
    Button savingAtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_sheet);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        myspinner = (Spinner) findViewById(R.id.myspinner);
        myspinner2 = (Spinner) findViewById(R.id.myspinner2);
        listContact = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        savingAtn = (Button) findViewById(R.id.savingAtn);


        attnList = new ArrayList<>();

        arecyclerView = (RecyclerView) findViewById(R.id.rvAttendance);
        arecyclerView.setLayoutManager(new LinearLayoutManager(this));

        attnAdapter = new AttendanceAdapter(attnList, this);
        arecyclerView.setAdapter(attnAdapter);

        String time = dateSet();
        if (time != null) {
            TextView textView = (TextView) findViewById(R.id.dateTime2);
            textView.setText(time);
            textView.setTextSize(20);

            semesterPick();

        }

        savingAtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingRecords();
            }
        });

    }



    private void semesterPick() {
        aprogressDialog = new ProgressDialog(this);
        aprogressDialog.setTitle("Loading");
        aprogressDialog.setCancelable(false);
        aprogressDialog.setMessage("Getting Student Data...");
        aprogressDialog.setIndeterminate(true);
        aprogressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GETSEMESTERDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        aprogressDialog.dismiss();
                        updateUI(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        aprogressDialog.dismiss();
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

            myspinner.setAdapter(new ArrayAdapter<>(AttendanceSheet.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
            myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, arrayList.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {
                        String semesterName = myspinner.getSelectedItem().toString();

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
                        aprogressDialog.dismiss();
                        populateCourseSpinner(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        aprogressDialog.dismiss();
                        Log.e("Error response", error.toString());
                    }
                });

        requestQueue.add(stringRequest);
    }

    private void populateCourseSpinner(String response) {
        try {
            JSONObject cs = new JSONObject(response);
            JSONArray jsonArray = cs.getJSONArray("courseIds");

            listCourseCode = new ArrayList<>();
            listCourseCode.add("Select Course Code");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject csId = jsonArray.getJSONObject(i);
                String csCode = csId.getString("course_id");
                listCourseCode.add(csCode);
            }
            Log.e("Response", "" + arrayList.size());

            myspinner2.setAdapter(new ArrayAdapter<>(AttendanceSheet.this, android.R.layout.simple_spinner_dropdown_item, listCourseCode));
            myspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, listCourseCode.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {
                        //Toast.makeText(Attendance.this, listCourseCode.get(position), Toast.LENGTH_LONG).show();
                        //String courseid = myspinner2.getSelectedItem().toString();
                        //createAttendanceSheet(myspinner2.getSelectedItem().toString());
                        Log.e("Spinner 2", listCourseCode.get(position));

                        createAttendanceSheet(listCourseCode.get(position));

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


    private void createAttendanceSheet(final String s) {
        aprogressDialog = new ProgressDialog(this);
        aprogressDialog.setTitle("Loading");
        aprogressDialog.setCancelable(false);
        aprogressDialog.setMessage("Getting Attendance Data...");
        aprogressDialog.setIndeterminate(true);
        aprogressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GETATTENDANCERECORDS + "?cid=" + s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        aprogressDialog.dismiss();
                        populateSheet(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        aprogressDialog.dismiss();
                        Log.e("Error response", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("cid", s);
                //params.put("domain", "http://itsalif.info");

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void populateSheet(String response) {
        try {
            JSONObject atten = new JSONObject(response);
            JSONArray jsonArray = atten.getJSONArray("attendanceSheet");

            attnList = new ArrayList<>();
            AttendanceModel atnM1 = new AttendanceModel("ID", "Name", "Attendance");
            attnList.add(atnM1);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject student = jsonArray.getJSONObject(i);
                String std_id = student.getString("student_id");
                String std_name = student.getString("student_name");
                String atn_state = student.getString("attendance_state");

                if (atn_state.equalsIgnoreCase("P")) {
                    present++;
                }
                AttendanceModel aM = new AttendanceModel(std_id, std_name, atn_state);
                attnList.add(aM);
            }
            Log.e("Response", "" + attnList.size());
            //aList = attnList;


            atn = (TextView) findViewById(R.id.atn_state);

            arecyclerView.setLayoutManager(new LinearLayoutManager(this));
            attnAdapter = new AttendanceAdapter(attnList, this);
            arecyclerView.setAdapter(attnAdapter);
            attnAdapter.notifyDataSetChanged();

            Spanned s;

            //String t = 1 + 2 + "" + 2 + 3;

            String d = "Total: <b>" + (attnList.size() - 1) + "</b>  Present: <b>" + present + "</b> </t>  " + "  Absent: <b>" + (attnList.size() - present - 1) + "</b>";

            if (Build.VERSION.SDK_INT >= 24) {
                s = Html.fromHtml(d, Html.FROM_HTML_MODE_LEGACY);
            } else {
                s = Html.fromHtml(d);
            }

            atn.setText(s);
            atn.setTextSize(18);
            atn.setPadding(15, 0, 5, 0);

        } catch (JSONException e) {
            //e.printStackTrace();

        }

    }

    TextView atn;
    int present = 0;

    static ProgressDialog p1;

    private void savingRecords() {

        int size = attnList.size();

        if (size > 1) {
            p1 = new ProgressDialog(this);
            p1.setTitle("Updating");
            p1.setMessage("Sending data to server...");
            p1.setIndeterminate(true);
            p1.show();
            count = 0;
            for (int m = 1; m < size; m++) {
                sendStudentDataToServer(attnList.get(m), (m == size - 1));
            }


        } else {
            Toast.makeText(this, "No Student Data!", Toast.LENGTH_SHORT).show();
        }
    }


    int count;

    void sendStudentDataToServer(final AttendanceModel model, final boolean flag) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SAVEATTENDANCERECORDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response Attendance:", response);

                        /*try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        try{
                            if (flag) {
                                p1.dismiss();
                                int total = attnList.size() - 1;
                                Toast.makeText(getApplicationContext(), "Complete\n" +
                                        "Total: " + total + "\nSuccess: " + (total - count) +
                                        "\nFailed: " + count, Toast.LENGTH_LONG).show();

                                getContact(myspinner.getSelectedItem().toString());


                            }

                        }catch (Exception e){
                            Log.e("Error before contact:", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (flag) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            count++;
                            p1.dismiss();
                        }

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                attnList.size();

                Map<String, String> params = new HashMap<>();

                params.put("student_id", model.getStudentId());
                params.put("student_name", model.getStudents_name());
                params.put("attendance_state", model.getAttendanceState());
                params.put("attendance_date", dateSet());
                params.put("course_id", myspinner2.getSelectedItem().toString());
                params.put("semester_id", myspinner.getSelectedItem().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);

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



            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

            builder.setTitle("Sending SMS!");
            builder.setMessage("Do you want to report to the guardians of the absent students???");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Sending sms through button click...
                    try{
                        String message = "Your son is absent today!";
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

                    }catch (Exception c){
                        Log.e("SMS Error: ", c.getMessage());
                    }

                }
            });

            builder.setNegativeButton("No", null);

            builder.create();
            builder.show();

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

    @Override
    public void clicked(String s, int position) {
        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


}
