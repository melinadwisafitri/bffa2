package com.example.android.usergithub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.usergithub.adapter.SectionpagerAdapter;
import com.example.android.usergithub.api.ApiClient;
import com.example.android.usergithub.api.ApiInterface;
import com.example.android.usergithub.db.DbContract;
import com.example.android.usergithub.db.UserHelper;
import com.example.android.usergithub.entity.Users;
import com.example.android.usergithub.fragments.FollowersFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    TextView login, full_name, repository, company, location,gitst;
    ImageView imgAvatar, imgAvatar1;
    ImageButton btn_share;
    ProgressBar progressBar;
    FloatingActionButton btn_favorite;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton btnback;
    private SectionpagerAdapter adapter;
    private UserHelper userHelper;

    public static final String EXTRA_USER = "extra_user";
    private static final String TAG= DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getInit();

        final Users users =  getIntent().getParcelableExtra(EXTRA_USER);
        String username = null;
        if (users != null) {
            username = users.getLogin();
        }
        getDetail(username);

        adapter = new SectionpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        userHelper = UserHelper.getInstance(getApplicationContext());
        userHelper.open();

    }
    public void addFavorite(Users users){
        if (users != null){
            ContentValues values = new ContentValues();
            values.put(DbContract.UserColumn.USERNAME, users.getLogin());
            values.put(DbContract.UserColumn.AVATAR_URL, users.getAvatar_url());
            values.put(DbContract.UserColumn.HTML_URL, users.getHtml_url());
            long result = userHelper.insert(values);
            showResult(result);
        }
    }
    private void  showResult(Long result){
        if (result > 0){
            Toast.makeText(this, "Favorite berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Gagal ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }
    public void getDetail(final String userName){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Users> call = apiInterface.getDetail(userName);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()){
                    Users users = response.body();
                    itemDetail(users);
                    getFollowers(userName, users.getTotalfollowers() + " Followers");
                    getFollowing(userName, users.getTotalfollowing() + " Following");
                    Log.d(TAG, "onResponseSuccess");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.d(TAG, "On Failure");
            }
        });
    }
    public void getFollowers(String userNmae, final String title){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Users>> call = apiInterface.getFollowers(userNmae);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()){
                    ArrayList<Users> users = new ArrayList<Users>(response.body());
                    adapter.addFragment(FollowersFragment.newInstance(users), title);
                    showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }
    public void getFollowing(String userName, final String title){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Users>> call = apiInterface.getFollowing(userName);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(@NonNull Call<List<Users>> call, Response<List<Users>> response) {
                ArrayList<Users> users = new ArrayList<Users>(response.body());
                adapter.addFragment(FollowersFragment.newInstance(users), title);
                showLoading(false);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }
    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void getInit(){
        login = findViewById(R.id.tv_username);
        full_name = findViewById(R.id.tv_fullname);
        repository = findViewById(R.id.total_repository);
        company = findViewById(R.id.tv_company);
        location = findViewById(R.id.tv_location);
        gitst = findViewById(R.id.total_gists);
        imgAvatar = findViewById(R.id.avatar_detail);
        imgAvatar1 = findViewById(R.id.avatar_detail2);
        progressBar = findViewById(R.id.progressbar_detail);
        tabLayout = findViewById(R.id.tabslayout);
        viewPager = findViewById(R.id.viewPager);
        btnback = findViewById(R.id.btn_back_arrow);

        btn_favorite = findViewById(R.id.btn_favorite);
        btn_share = findViewById(R.id.btn_share);

        btn_share.setBackgroundResource(0);
        btnback.setBackgroundResource(0);

        btnback.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
    }
    private void itemDetail(Users users){
        String dlogin = users.getLogin();
        String fullnames = users.getName();
        String repost = users.getRepository();
        String companys = users.getCompany();
        String locations = users.getLocation();
        String gistss = users.getPublic_gits();
        String avatar = users.getAvatar_url();
        String avatar1 = users.getAvatar_url();

        login.setText(dlogin);
        full_name.setText(fullnames);
        repository.setText(repost);
        if (company !=null){
            company.setText(companys);
        }
        location.setText(locations);
        gitst.setText(gistss);
        Glide.with(this)
                .load(avatar)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(R.drawable.ic_baseline_close)
                .into(imgAvatar);
        Glide.with(this)
                .load(avatar1)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(R.drawable.ic_baseline_close)
                .into(imgAvatar1);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_back_arrow:
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
            break;
           case R.id.btn_favorite:
               Users users = getIntent().getParcelableExtra(EXTRA_USER);
               addFavorite(users);
               break;
           case R.id.btn_share:
               Users user = getIntent().getParcelableExtra(EXTRA_USER);
               Intent intent_btnshare = new Intent();
               intent_btnshare.setAction(Intent.ACTION_SEND);
               if (user != null) {
                   intent_btnshare.putExtra(Intent.EXTRA_TEXT, "Visit the following link for information : " + user.getHtml_url());
               }
               intent_btnshare.setType("text/plain");
               startActivity(intent_btnshare);
               break;
        }
    }
    public void onDestroy(){
        super.onDestroy();
        userHelper.close();
    }
}