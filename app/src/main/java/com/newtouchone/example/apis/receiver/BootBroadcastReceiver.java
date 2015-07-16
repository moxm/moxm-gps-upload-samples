package com.newtouchone.example.apis.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.newtouchone.example.apis.service.LocationService;
import com.newtouchone.example.apis.service.UploadGpsService;

public class BootBroadcastReceiver extends BroadcastReceiver {
    
	private static final String TAG = BootBroadcastReceiver.class.getSimpleName();
	
	Intent mLocationServer;
	Intent mUploadServer;

    @Override    
    public void onReceive(Context context, Intent intent) {
    	Log.d(TAG, "========================开机自动启动===============================");
    	init(context);
//    	String action = intent.getAction();"android.intent.action.BOOT_COMPLETED"
    	start(context);
    }
    
    private void init(Context context){
        mUploadServer = new Intent(context, UploadGpsService.class);
        mLocationServer = new Intent(context, LocationService.class);
    }
    private void start(Context context){
    	context.startService(mLocationServer);
    	context.startService(mUploadServer);
    }
    
}