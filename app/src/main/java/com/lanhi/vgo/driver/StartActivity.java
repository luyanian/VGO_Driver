package com.lanhi.vgo.driver;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.vgo.driver.common.SPKeys;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.ryon.utils.mutils.SpanUtils;

public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tokenId = SPUtils.getInstance().getString(SPKeys.TOKENID);
        if(TextUtils.isEmpty(tokenId)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ARouter.getInstance().build("/user/login").navigation();
                }
            },200);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ARouter.getInstance().build("/main/main").navigation();
                }
            },200);
        }

    }
}
