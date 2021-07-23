package com.example.sazzad.cas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sazzad on 7/10/2017.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    private List<CourseModel> courseList;
    CourseClickListener csClick;

    public CourseAdapter(List<CourseModel> courseList, CourseClickListener csClick) {
        this.courseList = courseList;
        this.csClick = csClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courses, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CourseModel csModel = courseList.get(position);
        holder.setCourse_code(csModel.getCourse_code());
        holder.setCourse_title(csModel.getCourse_title());
        holder.setTotal_class_no(csModel.getTotal_class_no());

        if(position == 0){
            holder.course_code.setTextSize(16);
            holder.course_title.setTextSize(16);
            holder.total_class_no.setTextSize(16);
        }
        else if(position != 0){
            holder.courseLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    csClick.clicked(courseList.get(position).getCourse_code(), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView course_code, course_title, total_class_no;
        LinearLayout courseLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            course_code = (TextView) itemView.findViewById(R.id.course_code);
            course_title = (TextView) itemView.findViewById(R.id.course_title);
            total_class_no = (TextView) itemView.findViewById(R.id.total_class);

            courseLayout = (LinearLayout) itemView.findViewById(R.id.layoutCourses);

        }

        public String getCourse_code() {
            return course_code.getText().toString();
        }

        public void setCourse_code(String s) {
            course_code.setText(s);
        }

        public String getTotal_class_no() {
            return total_class_no.getText().toString();
        }

        public void setTotal_class_no(String s) {
            total_class_no.setText(s);
        }

        public String getCourse_title() {
            return course_title.getText().toString();
        }

        public void setCourse_title(String s) {
            course_title.setText(s);
        }
    }
}
