package com.example.sazzad.cas;


public class AttendanceModel {
    String currentDate, semesterId, courseCode, studentId, attendanceState, studentsName;

    /*public AttendanceModel(String currentDate, String semesterId, String courseCode, String studentId, String attendanceState) {
        this.currentDate = currentDate;
        this.semesterId = semesterId;
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.attendanceState = attendanceState;
    }*/

    public AttendanceModel(String studentId, String studentsName, String attendanceState) {
        this.studentId = studentId;
        this.studentsName = studentsName;
        this.attendanceState = attendanceState;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudents_name(){
        return studentsName;
    }

    public void setStudentsName(String studentsName){
        this.studentsName = studentsName;
    }

    public String getAttendanceState() {
        return attendanceState;
    }

    public void setAttendanceState(String attendanceState) {
        this.attendanceState = attendanceState;
    }
}
