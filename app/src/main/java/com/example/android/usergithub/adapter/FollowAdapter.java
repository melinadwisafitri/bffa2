package com.example.android.usergithub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.usergithub.R;
import com.example.android.usergithub.entity.Users;

import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.UserViewHolder> {
    private ArrayList<Users> list = new ArrayList<>();

    public void setData(ArrayList<Users> item){
        list.clear();
        list.addAll(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowAdapter.UserViewHolder holder, int position) {
        final Users users = list.get(position);
        holder.tvLogin.setText(users.getLogin());
        holder.tvurl.setText(users.getHtml_url());
        Glide.with(holder.itemView.getContext())
                .load(users.getAvatar_url())
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(R.drawable.ic_baseline_close)
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvLogin, tvurl;
        ImageView imgAvatar;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLogin = itemView.findViewById(R.id.tv_login_row);
            tvurl = itemView.findViewById(R.id.tv_htmlurl_row);
            imgAvatar = itemView.findViewById(R.id.avatar_row);
        }
    }
}

