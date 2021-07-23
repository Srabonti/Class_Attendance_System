package com.example.sazzad.cas;

public class SemesterModel {
    private String stringSemesterId, stringSemesterName;
    //RequestQueue requestQueue;


    public SemesterModel(String stringSemesterId, String stringSemesterName){
        this.stringSemesterId = stringSemesterId;
        this.stringSemesterName = stringSemesterName;
    }

    public void setStringSemesterId(String stringSemesterId) {
        this.stringSemesterId = stringSemesterId;
    }

    public void setStringSemesterName(String stringSemesterName) {
        this.stringSemesterName = stringSemesterName;
    }

    public String getStringSemesterId() {
        return stringSemesterId;
    }

    public String getStringSemesterName() {
        return stringSemesterName;
    }


    /*public static ArrayList<SemesterModel> createSemesterList(){
        ArrayList<SemesterModel> arrayList = new ArrayList<SemesterModel>();

        arrayList.add(new SemesterModel("1st", "First semester"));
        arrayList.add(new SemesterModel("2nd", "Second semester"));
        arrayList.add(new SemesterModel("3rd", "Third semester"));
        arrayList.add(new SemesterModel("4th", "Fourth semester"));
        arrayList.add(new SemesterModel("5th", "Fifth semester"));
        arrayList.add(new SemesterModel("6th", "Sixth semester"));
        arrayList.add(new SemesterModel("7th", "Seventh semester"));
        arrayList.add(new SemesterModel("8th", "Eighth semester"));

        return arrayList;
    }*/



    /*public ArrayList<SemesterModel> createSemesterList(){
        requestQueue = Volley.newRequestQueue(); 'this' cannot be given in the newRequestQueue() method as a parameter

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GETSEMESTERDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject semester = new JSONObject(response);
                            JSONArray jsonArray = semester.getJSONArray("semesters");

                            arrayList = new ArrayList<SemesterModel>();

                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject sem = jsonArray.getJSONObject(i);
                                String semesterId = sem.getString("semester_id");
                                String semesterName = sem.getString("semester_name");

                                arrayList.add(new SemesterModel(semesterId, semesterName));
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(stringRequest);

        return arrayList;
    }
*/



}
