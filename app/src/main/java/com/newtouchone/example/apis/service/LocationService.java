package com.newtouchone.example.apis.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.newtouchone.example.apis.domain.Position;
import com.newtouchone.example.apis.event.GpsEvent;
import com.newtouchone.example.apis.manager.GpsManager;
import com.newtouchone.example.apis.otto.BusProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service {

	private static final String TAG = LocationService.class.getSimpleName();

	private GpsManager mGpsManager;

	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;

	@Override
	public void onCreate() {
		Log.d(TAG, "========================onCreate");
		super.onCreate();
		mGpsManager = new GpsManager(this.getApplicationContext());

		BusProvider.getInstance().register(this);

		mLocationClient = new LocationClient(this.getApplicationContext());
		mGeofenceClient = new GeofenceClient(this.getApplicationContext());

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		int span = 1000;

		option.setScanSpan(span);
		option.setIsNeedAddress(true);

		mLocationClient.setLocOption(option);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);

		mLocationClient.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
	}

	class MyLocationListener implements BDLocationListener {

		long time = -1;
		int count = 0;
		@Override
		public void onReceiveLocation(BDLocation location) {
			long currentTime = System.currentTimeMillis();
			if(currentTime - time >= 1000 * 60 * 10) {
				time = currentTime;
				Position data = new Position();

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time = format.format(new Date(currentTime));

				data.setTime("百度：" + location.getTime() + "\n本机：" + time);
				data.setAddrStr(location.getAddrStr());
				mGpsManager.insertPosition(data);

			}

			count++;

			//Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("count : ");
			sb.append(count);
			sb.append("\ntime : ");
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

			BusProvider.getInstance().post(new GpsEvent(sb.toString()));
		}
	}



	@Override
	public void onDestroy() {
		Log.d(TAG, "========================onDestroy");
		super.onDestroy();

		BusProvider.getInstance().unregister(this);

		if (mLocationClient != null) {
			mLocationClient.unRegisterLocationListener(mMyLocationListener);
		}

		mTimer.schedule(mTimerTask, 1000);
	}


	

	Timer mTimer = new Timer();
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			Log.d(TAG, "========================mTimerTask");
			Intent serviceLauncher = new Intent(getApplicationContext(), LocationService.class);
			startService(serviceLauncher);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}