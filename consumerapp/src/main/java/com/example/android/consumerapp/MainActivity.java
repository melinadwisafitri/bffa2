package com.example.android.consumerapp;

import android.app.SearchManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.consumerapp.adapter.UserAdapter;
import com.example.android.consumerapp.entity.Users;
import com.example.android.consumerapp.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import static com.example.android.consumerapp.db.DbContract.UserColumn.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadUserCallback {
    private UserAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Consumer App");
        }

        progressBar = findViewById(R.id.progressBar_favorite);

        RecyclerView recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Users callback) {
                Toast.makeText(MainActivity.this, callback.getLogin(), Toast.LENGTH_SHORT).show();
            }
        });

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);
        if (savedInstanceState == null){
            new loadUserAsync(this, this).execute();
        }
    }
    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Users> users) {
        progressBar.setVisibility(View.INVISIBLE);
        if (users.size() > 0) {
            adapter.setData(users);
        } else {
            adapter.setData(new ArrayList<Users>());
            Toast.makeText(this, "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
        }
    }

    private static final class loadUserAsync extends AsyncTask<Void, Void, ArrayList<Users>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUserCallback> weakCallback;

        public loadUserAsync(Context context, LoadUserCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Users> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorArrayList(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Users> users) {
            super.onPostExecute(users);
            weakCallback.get().postExecute(users);
        }
    }
    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            new loadUserAsync(context, (LoadUserCallback) context).execute();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null){
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return true;
    }
}
interface LoadUserCallback{
    void preExecute();
    void postExecute(ArrayList<Users> users);
}