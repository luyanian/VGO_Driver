package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.GetCityResponse;
import com.lanhi.vgo.driver.api.response.GetStateCityResponse;
import com.lanhi.vgo.driver.api.response.GetStatesResponse;
import com.lanhi.vgo.driver.api.response.GetVertificationResponse;
import com.lanhi.vgo.driver.common.CountDownTimerUtils;
import com.lanhi.vgo.driver.common.GlobalParams;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.UserRegisterStep1ActivityBinding;
import com.lanhi.vgo.driver.mvvm.model.UserData;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/user/register/step1")
public class RegistStep1Activity extends BaseActivity {
    UserViewModel viewModel;
    private String apiSmsCode="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRegisterStep1ActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.user_register_step1_activity);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        binding.setData(viewModel.getLiveDate().getValue());
        binding.setListener(new OnEventListener(){
            @Override
            public void getVerCode(final View v) {
                super.getVerCode(v);
                final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils((TextView) v, 60000, 1000);
                viewModel.getVerification(new RObserver<GetVertificationResponse>() {
                    @Override
                    public void onSuccess(GetVertificationResponse vertificationResponse) {
                        mCountDownTimerUtils.start();
                        if(vertificationResponse!=null&&vertificationResponse.getData()!=null&&vertificationResponse.getData().size()>0&&vertificationResponse.getData().get(0)!=null){
                            apiSmsCode = vertificationResponse.getData().get(0).getVerification();
                        }
                    }
                }, GlobalParams.SCOPE.REGIST);
            }

            @Override
            public void vertifySMSCode(View v) {
                super.vertifySMSCode(v);
                viewModel.vertifyCode(apiSmsCode,new RObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        UserData registData = viewModel.getLiveDate().getValue();
                        ARouter.getInstance()
                                .build("/user/register/step2")
                                .withString(UserData.KEY_PHONE,registData.getPhone())
                                .withString(UserData.KEY_SMSCODE,registData.getSmsCode())
                                .withString(UserData.KEY_PASSWORD,registData.getPassword())
                                .withString(UserData.KEY_PASSWORD2,registData.getPassword2())
                                .navigation();
                    }
                });
            }
        });
        viewModel.getLiveDate().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(@Nullable UserData registData) {

            }
        });
    }
}
