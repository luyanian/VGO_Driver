package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.GetStatesResponse;
import com.lanhi.vgo.driver.mvvm.model.StateCityData;
import com.lanhi.vgo.driver.adapter.StateCityAdapter;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.UserRegisterStep2ActivityBinding;
import com.lanhi.vgo.driver.mvvm.model.UserData;
import com.lanhi.vgo.driver.mvvm.viewmodel.StateCityViewModel;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;
import com.lanhi.ryon.utils.mutils.ActivityPools;
import com.lanhi.ryon.utils.mutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/user/register/step2")
public class RegistStep2Activity extends BaseActivity {
    UserRegisterStep2ActivityBinding binding;
    UserViewModel userViewModel;
    StateCityViewModel stateCityViewModel;
    StateCityAdapter stateAdapter;
    StateCityAdapter cityAdapter;
    private List<StateCityData> stateDataLists = new ArrayList<>();
    private List<StateCityData> cityDataLists = new ArrayList<>();

    private StateCityData currentSelectedStateCityData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_register_step2_activity);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        stateCityViewModel = ViewModelProviders.of(this).get(StateCityViewModel.class);
        stateAdapter = new StateCityAdapter(this);
        cityAdapter = new StateCityAdapter(this);
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

    private void initData() {
        UserData registData = userViewModel.getLiveDate().getValue();
        registData.setPhone(getIntent().getStringExtra(UserData.KEY_PHONE));
        registData.setPassword(getIntent().getStringExtra(UserData.KEY_PASSWORD));
        registData.setPassword2(getIntent().getStringExtra(UserData.KEY_PASSWORD2));
        registData.setSmsCode(getIntent().getStringExtra(UserData.KEY_SMSCODE));
        binding.setData(registData);
        binding.setStateCityViewModel(stateCityViewModel);

        stateCityViewModel.getStateLiveData().observe(this, new Observer<GetStatesResponse>() {
            @Override
            public void onChanged(@Nullable GetStatesResponse statesResponse) {
                stateDataLists.clear();
                stateDataLists.addAll(stateCityViewModel.getStateData());
                StateCityData stateData = new StateCityData("-1","州","-1","000000",StateCityData.STATE);
                stateDataLists.add(0, stateData); //insert a blank item on the top of the list

                stateAdapter.changeData(stateDataLists);
                binding.setStateAdapter(stateAdapter);


                cityDataLists.clear();
                cityDataLists.addAll(stateCityViewModel.getCurrentCitiesData(stateData.getId()));
                StateCityData cityData = new StateCityData("-1","市","-1","000000",StateCityData.CITY);
                cityDataLists.add(0, cityData); //insert a blank item on the top of the list

                cityAdapter.changeData(cityDataLists);
                binding.setCityAdapter(cityAdapter);
            }
        });

        stateCityViewModel.getCurrentConsignorSelectedStateData().observe(this, new Observer<StateCityData>() {
            @Override
            public void onChanged(@Nullable final StateCityData stateCityData) {
                cityDataLists.clear();
                cityDataLists.addAll(stateCityViewModel.getCurrentCitiesData(stateCityData.getId()));
                StateCityData cityData = new StateCityData("-1","市","-1","000000",StateCityData.CITY);
                cityDataLists.add(0, cityData); //insert a blank item on the top of the list
                cityAdapter.changeData(cityDataLists);
                binding.setCityAdapter(cityAdapter);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stateCityViewModel.setSelectCityById("consignor",stateCityData.getId(),stateCityData.getSelecteCityId());
                    }
                },100);

            }
        });

        stateCityViewModel.getCurrentConsignorSelectedCityData().observe(this, new Observer<StateCityData>() {
            @Override
            public void onChanged(@Nullable StateCityData stateCityData) {
                currentSelectedStateCityData = stateCityData;
            }
        });
    }

    private void initEventListener() {
        binding.setListener(new OnEventListener(){
            @Override
            public void regist(View v) {
                super.regist(v);
                userViewModel.regist(currentSelectedStateCityData,new RObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        SPUtils.getInstance(SPConstants.USER.NAME).put(SPConstants.USER.TOKENID,baseResponse.getTokenid());
                        ARouter.getInstance().build("/user/login").navigation();
                        ActivityPools.finishAllExcept(LoginActivity.class);
                    }
                });
            }
        });
    }

    private void initDataChanged() {
        userViewModel.getLiveDate().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(@Nullable UserData registData) {

            }
        });
    }
}
