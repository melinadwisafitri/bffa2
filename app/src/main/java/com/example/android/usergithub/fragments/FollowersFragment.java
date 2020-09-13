package com.example.android.usergithub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.usergithub.R;
import com.example.android.usergithub.adapter.FollowAdapter;
import com.example.android.usergithub.entity.Users;

import java.util.ArrayList;

public class FollowersFragment extends Fragment {

    public FollowersFragment() {

    }

    public static final String SECTION_NUMBER = "section_number";
    private ArrayList<Users> index = new ArrayList<>();

    public static FollowersFragment newInstance(ArrayList<Users> index) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SECTION_NUMBER, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        if (getArguments() != null){
            index = getArguments().getParcelableArrayList(SECTION_NUMBER);
        }
        RecyclerView rv_fragment = view.findViewById(R.id.rvfollow);
        rv_fragment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_fragment.setHasFixedSize(true);
        FollowAdapter adapter = new FollowAdapter();
        adapter.setData(index);
        adapter.notifyDataSetChanged();
        rv_fragment.setAdapter(adapter);
        return view;
    }
}