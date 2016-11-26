package com.example.renhaojie.mememaker.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.renhaojie.mememaker.R;
import com.example.renhaojie.mememaker.adapters.GridViewAdapter;
import com.example.renhaojie.mememaker.models.ImageGridItem;
import com.example.renhaojie.mememaker.ui.activities.CreateMemeActivity;
import com.example.renhaojie.mememaker.ui.activities.MemeSettingsActivity;
import com.example.renhaojie.mememaker.utils.FileUtilities;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    GridView mGridView;
    GridViewAdapter mGridAdapter;
    public static int RESULT_LOAD_IMAGE = 1000;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_grid, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.gridView);
        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.view_grid, extractFiles());
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);
        this.setHasOptionsMenu(true);
        return rootView;
    }

    private ArrayList extractFiles() {
        final ArrayList imageItems = new ArrayList();
        File[] filteredFiles = FileUtilities.listFiles(getActivity());
        for (File file : filteredFiles) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ImageGridItem item = new ImageGridItem(bitmap, file.getName(), file.getAbsolutePath());
            imageItems.add(item);
        }
        return imageItems;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.image_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.import_action) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getActivity().startActivityForResult(intent, RESULT_LOAD_IMAGE);
        } else if (itemId == R.id.settings_action) {
            Intent intent = new Intent(this.getActivity(), MemeSettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ImageGridItem imageGridItem = (ImageGridItem) adapterView.getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), CreateMemeActivity.class);
        intent.putExtra(CreateMemeActivity.EXTRA_IMAGE_FILE_PATH, imageGridItem.getFullPath());
        Log.d("FILE:", imageGridItem.getFullPath());
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        final ImageGridItem imageGridItem = (ImageGridItem) adapterView.getAdapter().getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please confrim")
                .setMessage("Are you sure you want to remove this file?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File fileToDelete = new File(imageGridItem.getFullPath());
                        boolean deleted = fileToDelete.delete();
                        if (deleted) {
                            resetGridAdapter();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
        return true;
    }

    private void resetGridAdapter() {
        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.view_grid, extractFiles());
        mGridView.setAdapter(mGridAdapter);
    }
}
