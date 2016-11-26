package com.example.renhaojie.mememaker.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renhaojie.mememaker.R;
import com.example.renhaojie.mememaker.database.MemeDataSource;
import com.example.renhaojie.mememaker.models.Meme;
import com.example.renhaojie.mememaker.models.MemeAnnotation;
import com.example.renhaojie.mememaker.ui.activities.CreateMemeActivity;
import com.example.renhaojie.mememaker.ui.activities.MemeSettingsActivity;
import com.example.renhaojie.mememaker.utils.FileUtilities;

import java.util.ArrayList;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class MemeItemFragment extends ListFragment {
    private Menu mMenu;
    private int mSelectedItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.meme_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.settings_action) {
            Intent intent = new Intent(getActivity(), MemeSettingsActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.share_action) {
            Meme meme = (Meme) getListAdapter().getItem(mSelectedItem);
            Bitmap bitmap = createMeme(meme);
            Uri uriForShare = FileUtilities.saveImageForSharing(getActivity(), bitmap, "new_meme.jpg");

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriForShare);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Send to"));
        } else if (itemId == R.id.edit_action) {
            Toast.makeText(getActivity(), "Into edit image now", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), CreateMemeActivity.class);
            Meme meme = (Meme) getListAdapter().getItem(mSelectedItem);
            intent.putExtra(CreateMemeActivity.EXTRA_MEME_OBJECT, meme);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        MemeDataSource dataSource = new MemeDataSource(getActivity());
        ArrayList<Meme> memes=dataSource.read();
    }

    private Bitmap createMeme(Meme meme) {
        Bitmap bitmap = BitmapFactory.decodeFile(meme.getmAssetLocation());
        Bitmap workingBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        float scale = getActivity().getResources().getDisplayMetrics().density;
        Canvas canvas = new Canvas(workingBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        for (MemeAnnotation annotation : meme.getmAnnotations()) {
            paint.setColor(Color.parseColor(annotation.getmColor()));
            paint.setTextSize(12 * scale);

            Rect bounds = new Rect();
            String text = annotation.getmTitle();
            paint.getTextBounds(text, 0, text.length(), bounds);

            int x = annotation.getmLocationX();
            int y = annotation.getmLocationY();

            canvas.drawText(text, x, y, paint);
        }

        return workingBitmap;
    }
}
