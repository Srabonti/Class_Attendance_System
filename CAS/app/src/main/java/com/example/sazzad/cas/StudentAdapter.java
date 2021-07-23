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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>{
    private List<StudentModel> studentList;
    StudentClickListener studentClick;

    public StudentAdapter(List<StudentModel> studentList, StudentClickListener studentClick) {
        this.studentList = studentList;
        this.studentClick = studentClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final StudentModel stdModel = studentList.get(position);
        holder.setStudent_id(stdModel.getId());
        holder.setStudent_name(stdModel.getName());
        holder.setGuardian_contact(stdModel.getGuardianContact());

        if(position == 0){
            holder.student_id.setTextSize(16);
            holder.student_name.setTextSize(16);
            holder.guardian_contact.setTextSize(16);
        }
        else if(position != 0){
            holder.layoutStudents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    studentClick.clicked(studentList.get(position).getId(), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView student_id, student_name, guardian_contact;
        LinearLayout layoutStudents;

        public MyViewHolder(View itemView) {
            super(itemView);

            student_id = (TextView) itemView.findViewById(R.id.student_id);
            student_name = (TextView) itemView.findViewById(R.id.student_name);
            guardian_contact = (TextView) itemView.findViewById(R.id.guardian_phone);

            layoutStudents = (LinearLayout) itemView.findViewById(R.id.layoutStudents);

        }

        public String getStudent_id() {
            return student_id.getText().toString();
        }

        public void setStudent_id(String s) {
            student_id.setText(s);
        }

        public String getStudent_name() {
            return student_name.getText().toString();
        }

        public void setStudent_name(String s) {
            student_name.setText(s);
        }

        public String getGuardian_contact() {
            return guardian_contact.getText().toString();
        }

        public void setGuardian_contact(String s) {
            guardian_contact.setText(s);
        }
    }
}
