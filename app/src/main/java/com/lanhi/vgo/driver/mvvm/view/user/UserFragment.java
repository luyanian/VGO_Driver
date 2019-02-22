package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.UserInfoResponse;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.databinding.UserFragmentBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.ryon.utils.mutils.ActivityPools;
import com.lanhi.ryon.utils.mutils.SPUtils;

public class UserFragment extends Fragment {
    UserFragmentBinding binding;
    UserViewModel userViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = ViewModelProviders.of((BaseActivity)this.getActivity()).get(UserViewModel.class);
        binding.setEvent(new OnEventListener(){
            @Override
            public void signOut(View v) {
                super.signOut(v);
                SPUtils.getInstance(SPConstants.USER.NAME).clear();
                SPUtils.getInstance(SPConstants.LOCATION.NAME).clear();
                SPUtils.getInstance(SPConstants.PAYPAL.NAME).clear();
                ARouter.getInstance().build("/user/login").navigation();
                ActivityPools.finishAllExcept(LoginActivity.class);
            }

            @Override
            public void viewUserInfo(View v) {
                super.viewUserInfo(v);
//                ARouter.getInstance().build("/user/login").navigation();
            }

            @Override
            public void viewUserShop(View v) {
                super.viewUserShop(v);
                ARouter.getInstance().build("/user/shop").navigation();
            }

            @Override
            public void viewUserAccunt(View v) {
                super.viewUserAccunt(v);
                ARouter.getInstance().build("/user/account/manage").navigation();
            }

            @Override
            public void viewUserFinancial(View v) {
                super.viewUserFinancial(v);
                ARouter.getInstance().build("/user/financial").navigation();
            }

            @Override
            public void viewServiceScope(View v) {
                super.viewServiceScope(v);
                ARouter.getInstance().build("/user/servicescope").navigation();
            }

            @Override
            public void viewMyScore(View v) {
                super.viewMyScore(v);
                ARouter.getInstance().build("/user/score").navigation();
            }

            @Override
            public void viewChangedLanguage(View v) {
                super.viewChangedLanguage(v);
                ARouter.getInstance().build("/language/change").withBoolean("isShowBtnBack",true).navigation();
            }

            @Override
            public void viewUserMore(View v) {
                super.viewUserMore(v);
                ARouter.getInstance().build("/setting/more").navigation();
            }
        });
        userViewModel.getUserInfoMutableLiveData().observe(this, new Observer<UserInfoResponse>() {
            @Override
            public void onChanged(@Nullable UserInfoResponse userInfoResponse) {
                binding.setData(userInfoResponse.getData().get(0));
            }
        });
        userViewModel.loadUserInfo();
    }
}
