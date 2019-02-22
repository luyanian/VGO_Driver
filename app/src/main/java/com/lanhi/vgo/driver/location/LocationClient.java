package com.lanhi.vgo.driver.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.Common;
import com.lanhi.vgo.driver.common.RObserver;

import java.util.HashMap;
import java.util.Map;

public class LocationClient {
    private static LocationClient locationClient;
    private LocationManager manager;
    private boolean isRunning = false;

    public static LocationClient getInstance(){
        if(locationClient==null) {
            synchronized (LocationClient.class){
                locationClient = new LocationClient();
            }
        }
        return locationClient;
    }

    public boolean isRunning(){
        return isRunning;
    }

    @SuppressLint("MissingPermission")
    public void startLocationListener(){
//        Criteria c = new Criteria();
//        c.setPowerRequirement(Criteria.POWER_LOW);//设置耗电量为低耗电
////        c.setBearingAccuracy(Criteria.ACCURACY_);//设置精度标准为粗糙
//        c.setAltitudeRequired(false);//设置海拔不需要/
//        c.setBearingRequired(false);//设置导向不需要
//        c.setAccuracy(Criteria.ACCURACY_FINE);//设置精度为低
////        c.setCostAllowed(false);//设置成本为不需要
//        //... Criteria 还有其他属性
        manager = (LocationManager) App.getInstance().getSystemService(Context.LOCATION_SERVICE);
//        String bestProvider = manager.getBestProvider(c, false);

        MyLocationListener gpsListener = new MyLocationListener();
        MyLocationListener networkListener = new MyLocationListener();

        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*1000, 500, gpsListener);
        }
        if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*1000, 500, networkListener);
        }

//        if (!TextUtils.isEmpty(bestProvider)) {
//            isRunning = true;
//            manager.requestLocationUpdates(bestProvider, 5*1000, 500, locationListener);
//        }
    }



    public void getLastLocation(){
        locationClient.getLastLocation();
    }

    private class MyLocationListener implements LocationListener {

        Location currentLocation;

        @Override
        public void onLocationChanged(Location location) {
            if (currentLocation != null) {
                if (isBetterLocation(location, currentLocation)) {
                    currentLocation = location;
                    showLocation(location);
                } else {
                    LogUtils.d("经度:" + location.getLongitude() + ",纬度:" + location.getLatitude()+"not is best location");
                }
            } else {
                currentLocation = location;
                showLocation(location);
            }
            //移除基于LocationManager.NETWORK_PROVIDER的监听器
//            if (LocationManager.NETWORK_PROVIDER.equals(location.getProvider())) {
//                manager.removeUpdates(this);
//            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }

        private void showLocation(Location location) {
            Toast.makeText(App.getInstance(), "经度:" + location.getLongitude() + ",纬度:" + location.getLatitude() + ",精确度:" + location.getAccuracy(),
                    Toast.LENGTH_SHORT).show();
            //经度
            double longitude = location.getLongitude();
            //纬度
            double latitude = location.getLatitude();
            //海拔
            double altitude = location.getAltitude();

            SPUtils.getInstance(SPConstants.LOCATION.NAME).put(SPConstants.LOCATION.LAT,latitude+"");
            SPUtils.getInstance(SPConstants.LOCATION.NAME).put(SPConstants.LOCATION.LNG,longitude+"");

            UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance(SPConstants.USER.NAME).readObject(SPConstants.USER.USER_INFO);
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
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > 60*1000;
        boolean isSignificantlyOlder = timeDelta < -60*1000;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location,
        // use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must
            // be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
                .getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate
                && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}

