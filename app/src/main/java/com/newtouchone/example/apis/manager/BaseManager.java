
package com.newtouchone.example.apis.manager;

import android.content.Context;

import com.newtouchone.example.apis.database.DatabaseHelper;


public class BaseManager {
	
    protected Context mContext;

    private DatabaseHelper mDatabaseHelper;

    public BaseManager(Context context) {
        this.mContext = context;
        this.mDatabaseHelper = new DatabaseHelper(mContext);
    }
    
    public DatabaseHelper getDatabaseHelper() {
    	return this.mDatabaseHelper;
    }
}
