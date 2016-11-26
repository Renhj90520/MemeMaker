package com.example.renhaojie.mememaker.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renhaojie.mememaker.R;
import com.example.renhaojie.mememaker.models.Meme;

import java.util.List;

/**
 * Created by Ren Haojie on 2016/11/24.
 */

public class MemeListAdapter extends BaseAdapter {
    private List<Meme> imageItems;
    private Context context;

    public MemeListAdapter(List<Meme> imageItems, Context context) {
        this.imageItems = imageItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_meme_item, null);
        }
        convertView.setBackgroundResource(R.drawable.meme_list_selector);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.meme_image);
        TextView textView = (TextView) convertView.findViewById(R.id.meme_text);

        Meme imageItem = imageItems.get(position);
        imageView.setImageBitmap(imageItem.getBitmap());
        textView.setText(imageItem.getmName());

        return convertView;
    }

}
