package com.lanhi.vgo.driver;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.ryon.utils.mutils.language.LanguageType;
import com.lanhi.ryon.utils.mutils.language.MultiLanguageUtil;
import com.lanhi.ryon.utils.mutils.SPUtils;

public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiLanguageUtil.init();
        int languageType = MultiLanguageUtil.getInstance().getLanguageType();
        if(languageType==LanguageType.LANGUAGE_FOLLOW_SYSTEM){
            ARouter.getInstance().build("/language/change").withBoolean("isShowBtnBack",false).navigation();
            finish();
        }else {
            String tokenId = SPUtils.getInstance(SPConstants.USER.NAME).getString(SPConstants.USER.TOKENID);
            if (TextUtils.isEmpty(tokenId)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ARouter.getInstance().build("/user/login").navigation();
                        finish();
                    }
                }, 200);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ARouter.getInstance().build("/main/main").with(getIntent().getExtras()).navigation();
                        finish();
                    }
                }, 200);
            }
        }
    }
}
