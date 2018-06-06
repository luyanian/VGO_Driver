package com.lanhi.vgo.driver.common;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.mvvm.view.user.LoginActivity;
import com.lanhi.ryon.utils.mutils.ActivityPools;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.ryon.utils.mutils.ToastUtils;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class RObserver<T> implements Observer{


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object value) {
        BaseResponse baseResponse = (BaseResponse) value;
        switch (baseResponse.getRet()){
            case "1"://接口调用成功
                onSuccess((T) value);
                break;
            case "99"://tokenid无效
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_user_tocken));
                SPUtils.getInstance().clear();
                ARouter.getInstance().build("/user/login").navigation();
                ActivityPools.finishAllExcept(LoginActivity.class);
                break;
            case "20001": //服务器错误
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_service));
                break;
            case "10001": //账户不存在
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_account_not_exsit));
                break;
            case "10002": //密码错误
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_user_password));
                break;
            case "10003": //尚未通过审核
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_user_applying));
                break;
            case "10004": //验证码无效
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_vertify_vercode));
                break;
            case "10005": //推荐人不存在
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_referee_not_exist));
                break;
            case "10006": //该手机号已被注册
                ToastUtils.showShort(App.getInstance().getResources().getString(R.string.error_account_already_exist));
                break;
            default:
                ToastUtils.showShort(baseResponse.getMsg());
                break;

        }
    }


    @Override
    public void onError(Throwable e) {
        Logger.e(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
    public abstract void onSuccess(T t);
}
