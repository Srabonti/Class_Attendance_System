package com.example.sazzad.cas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FinalAttendanceRecord extends AppCompatActivity {
    ProgressDialog progressDialog;
    Spinner finspinner, finspinner2;
    RequestQueue finrequestQueue;
    List<String> finarrayList, finlistCourseCode;
    Toolbar toolbar;
    Button buttonCreateSheet, buttonOpenSheet;
    File ul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_attendance_record);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);

        }

        finspinner = (Spinner) findViewById(R.id.finspinner);
        finspinner2 = (Spinner) findViewById(R.id.finspinner2);

        finrequestQueue = Volley.newRequestQueue(this);
        buttonCreateSheet = (Button) findViewById(R.id.sheet);
        buttonOpenSheet =(Button) findViewById(R.id.openSheet);

        semesterPick();


        //List<String> id = null, names = null, dates = null, records = null;

        //generateSheetTest(id, names, dates, records);
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

            finarrayList = new ArrayList<>();
            finarrayList.add("Select Semester");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject student = jsonArray.getJSONObject(i);
                String semesterID = student.getString("semester_id");
                finarrayList.add(semesterID);
            }
            Log.e("Response", "" + finarrayList.size());

            finspinner.setAdapter(new ArrayAdapter<>(FinalAttendanceRecord.this, android.R.layout.simple_spinner_dropdown_item, finarrayList));
            finspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, arrayList.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {

                        String semesterName = finspinner.getSelectedItem().toString();

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
                        //progressDialog.dismiss();
                        populateCourseSpinner(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
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

            finlistCourseCode = new ArrayList<>();
            finlistCourseCode.add("Select Course Code");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject csId = jsonArray.getJSONObject(i);
                String csCode = csId.getString("course_id");
                finlistCourseCode.add(csCode);
            }
            Log.e("Response", "" + finlistCourseCode.size());

            finspinner2.setAdapter(new ArrayAdapter<>(FinalAttendanceRecord.this, android.R.layout.simple_spinner_dropdown_item, finlistCourseCode));
            finspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(Attendance.this, listCourseCode.get(position), Toast.LENGTH_LONG).show();
                    if (position != 0) {
                        //Toast.makeText(Attendance.this, listCourseCode.get(position), Toast.LENGTH_LONG).show();

                        buttonCreateSheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                downloadStudentInfo(finspinner.getSelectedItem().toString(), finspinner2.getSelectedItem().toString());

                                //generateSheet();
                            }
                        });

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


    private void downloadStudentInfo(final String semid, final String courseCode) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SPREADSHEET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Parsing Records: ", response);
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                //map.put("", "");
                map.put("semester_id", semid);
                map.put("course_id", courseCode);


                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    void parseData(String res) {
        try {
            JSONObject jsonObject = new JSONObject(res);

            JSONArray array = jsonObject.getJSONArray("spreadsheetData");

            int size = array.length();

            List<AttendanceSheetModel> mList = new ArrayList<>();
            List<String> dates = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject1 = array.getJSONObject(i);

                String id = jsonObject1.getString("student_id");
                String name = jsonObject1.getString("student_name");
                String state = jsonObject1.getString("attendance_state");
                String date = jsonObject1.getString("attendance_date");
                String course = jsonObject1.getString("course_id");
                String sem = jsonObject1.getString("semester_id");
                String ctitle = jsonObject1.getString("course_title");
                AttendanceSheetModel asm = new AttendanceSheetModel(name, id, state, date, course, ctitle);
                mList.add(asm);

                int t = dates.size();
                if (t == 0) {
                    dates.add(date);
                } else if (!dates.get(t - 1).equalsIgnoreCase(date)) {
                    dates.add(date);
                }
            }

            //generateSheet(mList, dates);

            filter(mList, dates);
        } catch (Exception e) {
            Log.e("Exception Parsing", e.toString());
        }

    }

    void filter(List<AttendanceSheetModel> mList, List<String> dates) {

        try{
            List<String> records = new ArrayList<>();
            List<String> id = new ArrayList<>();
            List<String> name = new ArrayList<>();
            //List<String> name = new ArrayList<>();

            int size = mList.size();
            List<AttendanceSheetModel> temp = new ArrayList<>();

            temp.addAll(mList);


            for (int i = 0; i < size; i++) {

                int count = 0;
                String state = "";
                for (int j = 0; j < temp.size(); j++) {

                    if (mList.get(i).getS_studentID().equalsIgnoreCase(temp.get(j).getS_studentID())) {

                        state += temp.get(j).getS_attendanceState() + ",";

                        temp.remove(j);
                        count++;
                        j = 0;
                    }

                }

                if (count > 0) {
                    id.add(mList.get(i).getS_studentID());
                    records.add(state);
                    name.add(mList.get(i).getS_studentName());
                }
            }

            generateSheet(id, name, dates, records);

        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
        }

    }

    private void generateSheetTest(List<String> id, List<String> names, List<String> dates, List<String> states) {

        //dates.toArray()

        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet spreadsheet = workbook.createSheet("    Attendance Sheet    ");

            HSSFRow row;
            //This data needs to be written (Object[])
            Map<String, Object[]> empinfo =
                    new TreeMap<String, Object[]>();
            empinfo.put("1", new Object[]{
                    "EMP ID", "EMP NAME", "DESIGNATION"});
            empinfo.put("2", new Object[]{
                    "tp01", "Gopal", "Technical Manager"});
            empinfo.put("3", new Object[]{
                    "tp02", "Manisha", "Proof Reader"});
            empinfo.put("4", new Object[]{
                    "tp03", "Masthan", "Technical Writer"});
            empinfo.put("5", new Object[]{
                    "tp04", "Satish", "Technical Writer"});
            empinfo.put("6", new Object[]{
                    "tp05", "Krishna", "Technical Writer"});
            //Iterate over data and write to sheet
            Set<String> keyid = empinfo.keySet();
            int rowid = 0;
            for (String key : keyid) {
                row = spreadsheet.createRow(rowid++);
                Object[] objectArr = empinfo.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
            }


            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                Toast.makeText(this, "Storage Not Available!", Toast.LENGTH_LONG).show();

            } else {
                myExternalFile = new File(getExternalFilesDir(filepath), filename);
                //Write the workbook in file system

                FileOutputStream fos = new FileOutputStream(myExternalFile);

            /*FileOutputStream out = new FileOutputStream(
                    new File("E:/xampp/htdocs/Attendance/Spreadsheet.xls"));*/
                workbook.write(fos);
                fos.close();
                Toast.makeText(this, "Spreadsheet is created successfully!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.e("Spreadsheet error: ", e.toString());
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private String filename;
    private String filepath = "MyFileStorage";
    File myExternalFile;


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


    //////

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    /////////////// test

    private void generateSheet(List<String> id, List<String> names, List<String> dates, List<String> states) {

        //dates.toArray()


        /*id = new ArrayList<>();
        id.add("13701023");
        id.add("13701015");
        id.add("13701060");

        names = new ArrayList<>();

        names.add("Shazzad");
        names.add("Mikel");
        names.add("Kamal");

        dates = new ArrayList<>();

        dates.add("23/07/2017");
        dates.add("24/07/2017");
        dates.add("25/07/2017");
        dates.add("26/07/2017");

        states = new ArrayList<>();

        states.add("P,P,P,P,");
        states.add("A,P,A,A,");
        states.add("A,A,P,A,");
*/

        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet spreadsheet = workbook.createSheet("    Attendance Sheet    ");

            HSSFRow row;
            //This data needs to be written (Object[])
            Map<String, Object[]> atndInfo =
                    new TreeMap<String, Object[]>();


            ///////////////header
            Object obj[] = new Object[dates.size() + 2];

            obj[0] = "STUDENT ID";
            obj[1] = "STUDENT NAME";

            for (int m = 2; m < dates.size() + 2; m++) {
                obj[m] = dates.get(m - 2);
            }
            atndInfo.put("1", obj);
            /////////header

            for (int n = 0; n < id.size(); n++) {
                Object obj1[] = new Object[dates.size() + 2];

                obj1[0] = id.get(n);
                obj1[1] = names.get(n);

                String[] Astates = states.get(n).split(",");

                for (int m = 2; m < dates.size() + 2; m++) {
                    obj1[m] = Astates[m - 2];
                }

                atndInfo.put("" + (n + 2), obj1);
            }

            /*empinfo.put("2", new Object[]{
                    id.get(0), names.get(0), "Technical Manager"});
*/

            //Iterate over data and write to sheet
            Set<String> keyid = atndInfo.keySet();
            int rowid = 0;
            for (String key : keyid) {
                row = spreadsheet.createRow(rowid++);
                Object[] objectArr = atndInfo.get(key);
                int cellid = 0;
                for (Object obj2 : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj2);
                    /*int columnIndex = cell.getColumnIndex();
                    spreadsheet.autoSizeColumn(columnIndex);*/
                }
            }


            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                Toast.makeText(this, "Storage Not Available!", Toast.LENGTH_LONG).show();

            } else {
                String f = finspinner2.getSelectedItem().toString().trim();
                //Removing space from the middle of the string
                filename = f.replaceAll("\\s", "")+".xls";
                myExternalFile = new File(getExternalFilesDir(filepath), filename);
                //Write the workbook in file system

                FileOutputStream fos = new FileOutputStream(myExternalFile);

            /*FileOutputStream out = new FileOutputStream(
                    new File("E:/xampp/htdocs/Attendance/Spreadsheet.xls"));*/
                workbook.write(fos);
                fos.close();
                Toast.makeText(this, "Spreadsheet is created successfully!", Toast.LENGTH_LONG).show();

                //ul = myExternalFile;

                buttonOpenSheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFile(getApplicationContext(), myExternalFile);
                    }
                });


            }

        } catch (Exception e) {
            Log.e("Spreadsheet error: ", e.toString());
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private static void openFile(Context context, File url){
        try{
            // Create URI
            File file = url;
            Uri uri = Uri.fromFile(file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            }
            else{
                Log.e("File Type Error: ", "Unknown file types");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }catch (Exception c){
            Log.e("Opening File: ", c.toString());
        }
    }

}
