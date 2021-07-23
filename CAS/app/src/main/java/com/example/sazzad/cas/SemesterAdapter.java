package com.example.sazzad.cas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.MyViewHolder> {

    private List<SemesterModel> mSemesters;
    SemesterClickListener smClick;

    public SemesterAdapter(List<SemesterModel> list, SemesterClickListener smClick){
        this.mSemesters = list;
        this.smClick = smClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_semesters, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SemesterModel smModel = mSemesters.get(position);
        holder.setId(smModel.getStringSemesterId());
        holder.setName(smModel.getStringSemesterName());

        if(position == 0){
            holder.id.setTextSize(20);
            holder.name.setTextSize(20);
        }
        else if(position != 0){
            holder.layoutSemester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    smClick.clicked(mSemesters.get(position).getStringSemesterId(), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSemesters.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, name;
        LinearLayout layoutSemester;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.semesterid);
            name = (TextView) itemView.findViewById(R.id.semestername);
            layoutSemester = (LinearLayout) itemView.findViewById(R.id.layoutSemester);
        }

        String getId() {
            return id.getText().toString();
        }

        void setId(String s) {
            id.setText(s);
        }

        String getName() {
            return name.getText().toString();
        }

        void setName(String s) {
            name.setText(s);
        }
    }

}
