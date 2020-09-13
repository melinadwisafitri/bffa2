package com.example.android.usergithub.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.usergithub.DetailActivity;
import com.example.android.usergithub.R;
import com.example.android.usergithub.adapter.UserAdapter;
import com.example.android.usergithub.db.UserHelper;
import com.example.android.usergithub.entity.Users;
import com.example.android.usergithub.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.android.usergithub.db.DbContract.UserColumn.CONTENT_URI;

public class FavoriteFragment extends Fragment implements LoadUserCallback{

    private UserHelper userHelper;
    private UserAdapter adapter;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        progressBar = root.findViewById(R.id.progressBar_favorite);
        RecyclerView rv_favorite = root.findViewById(R.id.rv_favorite);
        rv_favorite.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_favorite.setHasFixedSize(true);

        adapter = new UserAdapter();
        rv_favorite.setAdapter(adapter);

        userHelper = UserHelper.getInstance(requireActivity().getApplicationContext());
        userHelper.open();

        new loadUserAsync(userHelper, this).execute();
        initRecycler();

        return root;
    }
    private void initRecycler(){
        adapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Users callback) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_USER, callback);
                startActivity(intent);
            }
        });
        adapter.setOnLongClickCallback(new UserAdapter.OnLongClickCallback() {
            @Override
            public void onlongClicked(Users delete, int position) {
                removeUsers(delete, position);
            }
        });

    }
    public void removeUsers(Users users, int position){
        long result = userHelper.deleteById(String.valueOf(users.getId()));
        if (result > 0){
            adapter.removeItem(position);
            Toast.makeText(getContext(), "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void preExecute() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Users> users) {
        progressBar.setVisibility(View.INVISIBLE);
        if (users.size()>0){
            adapter.setData(users);
        }else {
            adapter.setData(new ArrayList<Users>());
            Toast.makeText(getContext(), "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
        }
    }
    private static final class loadUserAsync extends AsyncTask<Void, Void, ArrayList<Users> >{

        private final WeakReference<UserHelper> weakUserHlper;
        private final WeakReference<LoadUserCallback> weakCallback;

        public loadUserAsync(UserHelper userHelper, LoadUserCallback callback){
            //weakContext = new WeakReference<>(context);
            weakUserHlper = new WeakReference<>(userHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Users> doInBackground(Void... voids) {
            Cursor cursor = weakUserHlper.get().queryAll();
            return MappingHelper.mapCursorArrayList(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Users> users){
            super.onPostExecute(users);
            weakCallback.get().postExecute(users);
        }
    }

    /*@Override
    public void onDestroy(){
        super.onDestroy();
        userHelper.close();
    }*/
}
interface LoadUserCallback{
    void preExecute();
    void postExecute(ArrayList<Users> users);
}