package com.example.sazzad.cas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sazzad on 7/9/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder>{
    private List<UserModel> userList;
    UserListener userClick;

    public UserAdapter(List<UserModel> list, UserListener userClick){
        this.userList = list;
        this.userClick = userClick;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);

        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final UserModel userModel = userList.get(position);
        holder.setAdminName(userModel.getAdminName());
        holder.setAdminEmail(userModel.getAdminEmail());
        holder.setAdminContact(userModel.getAdminContact());

        if(position == 0){
            holder.username.setTextSize(14);
            holder.useremail.setTextSize(14);
            holder.usercontact.setTextSize(14);
        }
        else if(position != 0){
            holder.layoutUsers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userClick.clicked(userList.get(position).getAdminName(), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView username, useremail, usercontact;
        LinearLayout layoutUsers;

        public MyHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.user_name);
            useremail = (TextView) itemView.findViewById(R.id.user_email);
            usercontact = (TextView) itemView.findViewById(R.id.user_contact);

            layoutUsers = (LinearLayout) itemView.findViewById(R.id.layoutUsers);
        }

        String getAdminName() {
            return username.getText().toString();
        }

        void setAdminName(String s) {
            username.setText(s);
        }

        String getAdminEmail() {
            return useremail.getText().toString();
        }

        void setAdminEmail(String s) {
            useremail.setText(s);
        }

        String getAdminContact(){
            return usercontact.getText().toString();
        }

        void setAdminContact(String s){
            usercontact.setText(s);
        }

    }
}
