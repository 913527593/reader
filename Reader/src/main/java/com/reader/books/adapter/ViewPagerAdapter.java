package com.reader.books.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by john on 2017/5/29.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> list = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
    }
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragment) {
        super(fm);
        list.addAll(fragment);
    }

//    public void addFragment(Fragment fragment) {
//        list.add(fragment);
//    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
