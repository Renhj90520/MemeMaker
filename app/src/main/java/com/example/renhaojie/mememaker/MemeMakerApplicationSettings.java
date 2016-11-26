package com.example.renhaojie.mememaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.renhaojie.mememaker.utils.StorageType;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class MemeMakerApplicationSettings {
    SharedPreferences mSharedPreferences;

    public MemeMakerApplicationSettings(Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getmSharedPreferences() {
        return mSharedPreferences.getString("Storage", StorageType.INTERNAL);
    }

    public void setmSharedPreferences(String storageType) {
        this.mSharedPreferences.edit().putString("Storage", storageType).apply();
    }
}
