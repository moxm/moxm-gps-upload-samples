package com.newtouchone.example.apis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newtouchone.example.apis.event.GpsEvent;
import com.newtouchone.example.apis.otto.BusProvider;
import com.squareup.otto.Subscribe;


/**
 * 百度定位
 * @author xiangli.ma
 *
 */
public class MainFragment extends Fragment {

    private TextView mTextView1;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_location, container, false);
        init(savedInstanceState);
        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fillView();
		setListener();
		loadData();
	}
	
	/**
	 * 初始化
	 * 
	 * @param savedInstanceState
	 */
	protected void init(Bundle savedInstanceState) {
	}
	/**
	 * 获取View对象
	 */
	protected void fillView() {
        mTextView1 = (TextView) getView().findViewById(R.id.textview1);
	}
	/**
	 * 设置监听事件
	 */
	protected void setListener() {
	}
	/**
	 * 加载填充数据
	 */
	protected void loadData() {
	}


    @Subscribe
    public void onGpsEvent(GpsEvent event) {
        mTextView1.setText(event.content);
    }


    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
