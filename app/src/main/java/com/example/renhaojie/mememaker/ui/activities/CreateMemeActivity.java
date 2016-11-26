package com.example.renhaojie.mememaker.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.renhaojie.mememaker.R;
import com.example.renhaojie.mememaker.database.MemeDataSource;
import com.example.renhaojie.mememaker.models.Meme;
import com.example.renhaojie.mememaker.models.MemeAnnotation;
import com.example.renhaojie.mememaker.views.MemeImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ren Haojie on 2016/11/25.
 */

public class CreateMemeActivity extends Activity {
    public static final String EXTRA_IMAGE_FILE_PATH = "EXTRA_IMAGE_FILE_PATH";
    public static final String EXTRA_MEME_OBJECT = "EXTRA_MEME_OBJECT";

    MemeImageView mMemeBitmapHolder;
    FrameLayout mMemeContainer;
    List<EditText> mMemeTexts;
    String mImageFilePath;
    String mCurrentColor;
    Meme mCurrentMeme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meme);

        mMemeContainer = (FrameLayout) findViewById(R.id.meme_container);
        mMemeBitmapHolder = (MemeImageView) findViewById(R.id.meme_bitmap_container);
        mMemeTexts = new ArrayList<>();

        if (this.getIntent().hasExtra(EXTRA_IMAGE_FILE_PATH)) {
            mImageFilePath = getIntent().getStringExtra(EXTRA_IMAGE_FILE_PATH);
            mCurrentMeme = new Meme(-1, mImageFilePath, null, "");
        } else {
            mCurrentMeme = (Meme) getIntent().getSerializableExtra(EXTRA_MEME_OBJECT);
            mImageFilePath = mCurrentMeme.getmAssetLocation();

            for (MemeAnnotation annotation : mCurrentMeme.getmAnnotations()) {
                addEditTextOverImage(annotation.getmTitle(), annotation.getmLocationX(), annotation.getmLocationY(), annotation.getmColor());
            }
        }

        mMemeBitmapHolder.setImageBitmap(mCurrentMeme.getBitmap());
        mMemeBitmapHolder.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEvent.ACTION_UP == event.getAction()) {
                    int touchX = (int) event.getX();
                    int touchY = (int) event.getY();
                    addAnnotion(touchX, touchY, mCurrentColor);
                    addEditTextOverImage("Title", touchX, touchY, mCurrentColor);
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    private void addAnnotion(int touchX, int touchY, String mCurrentColor) {
        MemeAnnotation annotation = new MemeAnnotation();
        annotation.setmColor(mCurrentColor);
        annotation.setmLocationX(touchX);
        annotation.setmLocationY(touchY);

        if (mCurrentMeme.getmAnnotations() == null) {
            mCurrentMeme.setmAnnotations(new ArrayList<MemeAnnotation>());
        }
        mCurrentMeme.getmAnnotations().add(annotation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_meme, menu);
        Spinner colorOptions = (Spinner) menu.findItem(R.id.choose_font_action).getActionView();
        colorOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentColor = getResources().getStringArray(R.array.fontColorValues)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.save_action) {
            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Name of Meme?")
                    .setMessage("Please give this meme a name.")
                    .setView(input)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String memeName = input.getText().toString();
                            mCurrentMeme.setmName(memeName);
                            saveMeme();
                            finish();
                        }
                    }).show();
            return true;
        } else if (itemId == android.R.id.home) {
            finish();
            return true;
        } else if (itemId == R.id.choose_font_action) {

        }

        return super.onOptionsItemSelected(item);
    }

    private void saveMeme() {
        for (int i = 0; i < mMemeTexts.size(); i++) {
            EditText editText = mMemeTexts.get(i);
            MemeAnnotation annotation = mCurrentMeme.getmAnnotations().get(i);
            annotation.setmTitle(editText.getText().toString());
        }
        MemeDataSource dataSource = new MemeDataSource(this);
        if (mCurrentMeme.getmId() == -1) {
            dataSource.create(mCurrentMeme);
        } else {
            dataSource.update(mCurrentMeme);
        }
    }



    private void addEditTextOverImage(String title, int x, int y, String color) {
        EditText editText = new EditText(this);
        editText.setText(title);
        editText.setBackground(null);
        editText.setTextColor(Color.parseColor(color));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(x, y, 0, 0);
        editText.setLayoutParams(layoutParams);

        mMemeContainer.addView(editText);
        editText.requestFocus();
        mMemeTexts.add(editText);
    }
}
