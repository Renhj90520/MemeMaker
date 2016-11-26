package com.example.renhaojie.mememaker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class MemeAnnotation implements Serializable {
    private int mId = -1;
    private String mColor;
    private String mTitle;
    private int mLocationX;
    private int mLocationY;

    public MemeAnnotation(int mId, String mColor, String mTitle, int mLocationX, int mLocationY) {
        this.mId = mId;
        this.mColor = mColor;
        this.mTitle = mTitle;
        this.mLocationX = mLocationX;
        this.mLocationY = mLocationY;
    }

    public MemeAnnotation() {
    }

    public int getmId() {
        return mId;
    }

    public boolean hasBeenSaved() {
        return getmId() != -1;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmLocationX() {
        return mLocationX;
    }

    public void setmLocationX(int mLocationX) {
        this.mLocationX = mLocationX;
    }

    public int getmLocationY() {
        return mLocationY;
    }

    public void setmLocationY(int mLocationY) {
        this.mLocationY = mLocationY;
    }
}
