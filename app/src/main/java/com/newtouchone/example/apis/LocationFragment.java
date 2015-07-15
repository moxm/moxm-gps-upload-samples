package com.newtouchone.example.apis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.newtouchone.example.apis.domain.Position;
import com.newtouchone.example.apis.manager.GpsManager;


/**
 * 百度定位
 * @author xiangli.ma
 *
 */
public class LocationFragment extends Fragment {

    public LocationClient mLocationClient;
    public GeofenceClient mGeofenceClient;
    public MyLocationListener mMyLocationListener;

    private GpsManager mGpsManager;

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

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
		Bundle bundle = getArguments();

        mGpsManager = new GpsManager(getActivity());

        mLocationClient = new LocationClient(getActivity());
        mGeofenceClient = new GeofenceClient(getActivity());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 1000;

        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
	}
	/**
	 * 获取View对象
	 */
	protected void fillView() {
        mTextView1 = (TextView) getView().findViewById(R.id.textview1);
        mTextView2 = (TextView) getView().findViewById(R.id.textview2);
        mTextView3 = (TextView) getView().findViewById(R.id.textview3);
	}
	/**
	 * 设置监听事件
	 */
	protected void setListener() {
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
	}
	/**
	 * 加载填充数据
	 */
	protected void loadData() {
        mLocationClient.start();
	}


    /**
     *
     */
    public class MyLocationListener implements BDLocationListener {

        long time = -1;
        @Override
        public void onReceiveLocation(BDLocation location) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - time >= 1000 * 60 * 10) { //10分钟存储到数据库一次
                time = currentTime;
                Position data = new Position();
                data.setAddrStr(location.getAddrStr());
                data.setTime(location.getTime());
                mGpsManager.insertPosition(data);
            }

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append(location.getDirection());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
            }
//            Log.i("BaiduLocationApiDem", sb.toString());
            mTextView1.setText(sb.toString());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mLocationClient.stop();
    }
}
