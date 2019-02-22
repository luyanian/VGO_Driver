package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RatingBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.ryon.utils.mutils.StringUtils;
import com.lanhi.ryon.utils.mutils.ToastUtils;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.ServiceScopeResponse;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.ServiceScopeActivityBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/user/servicescope")
public class ServiceScopeActivity extends BaseActivity {
    ServiceScopeActivityBinding binding;
    UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.service_scope_activity);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        userViewModel.getServiceScope(new RObserver<ServiceScopeResponse>() {
            @Override
            public void onSuccess(ServiceScopeResponse response) {
                if(response!=null|| !StringUtils.isEmpty(response.getData())) {
                    int rating = Integer.parseInt(response.getData());
                    binding.ratingBar.setRating(rating);
                }
                binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        int rating = (int) v;
                        userViewModel.updateServiceScope(rating, new RObserver() {
                            @Override
                            public void onSuccess(Object o) {
                                ToastUtils.showShort("succes");
                            }
                        });
                    }
                });
            }
        });
    }
}
