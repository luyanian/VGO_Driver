package com.lanhi.vgo.driver.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cli on 14/11/2016.
 * All Rights Reserved.
 */

public class StateCityData {

    public static final int STATE = 1;
    public static final int CITY  = 2;

    private String id;
    private String name;
    private String zipCode;
    private String stateId;
    private int type;

    public StateCityData() {
    }

    public StateCityData(String id, String name, String zipCode, String stateId, int type) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.stateId = stateId;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name != null ? name : super.toString();
    }

}
