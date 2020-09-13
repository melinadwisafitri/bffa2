package com.example.android.usergithub.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.usergithub.MainActivity;
import com.example.android.usergithub.api.ApiClient;
import com.example.android.usergithub.api.ApiInterface;
import com.example.android.usergithub.entity.SearchResponse;
import com.example.android.usergithub.entity.Users;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Users>> _listSearch = new MutableLiveData<>();
    public LiveData<ArrayList<Users>> listSearch = _listSearch;

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errormessage = _errorMessage;

    public void SearchUser(String query){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SearchResponse> call = apiInterface.getSearchResult(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, Response<SearchResponse> response) {
                try {
                    if (response.isSuccessful()){
                        ArrayList<Users> searchResponse = response.body().getItems();
                        _listSearch.postValue(searchResponse);
                    }

                }catch (Exception e){
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));
                _errorMessage.postValue("");
            }
        });
    }
}