package com.lanhi.vgo.driver.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.gson.Gson;
import com.lanhi.ryon.utils.mutils.ActivityPools;
import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.Common;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.common.SPKeys;
import com.lanhi.vgo.driver.dialog.DialogOptions;
import com.lanhi.vgo.driver.dialog.DialogUtils;

import java.util.HashMap;
import java.util.Map;

import static com.lanhi.vgo.driver.common.GlobalParams.USER_TYPE;

public class LocationClient {
    private static LocationClient locationClient;
    private LocationManager locationManager;

    public static LocationClient getInstance(){
        synchronized (LocationClient.class){
            if(locationClient==null){
                locationClient = new LocationClient();
            }
            return locationClient;
        }
    }

    LocationListener locationListener = new LocationListener(){
        //当位置改变的时候调用
        @Override
        public void onLocationChanged(Location location) {
            //经度
            double longitude = location.getLongitude();
            //纬度
            double latitude = location.getLatitude();
            //海拔
            double altitude = location.getAltitude();
            UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
            if(userInfoData!=null) {
                Map<String, Object> map = new HashMap<>();
                map.put("tokenid", Common.getToken());
                map.put("distributor", userInfoData.getId());
                map.put("latitude", latitude+"");
                map.put("longitude", longitude+"");
                String json = new Gson().toJson(map);
                ApiRepository.getUpdateLatLng(json).subscribe(new RObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
//                    LogUtils.d(baseResponse.);
                    }
                });
            }
        }

        //当GPS状态发生改变的时候调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            switch (status) {

                case LocationProvider.AVAILABLE:
//                        message = "当前GPS为可用状态!";
                    break;

                case LocationProvider.OUT_OF_SERVICE:
//                        message = "当前GPS不在服务内!";
                    break;

                case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                        message = "当前GPS为暂停服务状态!";
                    break;

            }


        }

        //GPS开启的时候调用
        @Override
        public void onProviderEnabled(String provider) {
//                message = "GPS开启了!";
            if(locationManager==null){
                startLocationListener();
            }

        }
        //GPS关闭的时候调用
        @Override
        public void onProviderDisabled(String provider) {
//                message = "GPS关闭了!";
            stopListner();
        }
    };

    @SuppressLint("MissingPermission")
    public void startLocationListener(){
        stopListner();
        locationManager = (LocationManager) App.getInstance().getSystemService(Context.LOCATION_SERVICE);
//        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);//1.通过GPS定位，较精确。也比較耗电

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60*1000, 500, locationListener);
        } else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60*1000, 500, locationListener);
        }
    }
    /**
     * 销毁定位
     */
    public void stopListner(){
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        locationManager = null;
    }
}

