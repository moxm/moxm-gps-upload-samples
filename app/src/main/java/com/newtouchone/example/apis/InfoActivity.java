package com.newtouchone.example.apis;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.newtouchone.example.apis.adapter.InfoPagerAdapter;
import com.newtouchone.example.apis.manager.GpsManager;

import java.util.ArrayList;
import java.util.List;


public class InfoActivity extends ActionBarActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private GpsManager mGpsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);

        mGpsManager = new GpsManager(this);

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.pager);
//
//        mTabLayout.addTab(mTabLayout.newTab().setText("GPS 记录"), true);
//        mTabLayout.addTab(mTabLayout.newTab().setText("上传记录"));

        initViewPager();



        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViewPager(){
        Fragment gpsFragment = new GpsRecordsFragment();
        Fragment uploadFragment = new UploadRecordsFragment();

        List<Fragment> datas = new ArrayList<Fragment>();
        datas.add(gpsFragment);
        datas.add(uploadFragment);

        mViewPager.setAdapter(new InfoPagerAdapter(getSupportFragmentManager(), datas));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                mGpsManager.clearAllData();
                initViewPager();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
