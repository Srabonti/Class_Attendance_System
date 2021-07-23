package com.example.sazzad.cas;

/**
 * Created by Sazzad on 7/2/2017.
 */

public class Constants {
    private static final String ROOT_URL = "http://192.168.43.79/Attendance/v1/";

    public static final String URL_REGISTER = ROOT_URL + "registerUser.php";
    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_SAVINGSEMESTERDATA = ROOT_URL + "insertingSemester.php";
    public static final String URL_SAVINGCOURSEDATA = ROOT_URL + "insertingCourse.php";
    public static final String URL_SAVINGSTUDENTDATA = ROOT_URL + "insertingStudents.php";
    public static final String URL_GETSEMESTERDATA = ROOT_URL + "retrieveSemesterData.php";
    public static final String URL_GETUSERDATA = ROOT_URL + "retrieveUserdata.php";
    public static final String URL_GETCOURSEDATA = ROOT_URL + "retrieveCourseData.php";
    public static final String URL_GETSTUDENTDATA = ROOT_URL + "retrieveStudentData.php";
    public static final String URL_GETCOURSE = ROOT_URL + "getCourse.php";
    //public static final String URL_TAKEATTENDANCE = ROOT_URL + "takingAttendance.php";
    public static final String URL_DELETECOURSE = ROOT_URL + "deleteCourse.php";
    public static final String URL_DELETESTUDENTS = ROOT_URL + "deleteStudents.php";
    public static final String URL_DELETESEMESTERS = ROOT_URL + "deleteSemesters.php";
    public static final String URL_DELETEATTENDANCE = ROOT_URL + "deleteAttendance.php";
    public static final String URL_TATTENDANCE = ROOT_URL + "TAttendance.php";
    public static final String URL_GETATTENDANCERECORDS = ROOT_URL + "getAttendanceInfo.php";
    public static final String URL_SAVEATTENDANCERECORDS = ROOT_URL + "saveAttendance.php";
    public static final String URL_GETCONTACT = ROOT_URL + "getGuardianContact.php";
    public static final String URL_SPREADSHEET = ROOT_URL + "populateSpreadsheet.php";

}