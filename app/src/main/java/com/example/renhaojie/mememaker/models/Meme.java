package com.example.renhaojie.mememaker.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class Meme implements Serializable {
    private int mId;
    private String mAssetLocation;
    private ArrayList<MemeAnnotation> mAnnotations;
    private String mName;

    public Meme(int mId, String mAssetLocation, ArrayList<MemeAnnotation> mAnnotations, String mName) {
        this.mId = mId;
        this.mAssetLocation = mAssetLocation;
        this.mAnnotations = mAnnotations;
        this.mName = mName;
    }

    public int getmId() {
        return mId;
    }

    public String getmAssetLocation() {
        return mAssetLocation;
    }

    public void setmAssetLocation(String mAssetLocation) {
        this.mAssetLocation = mAssetLocation;
    }

    public ArrayList<MemeAnnotation> getmAnnotations() {
        return mAnnotations;
    }

    public void setmAnnotations(ArrayList<MemeAnnotation> mAnnotations) {
        this.mAnnotations = mAnnotations;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Bitmap getBitmap() {
        File file = new File(mAssetLocation);
        if (!file.exists())
            Log.e("File is missing", mAssetLocation);
        return BitmapFactory.decodeFile(mAssetLocation);
    }
}
