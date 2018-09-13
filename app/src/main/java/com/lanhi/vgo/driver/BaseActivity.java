package com.lanhi.vgo.driver;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.lanhi.ryon.utils.mutils.BarUtils;

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
    }

    public BaseActivity getInstance(){
        return this;
    }
}
