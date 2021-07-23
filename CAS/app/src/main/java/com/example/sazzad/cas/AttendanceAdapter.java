package com.example.sazzad.cas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Sazzad on 7/12/2017.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder>{
    private List<AttendanceModel> attendanceList;
    AttendanceClickListener attendanceClickListener;

    public AttendanceAdapter(List<AttendanceModel> attendanceList, AttendanceClickListener attendanceClickListener) {
        this.attendanceList = attendanceList;
        this.attendanceClickListener = attendanceClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendancesheet,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AttendanceModel attnModel = attendanceList.get(position);
        holder.setStd_id(attnModel.getStudentId());
        holder.setStd_name(attnModel.getStudents_name());
        holder.setAttendanceState(attnModel.getAttendanceState());

        if(position == 0){
            holder.std_id.setTextSize(16);
            holder.std_name.setTextSize(16);
            holder.attendance.setTextSize(16);
        }
        else if(position != 0){
            holder.layoutAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    attendanceClickListener.clicked(attendanceList.get(position).getAttendanceState(), position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView std_id, std_name, attendance;
        LinearLayout layoutAttendance;

        public MyViewHolder(View itemView) {
            super(itemView);

            std_id = (TextView) itemView.findViewById(R.id.std_id);
            std_name = (TextView) itemView.findViewById(R.id.std_name);
            attendance = (TextView) itemView.findViewById(R.id.attendance);

            layoutAttendance = (LinearLayout) itemView.findViewById(R.id.layoutAttendance);

        }

        public String getStd_id() {
            return std_id.getText().toString();
        }

        public void setStd_id(String s) {
            std_id.setText(s);
        }

        public String getStd_name() {
            return std_name.getText().toString();
        }

        public void setStd_name(String s) {
            std_name.setText(s);
        }

        public String getAttendanceState() {
            return attendance.getText().toString();
        }

        public void setAttendanceState(String s) {
            attendance.setText(s);
        }
    }
}
