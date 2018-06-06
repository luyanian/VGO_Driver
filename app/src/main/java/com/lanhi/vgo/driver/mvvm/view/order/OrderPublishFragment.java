package com.lanhi.vgo.driver.mvvm.view.order;

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
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.adapter.StateCityAdapter;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.GetCityResponse;
import com.lanhi.vgo.driver.api.response.GetStatesResponse;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.OrderPublishFragmentBinding;
import com.lanhi.vgo.driver.mvvm.model.OrderData;
import com.lanhi.vgo.driver.mvvm.model.StateCityData;
import com.lanhi.vgo.driver.mvvm.view.MainActivity;
import com.lanhi.vgo.driver.mvvm.view.user.RegistStep2Activity;
import com.lanhi.vgo.driver.mvvm.viewmodel.ConsignorStateCityViewModel;
import com.lanhi.vgo.driver.mvvm.viewmodel.OrderViewModel;
import com.lanhi.vgo.driver.mvvm.viewmodel.StateCityViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderPublishFragment extends Fragment {
    OrderPublishFragmentBinding binding;
    OrderViewModel orderViewModel;
    StateCityViewModel recipentStateCityViewModel;
    ConsignorStateCityViewModel consignorStateCityViewModel;
    private StateCityData currentSelectedStateCityData;
    private List<StateCityData> stateDataLists = new ArrayList<>();
    private List<StateCityData> cityDataLists = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.order_publish_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        recipentStateCityViewModel = ViewModelProviders.of(this).get(StateCityViewModel.class);
        consignorStateCityViewModel = ViewModelProviders.of(this).get(ConsignorStateCityViewModel.class);
        initData();
        initEventListener();
    }

    private void initData() {
        final OrderData orderData = orderViewModel.getOrderPublishLiveData().getValue();
        binding.setData(orderData);
        binding.setRecipentStateCityViewModel(recipentStateCityViewModel);
        recipentStateCityViewModel.getStateLiveData().observe(this, new Observer<GetStatesResponse>() {
            @Override
            public void onChanged(@Nullable GetStatesResponse statesResponse) {
                stateDataLists.clear();
                StateCityAdapter stateCityAdapter = new StateCityAdapter(getActivity());
                stateDataLists.addAll(recipentStateCityViewModel.getStateData());
                StateCityData stateCityData = new StateCityData("-1","州","-1","000000",StateCityData.STATE);
                stateDataLists.add(0, stateCityData); //insert a blank item on the top of the list
                stateCityAdapter.changeData(stateDataLists);
                binding.setStateAdapter(stateCityAdapter);

            }
        });
        recipentStateCityViewModel.getCurrentCityLiveData().observe(this, new Observer<GetCityResponse>() {
            @Override
            public void onChanged(@Nullable GetCityResponse getCityResponse) {
                cityDataLists.clear();
                StateCityAdapter stateCityAdapter = new StateCityAdapter(getActivity());
                cityDataLists.addAll(recipentStateCityViewModel.getCurrentCityData());
                StateCityData stateCityData = new StateCityData("-1","市","-1","000000",StateCityData.CITY);
                cityDataLists.add(0, stateCityData); //insert a blank item on the top of the list
                stateCityAdapter.changeData(cityDataLists);
                binding.setCityAdapter(stateCityAdapter);

            }
        });
        recipentStateCityViewModel.getCurrentSelectedCityData().observe(this, new Observer<StateCityData>() {
            @Override
            public void onChanged(@Nullable StateCityData stateCityData) {
                currentSelectedStateCityData = stateCityData;
            }
        });
        binding.setConsignorStateCityViewModel(consignorStateCityViewModel);
        consignorStateCityViewModel.getStateLiveData().observe(this, new Observer<GetStatesResponse>() {
            @Override
            public void onChanged(@Nullable GetStatesResponse statesResponse) {
                stateDataLists.clear();
                StateCityAdapter stateCityAdapter = new StateCityAdapter(getActivity());
                stateDataLists.addAll(consignorStateCityViewModel.getStateData());
                StateCityData stateCityData = new StateCityData("-1","州","-1","000000",StateCityData.STATE);
                stateDataLists.add(0, stateCityData); //insert a blank item on the top of the list
                stateCityAdapter.changeData(stateDataLists);
                binding.setStateAdapter(stateCityAdapter);
                consignorStateCityViewModel.setSelectStateByName(orderData.getConsignorState());
            }
        });
        consignorStateCityViewModel.getCurrentCityLiveData().observe(this, new Observer<GetCityResponse>() {
            @Override
            public void onChanged(@Nullable GetCityResponse getCityResponse) {
                cityDataLists.clear();
                StateCityAdapter stateCityAdapter = new StateCityAdapter(getActivity());
                cityDataLists.addAll(consignorStateCityViewModel.getCurrentCityData());
                StateCityData stateCityData = new StateCityData("-1","市","-1","000000",StateCityData.CITY);
                cityDataLists.add(0, stateCityData); //insert a blank item on the top of the list
                stateCityAdapter.changeData(cityDataLists);
                binding.setCityAdapter(stateCityAdapter);
//                consignorStateCityViewModel.setSelectCityByName(orderData.getConsignorCity());
            }
        });
        consignorStateCityViewModel.getCurrentSelectedCityData().observe(this, new Observer<StateCityData>() {
            @Override
            public void onChanged(@Nullable StateCityData stateCityData) {
                currentSelectedStateCityData = stateCityData;
            }
        });
    }

    private void initEventListener() {
        binding.setListener(new OnEventListener(){
            @Override
            public void orderPublish(View v) {
                super.orderPublish(v);
                orderViewModel.orderPublish(new RObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        orderViewModel.clearInputInfo();
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if(mainActivity!=null){
                            mainActivity.changeMenu(1);
                        }
                    }
                });
            }

            @Override
            public void cancelOrderPublish(View v) {
                super.cancelOrderPublish(v);
                orderViewModel.clearInputInfo();
            }
        });
    }

}
