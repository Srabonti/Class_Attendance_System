package com.example.sazzad.cas;

public class AttendanceSheetModel {
    String s_studentName, s_studentID, s_attendanceState, s_attendanceDate, s_courseCode, s_courseTitle;

    public AttendanceSheetModel(String s_studentName, String s_studentID, String s_attendanceState, String s_attendanceDate, String s_courseCode, String s_courseTitle) {
        this.s_studentName = s_studentName;
        this.s_studentID = s_studentID;
        this.s_attendanceState = s_attendanceState;
        this.s_attendanceDate = s_attendanceDate;
        this.s_courseCode = s_courseCode;
        this.s_courseTitle = s_courseTitle;
    }

    public String getS_studentName() {
        return s_studentName;
    }

    public void setS_studentName(String s_studentName) {
        this.s_studentName = s_studentName;
    }

    public String getS_studentID() {
        return s_studentID;
    }

    public void setS_studentID(String s_studentID) {
        this.s_studentID = s_studentID;
    }

    public String getS_attendanceState() {
        return s_attendanceState;
    }

    public void setS_attendanceState(String s_attendanceState) {
        this.s_attendanceState = s_attendanceState;
    }

    public String getS_attendanceDate() {
        return s_attendanceDate;
    }

    public void setS_attendanceDate(String s_attendanceDate) {
        this.s_attendanceDate = s_attendanceDate;
    }

    public String getS_courseCode() {
        return s_courseCode;
    }

    public void setS_courseCode(String s_courseCode) {
        this.s_courseCode = s_courseCode;
    }

    public String getS_courseTitle() {
        return s_courseTitle;
    }

    public void setS_courseTitle(String s_courseTitle) {
        this.s_courseTitle = s_courseTitle;
    }
}
