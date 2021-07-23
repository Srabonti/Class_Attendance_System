package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import java.util.List;

public class UserList extends AppCompatActivity implements UserListener{
    private List<UserModel> userList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    UserAdapter userAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }


        userList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);

        createUserList();

    }

    private void createUserList() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting User Data...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GETUSERDATA,
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
            JSONObject userObject = new JSONObject(response);
            JSONArray jsonArray = userObject.getJSONArray("admins");

            //arrayList = new ArrayList<SemesterModel>();

            userList = new ArrayList<>();
            UserModel uM1 = new UserModel("Name", "Email", "Contact");
            userList.add(uM1);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                String adminName = user.getString("admin_name");
                String adminEmail = user.getString("admin_email");
                String adminContact = user.getString("admin_contact");

                //arrayList.add(new SemesterModel(semesterId, semesterName));
                UserModel uM = new UserModel(adminName, adminEmail, adminContact);
                userList.add(uM);
            }
            Log.e("Response", "" + userList.size());

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            userAdapter = new UserAdapter(userList, this);
            recyclerView.setAdapter(userAdapter);
            userAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
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
    public void clicked(String s, int position) {
        //Toast.makeText(this, s+" "+position, Toast.LENGTH_LONG).show();
    }
}
