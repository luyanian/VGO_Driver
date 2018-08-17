package com.lanhi.vgo.driver.service;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * 一项继承 FirebaseInstanceIdService 的服务，
 * 用于处理注册令牌的创建、轮替和更新。如果要发送至特定设备或者创建设备组，则必须添加此服务。
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
    }
}
