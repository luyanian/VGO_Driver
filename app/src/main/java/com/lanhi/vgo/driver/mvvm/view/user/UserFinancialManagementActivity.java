package com.lanhi.vgo.driver.mvvm.view.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.UserFinancialManagementActivityBinding;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/user/financial")
public class UserFinancialManagementActivity extends BaseActivity {
    private UserFinancialManagementActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_financial_management_activity);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
    }
}
