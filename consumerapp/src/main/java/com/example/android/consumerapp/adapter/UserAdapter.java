package com.example.android.consumerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.consumerapp.R;
import com.example.android.consumerapp.entity.Users;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {
    private ArrayList<Users> list = new ArrayList<>();
    private ArrayList<Users> filterlist = list;
    private OnItemClickCallback onItemClickCallback;
    private OnLongClickCallback onlongClickCallback;;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }
    public void setOnLongClickCallback(OnLongClickCallback onlongClickCallback){
        this.onlongClickCallback = onlongClickCallback;
    }


    public void setData(ArrayList<Users> item){
        if (item.size()>0) {
            this.list.clear();}
            this.list.addAll(item);
            this.notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.list.remove(position);
        notifyItemRangeChanged(position, list.size());
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserAdapter.UserViewHolder holder, final int position) {
        final Users users = list.get(position);
        holder.tvLogin.setText(users.getLogin());
        holder.tvurl.setText(users.getHtml_url());
        Glide.with(holder.itemView.getContext())
                .load(users.getAvatar_url())
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(R.drawable.ic_baseline_close)
                .into(holder.imgAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(list.get(holder.getAdapterPosition()));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onlongClickCallback.onlongClicked(list.get(holder.getAdapterPosition()), position);
            return true;
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filteritem;
    }
    private Filter filteritem = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Users> filter = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filter.addAll(filterlist);
            }else {
                String filtered = constraint.toString().toLowerCase().trim();
                for (Users users : filterlist){
                    if (users.getLogin().toLowerCase().contains(filtered)){
                        filter.add(users);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filter;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterlist.clear();
            filterlist.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

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

    public interface OnItemClickCallback{
        void onItemClicked(Users callback);
    }
    public interface OnLongClickCallback{
        void onlongClicked(Users delete, int position);
    }
}

