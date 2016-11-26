package com.example.renhaojie.mememaker;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.renhaojie.mememaker.utils.FileUtilities;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class MemeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FileUtilities.saveAssetImage(this, "dogmess.jpg");
        FileUtilities.saveAssetImage(this, "excitedcat.jpg");
        FileUtilities.saveAssetImage(this, "guiltypup.jpg");

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
