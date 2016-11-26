package com.example.renhaojie.mememaker.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.renhaojie.mememaker.ui.fragments.ImageGridFragment;
import com.example.renhaojie.mememaker.ui.fragments.MemeItemFragment;

/**
 * Created by Ren Haojie on 2016/11/25.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {
    Context mContext;

    public SectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        if (position == 0) {
            fragment = new ImageGridFragment();
        } else {
            fragment = new MemeItemFragment();
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Images";
        } else {
            return "Memes";
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
