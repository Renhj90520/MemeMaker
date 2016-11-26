package com.example.renhaojie.mememaker.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renhaojie.mememaker.R;
import com.example.renhaojie.mememaker.models.ImageGridItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class GridViewAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int resource, ArrayList data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageGridItem item = (ImageGridItem) data.get(position);
        holder.image.setImageBitmap(item.getImage());
        holder.imageTitle.setText(item.getFileName());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
