package com.example.android.usergithub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.usergithub.fragments.SettingFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}