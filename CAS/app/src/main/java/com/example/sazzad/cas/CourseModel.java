package com.example.sazzad.cas;

/**
 * Created by Sazzad on 7/9/2017.
 */

public class CourseModel {
    private String course_code, course_title, total_class_no;

    public CourseModel(String course_code, String course_title, String total_class_no) {
        this.course_code = course_code;
        this.course_title = course_title;
        this.total_class_no = total_class_no;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getTotal_class_no() {
        return total_class_no;
    }

    public void setTotal_class_no(String total_class_no) {
        this.total_class_no = total_class_no;
    }
}
