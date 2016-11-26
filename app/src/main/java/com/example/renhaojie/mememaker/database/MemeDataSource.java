package com.example.renhaojie.mememaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.renhaojie.mememaker.models.Meme;
import com.example.renhaojie.mememaker.models.MemeAnnotation;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ren Haojie on 2016/11/25.
 */

public class MemeDataSource {

    Context mContext;
    private MemeSqliteHelper mMemeSqliteHelper;

    public MemeDataSource(Context mContext) {
        this.mContext = mContext;
        mMemeSqliteHelper = new MemeSqliteHelper(mContext);
        SQLiteDatabase database = mMemeSqliteHelper.getReadableDatabase();
        database.close();
    }

    private SQLiteDatabase open() {
        return mMemeSqliteHelper.getReadableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    public void create(Meme meme) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues memeValues = new ContentValues();
        memeValues.put(MemeSqliteHelper.COLUMN_MEME_NAME, meme.getmName());
        memeValues.put(MemeSqliteHelper.COLUMN_MEME_ASSET, meme.getmAssetLocation());
        memeValues.put(MemeSqliteHelper.COLUMN_MEME_CREATE_DATE, new Date().getTime());
        long memeID = database.insert(MemeSqliteHelper.MEMES_TABLE, null, memeValues);
        for (MemeAnnotation annotation : meme.getmAnnotations()) {
            ContentValues annotationValues = new ContentValues();
            annotationValues.put(MemeSqliteHelper.COLUMN_ANNOTATION_COLOR, annotation.getmColor());
            annotationValues.put(MemeSqliteHelper.COLUMN_ANNOTATION_TITLE, annotation.getmTitle());
            annotationValues.put(MemeSqliteHelper.COLUMN_ANNOTATION_X, annotation.getmLocationX());
            annotationValues.put(MemeSqliteHelper.COLUMN_ANNOTATION_Y, annotation.getmLocationY());
            annotationValues.put(MemeSqliteHelper.COLUMN_FOREIGN_KEY_MEME, memeID);

            database.insert(MemeSqliteHelper.ANNOTATIONS_TABLE, null, annotationValues);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Meme> readMemes() {
        SQLiteDatabase database = open();

        Cursor cursor = database.query(MemeSqliteHelper.MEMES_TABLE, new String[]{MemeSqliteHelper.COLUMN_MEME_NAME, BaseColumns._ID, MemeSqliteHelper.COLUMN_MEME_ASSET}
                , null//selection
                , null//selectionArgs
                , null//group by
                , null//having
                , null//order by
        );

        ArrayList<Meme> memes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Meme meme = new Meme(getIntFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, MemeSqliteHelper.COLUMN_MEME_ASSET),
                        null,
                        getStringFromColumnName(cursor, MemeSqliteHelper.COLUMN_MEME_NAME));
                memes.add(meme);
            }
            while (cursor.moveToNext());

        }
        cursor.close();
        close(database);
        return memes;
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public void update(Meme meme) {
        SQLiteDatabase database = open();
        database.beginTransaction();
        ContentValues updateMemeValue = new ContentValues();
        updateMemeValue.put(MemeSqliteHelper.COLUMN_MEME_NAME, meme.getmName());
        database.update(MemeSqliteHelper.MEMES_TABLE, updateMemeValue, String.format("%s=%d", BaseColumns._ID, meme.getmId()), null);

        for (MemeAnnotation annotation : meme.getmAnnotations()) {
            ContentValues updateAnnotations = new ContentValues();
            updateAnnotations.put(MemeSqliteHelper.COLUMN_ANNOTATION_TITLE, annotation.getmTitle());
            updateAnnotations.put(MemeSqliteHelper.COLUMN_ANNOTATION_X, annotation.getmLocationX());
            updateAnnotations.put(MemeSqliteHelper.COLUMN_ANNOTATION_Y, annotation.getmLocationY());
            updateAnnotations.put(MemeSqliteHelper.COLUMN_FOREIGN_KEY_MEME, meme.getmId());
            updateAnnotations.put(MemeSqliteHelper.COLUMN_ANNOTATION_COLOR, annotation.getmColor());

            if (annotation.hasBeenSaved()) {
                database.update(MemeSqliteHelper.ANNOTATIONS_TABLE, updateAnnotations, String.format("%s=%d", BaseColumns._ID, annotation.getmId()), null);
            } else {
                database.insert(MemeSqliteHelper.ANNOTATIONS_TABLE, null, updateAnnotations);
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Meme> read() {

        ArrayList<Meme> memes = readMemes();
        addMemeAnnotations(memes);
        return memes;
    }

    private void addMemeAnnotations(ArrayList<Meme> memes) {
        SQLiteDatabase database = open();

        for (Meme meme : memes) {
            ArrayList<MemeAnnotation> annotations = new ArrayList<MemeAnnotation>();
            Cursor cursor = database.rawQuery(
                    "SELECT * FROM " + MemeSqliteHelper.ANNOTATIONS_TABLE +
                            " WHERE MEME_ID = " + meme.getmId(), null);

            if (cursor.moveToFirst()) {
                do {
                    MemeAnnotation annotation = new MemeAnnotation(
                            getIntFromColumnName(cursor, BaseColumns._ID),
                            getStringFromColumnName(cursor, MemeSqliteHelper.COLUMN_ANNOTATION_COLOR),
                            getStringFromColumnName(cursor, MemeSqliteHelper.COLUMN_ANNOTATION_TITLE),
                            getIntFromColumnName(cursor, MemeSqliteHelper.COLUMN_ANNOTATION_X),
                            getIntFromColumnName(cursor, MemeSqliteHelper.COLUMN_ANNOTATION_Y));
                    annotations.add(annotation);
                } while (cursor.moveToNext());
            }
            meme.setmAnnotations(annotations);
            cursor.close();
        }
        database.close();
    }
}
