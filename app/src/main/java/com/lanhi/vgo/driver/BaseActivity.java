package com.lanhi.vgo.driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lanhi.ryon.utils.mutils.BarUtils;
import com.lanhi.vgo.driver.dialog.DialogOptions;
import com.lanhi.vgo.driver.dialog.DialogUtils;
import com.lanhi.vgo.driver.location.LocationClient;
import com.luck.picture.lib.permissions.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/3/21.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BarUtils.setStatusBarAlpha(this,1);
        App.getInstance().changeAppLanguage();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){
                    LocationClient.getInstance().startLocationListener();
                }else{
                    DialogUtils.getInstance()
                            .createMsgTipDialog(BaseActivity.this)
                            .setMsg(R.string.msg_location_provider_disable)
                            .setDialogOptons(new DialogOptions(){
                                @Override
                                public void sure(View v) {
                                    super.sure(v);
                                    Intent i = new Intent();
                                    i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(i);
                                }
                                @Override
                                public void cancle(View v) {
                                    super.cancle(v);
                                }
                            });
                }
            }
        });

    }

    public BaseActivity getInstance(){
        return this;
    }
}
