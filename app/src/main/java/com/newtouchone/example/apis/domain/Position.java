package com.newtouchone.example.apis.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Richard on 15/7/15.
 */
@DatabaseTable(tableName = "position")
public class Position {

    @DatabaseField(generatedId = true, columnName = "_id")
    private Long _id;

    @DatabaseField(columnName = "time")
    private String time;
    @DatabaseField(columnName = "locType")
    private int locType;
    @DatabaseField(columnName = "latitude")
    private double latitude;
    @DatabaseField(columnName = "lontitude")
    private double lontitude;
    @DatabaseField(columnName = "radius")
    private float radius;
    @DatabaseField(columnName = "addrStr")
    private String addrStr;
    @DatabaseField(columnName = "operators")
    private int operators;
    @DatabaseField(columnName = "direction")
    private float direction;
    @DatabaseField(columnName = "satelliteNumber")
    private int satelliteNumber;
    @DatabaseField(columnName = "speed")
    private float speed;
    @DatabaseField(columnName = "text")
    private String text;


    @DatabaseField(columnName = "uploaded")
    private Boolean uploaded = Boolean.FALSE;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLocType() {
        return locType;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLontitude() {
        return lontitude;
    }

    public void setLontitude(double lontitude) {
        this.lontitude = lontitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public int getOperators() {
        return operators;
    }

    public void setOperators(int operators) {
        this.operators = operators;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public int getSatelliteNumber() {
        return satelliteNumber;
    }

    public void setSatelliteNumber(int satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }
}
