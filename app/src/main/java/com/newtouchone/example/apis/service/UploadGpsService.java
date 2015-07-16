package com.newtouchone.example.apis.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newtouchone.example.apis.domain.Position;
import com.newtouchone.example.apis.domain.Upload;
import com.newtouchone.example.apis.manager.GpsManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UploadGpsService extends Service implements Runnable {

	private static final String TAG = UploadGpsService.class.getSimpleName();

	protected boolean isRun = true;
	private GpsManager mGpsManager;
	private RequestQueue mQueue;

	@Override
	public void onCreate() {
		Log.d(TAG, "========================onCreate");
		super.onCreate();
		mGpsManager = new GpsManager(this.getApplicationContext());
		mQueue = Volley.newRequestQueue(getApplicationContext());

		new Thread(this).start();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "========================onDestroy");
		super.onDestroy();
		isRun = false;
		mTimer.schedule(mTimerTask, 1000);
	}

	@Override
	public void run() {
		while (isRun) {

			Log.d(TAG, "++++++++Run");
			List<Position> datas = mGpsManager.findNotUploadDatas();
//			if (!datas.isEmpty()) {

				Log.d(TAG, "++++++++data is not Empty, size: " + datas.size());
				StringRequest request = new StringRequest(Method.GET, "http://api.map.baidu.com/telematics/v3/weather?location=%E7%BB%A5%E5%BE%B7&output=json&ak=11ffd27d38deda622f51c9d314d46b17", new SuccessListener<String>(datas), errorListener);

				mQueue.add(request);
//			}

			//10分钟提交一次
			Thread.currentThread();
			try {
				Thread.sleep(1000 * 60 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class SuccessListener<T> implements Response.Listener<T> {
		private List<Position> mDatas;
		public SuccessListener (List<Position> datas) {
			this.mDatas = datas;
		}
		@Override
		public void onResponse(T t) {
			Log.d(TAG, "++++++++SuccessListener");
			mGpsManager.updataUploaded(mDatas);
			Upload data = new Upload();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			String time = format.format(new Date(System.currentTimeMillis()));

			data.setTime(time);
			data.setGpsText("同步成功");
			data.setCount(mDatas.size());
			mGpsManager.insertUpload(data);
		}
	}

	private Response.ErrorListener errorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError volleyError) {
			Log.d(TAG, "++++++++ErrorListener");
			Upload data = new Upload();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			String time = format.format(new Date(System.currentTimeMillis()));

			data.setTime(time);
			data.setGpsText("同步失败");
			mGpsManager.insertUpload(data);
		}
	};

	

	Timer mTimer = new Timer();
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			Log.d(TAG, "========================mTimerTask");
			Intent serviceLauncher = new Intent(getApplicationContext(), UploadGpsService.class);
			startService(serviceLauncher);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}