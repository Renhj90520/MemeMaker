package com.example.renhaojie.mememaker.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class ImageGridItem {
    public ImageGridItem(Bitmap image, String fileName, String fullPath) {
        this.image = image;
        this.fileName = fileName;
        this.fullPath = fullPath;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    private Bitmap image;
    private String fileName;
    private String fullPath;


}
