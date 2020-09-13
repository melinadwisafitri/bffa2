package com.example.android.usergithub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.android.usergithub.R;
import com.example.android.usergithub.ReminderReceiver;

import java.net.URISyntaxException;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.reminder_preferences, rootKey);
        initRemind();
        changeLanguage();
    }
    private void initRemind(){
        final ReminderReceiver reminderReceiver = new ReminderReceiver();
        SwitchPreferenceCompat switchPreferenceCompat = findPreference(getString(R.string.reminder_pref));
        switchPreferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int notifId = ReminderReceiver.NOTIFICATION_ID;
                boolean value = (boolean) newValue;
                if (value){
                    reminderReceiver.setRepeatingAlarm(requireContext(), notifId);
                }else {
                    reminderReceiver.cancelAlarm(requireContext(), notifId);
                }
                return true;
            }
        });
    }
    private void changeLanguage() {
        Preference preference = findPreference(getString(R.string.language_pref));
        preference.setIntent(new Intent(Settings.ACTION_LOCALE_SETTINGS));
    }
}