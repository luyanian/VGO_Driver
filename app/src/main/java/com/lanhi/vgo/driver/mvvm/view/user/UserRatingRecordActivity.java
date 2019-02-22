package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.UserRatingRecordActivityBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/user/score")
public class UserRatingRecordActivity extends BaseActivity {
    UserRatingRecordActivityBinding binding;
    UserViewModel userViewModel;
    private final static int size = 10;
    private int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_rating_record_activity);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
         userViewModel.getUserScore(new RObserver() {
             @Override
             public void onSuccess(Object o) {

             }
         });

        userViewModel.getUserScoreRecord(page,size,new RObserver() {
            @Override
            public void onSuccess(Object o) {

            }
        });
    }
}
