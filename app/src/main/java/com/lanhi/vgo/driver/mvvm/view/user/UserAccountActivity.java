package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.databinding.UserAccountManagermentActivityBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;
import com.lanhi.ryon.utils.mutils.SPUtils;

@Route(path = "/user/account/manage")
public class UserAccountActivity extends BaseActivity {
    private UserAccountManagermentActivityBinding binding;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_account_managerment_activity);
        userViewModel = ViewModelProviders.of(getInstance()).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance(SPConstants.USER.NAME).readObject(SPConstants.USER.USER_INFO);
        if(userInfoData!=null){
//            userInfoData.
        }
        binding.setEvent(new OnEventListener(){
            @Override
            public void viewUserAccunt(View v) {
                super.viewUserAccunt(v);
                ARouter.getInstance().build("/user/name/edit").navigation();
            }

            @Override
            public void viewUserAccountInfo(View v) {
                super.viewUserAccountInfo(v);
                ARouter.getInstance().build("/user/check/edit").navigation();
            }

            @Override
            public void viewPasswordEdit(View v) {
                super.viewPasswordEdit(v);
                ARouter.getInstance().build("/user/password/edit").navigation();
            }
        });
    }
}
