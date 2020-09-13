package com.example.android.usergithub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.usergithub.DetailActivity;
import com.example.android.usergithub.R;
import com.example.android.usergithub.SettingsActivity;
import com.example.android.usergithub.entity.Users;
import com.example.android.usergithub.adapter.UserAdapter;
import com.example.android.usergithub.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{

    private HomeViewModel homeViewModel;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView = root.findViewById(R.id.searchview);
        progressBar = root.findViewById(R.id.progressBar);
        final RecyclerView rv_search = root.findViewById(R.id.recyclerview_home);

        searchView.setOnQueryTextListener(this);

        rv_search.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_search.setHasFixedSize(true);

        final UserAdapter adapter = new UserAdapter();
        adapter.notifyDataSetChanged();
        rv_search.setAdapter(adapter);
        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        homeViewModel.listSearch.observe(getViewLifecycleOwner(), new Observer<ArrayList<Users>>() {
            @Override
            public void onChanged(ArrayList<Users> users) {
                showLoading(false);
                adapter.setData(users);
            }
        });
        homeViewModel.errormessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading(false);
                Toast.makeText(getContext(), "Error to Load Data", Toast.LENGTH_LONG).show();
            }
        });

        adapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Users callback) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_USER, callback);
                startActivity(intent);
            }
        });

        return root;
    }
    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showLoading(true);
        homeViewModel.SearchUser(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}