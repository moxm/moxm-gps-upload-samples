package com.newtouchone.example.apis.manager;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.newtouchone.example.apis.domain.Position;
import com.newtouchone.example.apis.domain.Upload;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 15/7/15.
 */
public class GpsManager extends BaseManager {

    private Dao<Position, Long> positionDao;
    private Dao<Upload, Long> uploadDao;

    public GpsManager(Context context){
        super(context);
    }

    private Dao<Position, Long> getPositionDao() throws SQLException {
        if(positionDao == null){
            positionDao = getDatabaseHelper().getDao(Position.class);
        }
        return positionDao;
    }
    private Dao<Upload, Long> getUploadDao() throws SQLException {
        if(uploadDao == null){
            uploadDao = getDatabaseHelper().getDao(Upload.class);
        }
        return uploadDao;
    }



    public void insertPosition(Position item) {
        Log.i("GpsManager", "+++insertPosition.time: " + item.getTime() + "   address:" + item.getAddrStr());
        if (item == null) return;

        AndroidDatabaseConnection db = new AndroidDatabaseConnection(getDatabaseHelper().getWritableDatabase(), true);
        db.setAutoCommit(false);
        try {
            getPositionDao().create(item);
            db.commit(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertUpload(Upload item) {
        Log.i("GpsManager", "+++insertUpload.time: " + item.getTime() + "   count:" + item.getCount());
        if (item == null) return;

        AndroidDatabaseConnection db = new AndroidDatabaseConnection(getDatabaseHelper().getWritableDatabase(), true);
        db.setAutoCommit(false);
        try {
            getUploadDao().create(item);
            db.commit(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<Position> findAllPositionDatas() {
        List<Position> datas = new ArrayList<Position>();
        try {
            QueryBuilder<Position, Long> queryBuilder = getPositionDao().queryBuilder();
            queryBuilder.orderBy("time", true);
            datas = getPositionDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datas;
    }

    public List<Upload> findAllUploadDatas() {
        List<Upload> datas = new ArrayList<Upload>();
        try {
            QueryBuilder<Upload, Long> queryBuilder = getUploadDao().queryBuilder();
            queryBuilder.orderBy("time", true);
            datas = getUploadDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public List<Position> findNotUploadDatas() {
        List<Position> datas = new ArrayList<Position>();
        try {
            QueryBuilder<Position, Long> queryBuilder = getPositionDao().queryBuilder();
            Where<Position, Long> where = queryBuilder.where();
            where.eq("uploaded", Boolean.FALSE);
            queryBuilder.orderBy("time", true);
            datas = getPositionDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public void updataUploaded(List<Position> datas){
        AndroidDatabaseConnection db = new AndroidDatabaseConnection(getDatabaseHelper().getWritableDatabase(), true);
        db.setAutoCommit(false);
        try {
            for (Position data : datas) {
                data.setUploaded(Boolean.TRUE);
                getPositionDao().update(data);
            }
            db.commit(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void clearAllData(){
        AndroidDatabaseConnection db = new AndroidDatabaseConnection(getDatabaseHelper().getWritableDatabase(), true);
        db.setAutoCommit(false);
        try {
            getUploadDao().delete(getUploadDao().queryForAll());
            getPositionDao().delete(getPositionDao().queryForAll());
            db.commit(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
