package com.lanhi.vgo.driver.mvvm.view.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.ryon.utils.mutils.ToastUtils;
import com.lanhi.vgo.driver.BR;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.response.GetVertificationResponse;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.CountDownTimerUtils;
import com.lanhi.vgo.driver.common.GlobalParams;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.common.SPKeys;
import com.lanhi.vgo.driver.databinding.UserLoginActivityBinding;
import com.lanhi.vgo.driver.api.response.LoginResponse;
import com.lanhi.vgo.driver.mvvm.model.UserData;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.selector.RSelectorChangeLisener;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.ryon.utils.mutils.SpanUtils;
import com.squareup.sdk.pos.ChargeRequest;
import com.squareup.sdk.pos.PosClient;
import com.squareup.sdk.pos.PosSdk;

import static com.squareup.sdk.pos.CurrencyCode.USD;

/**
 * Created by Administrator on 2018/3/21.
 */
@Route(path = "/user/login")
public class LoginActivity extends BaseActivity {
    private UserLoginActivityBinding binding;
    private UserViewModel viewModel;
    private PosClient posClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_login_activity);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setData(viewModel.getLiveDate().getValue());
        posClient = PosSdk.createClient(LoginActivity.this, "sq0idp-ibJRYv86sNY_uEcUkkRy8Q");

        binding.rselector.setRSelectorChangeListener(new RSelectorChangeLisener() {
            @Override
            public void onLeftClick() {
                viewModel.getLiveDate().getValue().setCurrentItem(0);
                binding.setData(viewModel.getLiveDate().getValue());
            }

            @Override
            public void onRightClick() {
                viewModel.getLiveDate().getValue().setCurrentItem(1);
                binding.setData(viewModel.getLiveDate().getValue());
            }
        });
        binding.setListener(new OnEventListener() {
            @Override
            public void login(View v) {
                super.login(v);
                viewModel.login(new RObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        SPUtils.getInstance().put(SPKeys.TOKENID, loginResponse.getTokenid());
                        UserInfoDataBean userInfoData = loginResponse.getData().get(0);
                        SPUtils.getInstance().saveObject(SPKeys.USER_INFO, userInfoData);
                        ARouter.getInstance().build("/main/main").navigation();
                        finish();
                    }
                });
            }

            @Override
            public void getVerCode(final View v) {
                super.getVerCode(v);
                viewModel.getVerification(new RObserver<GetVertificationResponse>() {
                    @Override
                    public void onSuccess(GetVertificationResponse getVertificationResponse) {
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils((TextView) v, 60000, 1000);
                        mCountDownTimerUtils.start();
                    }
                }, GlobalParams.SCOPE.LOGIN_WITH_CODE);
            }

            @Override
            public void toRegistActivity(View v) {
                super.toRegistActivity(v);
                ARouter.getInstance().build("/user/register/step1").navigation();
            }

            @Override
            public void toForgotPwdActivity(View v) {
                super.toForgotPwdActivity(v);
                ARouter.getInstance().build("/user/password/forgot").navigation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data == null) {
                ToastUtils.showShort("Square Point of Sale was uninstalled or crashed");
                return;
            }
            if (resultCode == Activity.RESULT_OK) {
                ChargeRequest.Success success = posClient.parseChargeSuccess(data);
                String message = "Client transaction id: " + success.clientTransactionId;
                ToastUtils.showShort(message);
            } else {
                ChargeRequest.Error error = posClient.parseChargeError(data);

                if (error.code == ChargeRequest.ErrorCode.TRANSACTION_ALREADY_IN_PROGRESS) {
                    String title = "A transaction is already in progress";
                    String message = "Please complete the current transaction in Point of Sale.";
                    new AlertDialog.Builder(this)
                            .setTitle(title)
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    // Some errors can only be fixed by launching Point of Sale
                                    // from the Home screen.
                                    posClient.launchPointOfSale();
                                }
                            })
                            .show();
                } else {
                    ToastUtils.showShort("Error: " + error.code, error.debugDescription);
                }
            }
        }
    }
}
