package com.example.sazzad.cas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener{
    private Button csEntryButton, stdEntryButton, smstrEntryButton, csListButton, stdListButton, smstrListButton, userListButton, atndnceButton, atndceListButton, finalAtndceListButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        csEntryButton = (Button) findViewById(R.id.courseEntry);
        stdEntryButton = (Button) findViewById(R.id.studentEntry);
        smstrEntryButton = (Button) findViewById(R.id.smstrEntry);
        csListButton = (Button) findViewById(R.id.csList);
        stdListButton = (Button) findViewById(R.id.stdList);
        smstrListButton = (Button) findViewById(R.id.smstrList);
        userListButton = (Button) findViewById(R.id.userList);
        atndnceButton = (Button) findViewById(R.id.atndnce);
        atndceListButton = (Button) findViewById(R.id.atndnceSheet);
        finalAtndceListButton = (Button) findViewById(R.id.fnlAttendanceChart);

        csEntryButton.setOnClickListener(this);
        stdEntryButton.setOnClickListener(this);
        smstrEntryButton.setOnClickListener(this);
        csListButton.setOnClickListener(this);
        stdListButton.setOnClickListener(this);
        smstrListButton.setOnClickListener(this);
        userListButton.setOnClickListener(this);
        atndnceButton.setOnClickListener(this);
        atndceListButton.setOnClickListener(this);
        finalAtndceListButton.setOnClickListener(this);
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
        if(v == csEntryButton){
            startActivity(new Intent(this, AddingCourses.class));
        }
        if(v == stdEntryButton){
            startActivity(new Intent(this, AddingStudents.class));
        }
        if(v == smstrEntryButton){
            startActivity(new Intent(this, AddingSemester.class));
        }
        if(v == csListButton){
            startActivity(new Intent(this, CourseList.class));
        }
        if(v == stdListButton){
            startActivity(new Intent(this, StudentList.class));
        }
        if(v == smstrListButton){
            startActivity(new Intent(this, SemesterList.class));
        }
        if(v == userListButton){
            startActivity(new Intent(this, UserList.class));
        }
        if(v == atndnceButton){
            startActivity(new Intent(this, Attendance.class));
        }
        if(v == atndceListButton){
            startActivity(new Intent(this, AttendanceSheet.class));
        }
        if(v == finalAtndceListButton){
            startActivity(new Intent(this, FinalAttendanceRecord.class));
        }

    }
}
