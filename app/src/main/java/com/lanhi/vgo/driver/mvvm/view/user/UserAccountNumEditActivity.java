package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.LoginResponse;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.common.SPKeys;
import com.lanhi.vgo.driver.databinding.UserAccountNumEditActivityBinding;
import com.lanhi.vgo.driver.mvvm.model.UserData;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.ryon.utils.mutils.ToastUtils;

@Route(path = "/user/check/edit")
public class UserAccountNumEditActivity extends BaseActivity {
    private UserAccountNumEditActivityBinding binding;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_account_num_edit_activity);
        userViewModel = ViewModelProviders.of(getInstance()).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        UserInfoDataBean userInfo = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
        UserData userData = new UserData();
        if(userInfo!=null){
            userData.setCheckNum(userInfo.getChecking_account());
            userData.setRoutingNum(userInfo.getRouting_number());
        }
        binding.setData(userData);
        binding.setEvent(new OnEventListener(){
            @Override
            public void userAccountNumEdit(View v, String checkNum, String routingNum) {
                super.userAccountNumEdit(v, checkNum, routingNum);
                userViewModel.editUserAccountNum(checkNum, routingNum, new RObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        ToastUtils.showShort(R.string.msg_commit_successful);
                        finish();
                    }
                });
            }
        });
    }
}
