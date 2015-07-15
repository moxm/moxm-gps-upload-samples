package com.newtouchone.example.apis.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Richard on 15/7/15.
 */

@DatabaseTable(tableName = "gps_upload")
public class Upload {


    @DatabaseField(generatedId = true, columnName = "_id")
    private Long _id;

    @DatabaseField(columnName = "positionId")
    private Long positionId;
    @DatabaseField(columnName = "gpsText")
    private String gpsText;
    @DatabaseField(columnName = "time")
    private String time;
    @DatabaseField(columnName = "count")
    private int count;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getGpsText() {
        return gpsText;
    }

    public void setGpsText(String gpsText) {
        this.gpsText = gpsText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
