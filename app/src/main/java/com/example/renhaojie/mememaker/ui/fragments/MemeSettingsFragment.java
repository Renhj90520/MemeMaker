package com.example.renhaojie.mememaker.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.renhaojie.mememaker.R;

/**
 * Created by Ren Haojie on 2016/11/25.
 */

public class MemeSettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
