package com.newtouchone.example.apis.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 15/7/15.
 */
public class InfoPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mDatas = new ArrayList<Fragment>();

    public InfoPagerAdapter(FragmentManager fm, List<Fragment> datas) {
        super(fm);
        mDatas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "GPS 记录";
            case 1:
            default:
                return "上传记录";
        }
    }
}
