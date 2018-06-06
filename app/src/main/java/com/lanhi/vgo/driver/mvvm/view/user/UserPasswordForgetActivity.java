package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.GetVertificationResponse;
import com.lanhi.vgo.driver.common.GlobalParams;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.UserPasswordForgetBinding;
import com.lanhi.vgo.driver.mvvm.model.UserData;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/user/password/forgot")
public class UserPasswordForgetActivity extends BaseActivity {
    UserPasswordForgetBinding binding;
    UserViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.user_password_forget);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initTitleBar();
        initEventListener();
        initDataChanged();
        initData();
    }

    private void initTitleBar() {
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
    }

    private void initEventListener() {
        binding.setListener(new OnEventListener(){
            @Override
            public void resetPassword(final View v) {
                super.getVerCode(v);
                if(viewModel.getLiveDate().getValue().getCurrentItem()==0) {
                    viewModel.getVerification(new RObserver<GetVertificationResponse>() {
                        @Override
                        public void onSuccess(GetVertificationResponse getVertificationResponse) {
                            viewModel.getLiveDate().getValue().setCurrentItem(1);
                            viewModel.getLiveDate().getValue().setMsgTip(App.getInstance().getResources().getString(R.string.msg_vertify_account_success));
                            binding.setData(viewModel.getLiveDate().getValue());
                        }
                    }, GlobalParams.SCOPE.FIND_PASSWORD);
                }else{
                    viewModel.resetPassword(new RObserver<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse baseResponse) {
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void initDataChanged() {
        viewModel.getLiveDate().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(@Nullable UserData userData) {
                if(!TextUtils.isEmpty(userData.getMsgTip())) {
                    binding.setMsg(userData.getMsgTip());
                }
            }
        });
    }

    private void initData() {
        binding.setData(viewModel.getLiveDate().getValue());
        viewModel.getLiveDate().getValue().setCurrentItem(0);
        viewModel.getLiveDate().getValue().setMsgTip(App.getInstance().getResources().getString(R.string.msg_vertify_your_account));
    }
}
