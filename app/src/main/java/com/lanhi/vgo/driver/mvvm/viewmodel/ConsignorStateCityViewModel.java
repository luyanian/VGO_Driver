package com.lanhi.vgo.driver.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.lanhi.vgo.driver.adapter.StateCityAdapter;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.response.GetCityResponse;
import com.lanhi.vgo.driver.api.response.GetStateCityResponse;
import com.lanhi.vgo.driver.api.response.GetStatesResponse;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.mvvm.model.StateCityData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cli on 14/11/2016.
 * All Rights Reserved.
 */

public class ConsignorStateCityViewModel extends AndroidViewModel {

    public ObservableField<StateCityData> obvSelectedState = new ObservableField<>();
    public static ObservableField<StateCityData> obvSelectedCity = new ObservableField<>();

    private static SimpleArrayMap<String,GetCityResponse> mapStateCity = new SimpleArrayMap<>();
    private MutableLiveData<GetStatesResponse> stateLiveData = new MutableLiveData<>();
    private static MutableLiveData<GetCityResponse> currentCityLiveData = new MutableLiveData<>();
    private static MutableLiveData<StateCityData> currentSelectedCityData = new MutableLiveData<>();

    public ConsignorStateCityViewModel(@NonNull Application application) {
        super(application);
        StateCityData stateData = new StateCityData("-1","州","-1","000000",StateCityData.STATE);
        StateCityData cityData = new StateCityData("-1","市","-1","000000",StateCityData.CITY);
        obvSelectedState.set(stateData);
        obvSelectedCity.set(cityData);
        loadStates();
    }

    private void loadStates() {
        getStates(new RObserver<GetStatesResponse>() {
            @Override
            public void onSuccess(GetStatesResponse statesResponse) {
                stateLiveData.setValue(statesResponse);
                if(statesResponse.getData()!=null&&statesResponse.getData().size()>0){
                    List<GetStatesResponse.StateData> temp = statesResponse.getData();
                    for(final GetStatesResponse.StateData stateData : temp){
                        getCitys(stateData.getId(), new RObserver<GetCityResponse>() {
                            @Override
                            public void onSuccess(GetCityResponse getCityResponse) {
                                mapStateCity.put(stateData.getId(),getCityResponse);
                            }
                        });
                    }
                }

            }
        });
    }
    private static AdapterView.OnItemSelectedListener onStateSelectedListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            StateCityAdapter stateCityAdapter = (StateCityAdapter) parent.getAdapter();
            if(stateCityAdapter==null){
                return;
            }
            final StateCityData stateCityData = (StateCityData) stateCityAdapter.getItem(position);
            if(stateCityData != null) {
                if("-1".equals(stateCityData.getId())){
                    currentCityLiveData.setValue(new GetCityResponse());
                }else {
                    if(mapStateCity.containsKey(stateCityData.getStateId())&&mapStateCity.get(stateCityData.getStateId())!=null) {
                        currentCityLiveData.setValue(mapStateCity.get(stateCityData.getStateId()));
                    }else{
                        getCitys(stateCityData.getStateId(), new RObserver<GetCityResponse>() {
                            @Override
                            public void onSuccess(GetCityResponse getCityResponse) {
                                mapStateCity.put(stateCityData.getStateId(),getCityResponse);
                                currentCityLiveData.setValue(mapStateCity.get(stateCityData.getStateId()));
                            }
                        });
                    }
                }
                obvSelectedCity.set(getCurrentSelectedCityData().getValue());
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private static AdapterView.OnItemSelectedListener onCitySelectedListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            StateCityAdapter stateCityAdapter = (StateCityAdapter) parent.getAdapter();
            if(stateCityAdapter==null){
                return;
            }
            StateCityData stateCityData = (StateCityData) stateCityAdapter.getItem(position);
            if(stateCityData != null) {
                currentSelectedCityData.setValue(stateCityData);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    public ObservableField<StateCityData> getObservableSelectedState(){
        return obvSelectedState;
    }
    public ObservableField<StateCityData> getObservableSelectedCity(){
        return obvSelectedCity;
    }

    @BindingAdapter(value = {"bind:selectedPlanet", "bind:selectedPlanetAttrChanged"}, requireAll = false)
    public static void bindPlanetSelected(final AppCompatSpinner spinner, StateCityData stateCityData, final InverseBindingListener inverseBindingListener) {
        if (stateCityData != null) {
            if(stateCityData.getType()==StateCityData.STATE) {
                spinner.setOnItemSelectedListener(onStateSelectedListener);
                spinner.setSelection(getStateCityPositionInAdapter((StateCityAdapter) spinner.getAdapter(),stateCityData.getName()));

            }else if(stateCityData.getType()==StateCityData.CITY){
                spinner.setOnItemSelectedListener(onCitySelectedListener);
                spinner.setSelection(getStateCityPositionInAdapter((StateCityAdapter) spinner.getAdapter(),stateCityData.getName()));

            }
        }
    }

    @InverseBindingAdapter(attribute = "bind:selectedPlanet", event = "bind:selectedPlanetAttrChanged")
    public static StateCityData captureSelectedPlanet(AppCompatSpinner spinner) {
        return (StateCityData) spinner.getSelectedItem();
    }
    public void onEditTextChanged(CharSequence s, int start, int before, int count) {
        String keyword = s.toString();
        if(TextUtils.isEmpty(keyword)){
            return;
        }
        Map map = new HashMap();
        map.put("zip_code",keyword);
        ApiRepository.getStateCity(new Gson().toJson(map)).subscribe(new RObserver<GetStateCityResponse>() {
            @Override
            public void onSuccess(GetStateCityResponse getStateCityResponse) {
                if(getStateCityResponse==null||getStateCityResponse.getData()==null||getStateCityResponse.getData().size()<=0){
                    return;
                }
                GetStateCityResponse.StateCity stateCity = getStateCityResponse.getData().get(0);
                StateCityData stateCityData1 = new StateCityData();
                stateCityData1.setType(StateCityData.STATE);
                stateCityData1.setId(stateCity.getState_id());
                stateCityData1.setStateId(stateCity.getState_id());
                stateCityData1.setZipCode(stateCity.getZip_code());
                stateCityData1.setName(stateCity.getState_name());
                obvSelectedState.set(stateCityData1);


                StateCityData stateCityData2 = new StateCityData();
                stateCityData2.setType(StateCityData.CITY);
                stateCityData2.setId(stateCity.getId());
                stateCityData2.setStateId(stateCity.getState_id());
                stateCityData2.setZipCode(stateCity.getZip_code());
                stateCityData2.setName(stateCity.getCity_name());
                currentSelectedCityData.setValue(stateCityData2);
            }
        });
    }


    @BindingConversion
    public static String convertPlanetToString(StateCityData stateCityData) {
        return stateCityData != null? stateCityData.getName() : null;
    }

    private static Integer getStateCityPositionInAdapter(StateCityAdapter adapter, String name) {
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                StateCityData item = (StateCityData) adapter.getItem(i);
                if (item != null && item.getName() != null && item.getName().equals(name)) {
                    return i;
                }
            }
        }
        return 0;
    }






    public MutableLiveData<GetStatesResponse> getStateLiveData(){
        return stateLiveData;
    }
    public MutableLiveData<GetCityResponse> getCurrentCityLiveData(){
        return currentCityLiveData;
    }
    public static MutableLiveData<StateCityData> getCurrentSelectedCityData(){
        return currentSelectedCityData;
    }


    /**
     * 获取州列表
     * @param observer
     */
    private void getStates(RObserver<GetStatesResponse> observer) {
        ApiRepository.getStates().subscribe(observer);
    }

    /**
     * 获取城市列表
     * @param observer
     */
    private static void getCitys(String state_id, RObserver<GetCityResponse> observer) {
        Map map = new HashMap();
        map.put("state_id",state_id);
        String json = new Gson().toJson(map);
        ApiRepository.getCitys(json).subscribe(observer);
    }

    public List<StateCityData> getStateData() {
        List<StateCityData> list = new ArrayList<>();
        List<GetStatesResponse.StateData> temp = stateLiveData.getValue().getData();
        if(temp!=null&&temp.size()>0){
            for(GetStatesResponse.StateData stateData : temp){
                StateCityData stateCityData = new StateCityData();
                stateCityData.setId(stateData.getId());
                stateCityData.setName(stateData.getStateName());
                stateCityData.setZipCode(stateData.getZipCode());
                stateCityData.setStateId(stateData.getId());
                stateCityData.setType(StateCityData.STATE);
                list.add(stateCityData);
            }
        }
        return list;
    }
    public List<StateCityData> getCurrentCityData(){
        List<StateCityData> list = new ArrayList<>();
        if(currentCityLiveData.getValue()==null){
            return list;
        }
        List<GetCityResponse.CityData> temp = currentCityLiveData.getValue().getData();
        if(temp!=null&&temp.size()>0){
            for(GetCityResponse.CityData cityData : temp){
                StateCityData stateCityData = new StateCityData();
                stateCityData.setId(cityData.getId());
                stateCityData.setName(cityData.getCityName());
                stateCityData.setZipCode(cityData.getZipCode());
                stateCityData.setStateId(cityData.getStateId());
                stateCityData.setType(StateCityData.CITY);
                list.add(stateCityData);
            }
        }
        return list;
    }

    public void setSelectStateByName(String consignorState) {
        List<GetStatesResponse.StateData> list = getStateLiveData().getValue().getData();
        for (GetStatesResponse.StateData stateData : list) {
            if (consignorState.equals(stateData.getId())) {
                StateCityData stateCityData = new StateCityData();
                stateCityData.setId(stateData.getId());
                stateCityData.setName(stateData.getStateName());
                stateCityData.setZipCode(stateData.getZipCode());
                stateCityData.setStateId(stateData.getId());
                stateCityData.setType(StateCityData.STATE);
                obvSelectedState.set(stateCityData);
            }
        }
    }

        public void setSelectCityByName(String consignorCity) {
            if(currentCityLiveData.getValue()!=null&&currentCityLiveData.getValue().getData()!=null&&currentCityLiveData.getValue().getData().size()>0){
                List<GetCityResponse.CityData> list = currentCityLiveData.getValue().getData();
                for (GetCityResponse.CityData cityData : list){
                    if(consignorCity.equals(cityData.getId())){
                        StateCityData stateCityData = new StateCityData();
                        stateCityData.setId(cityData.getId());
                        stateCityData.setName(cityData.getCityName());
                        stateCityData.setZipCode(cityData.getZipCode());
                        stateCityData.setStateId(cityData.getStateId());
                        stateCityData.setType(StateCityData.CITY);
                        obvSelectedCity.set(stateCityData);
                    }
                }
            }
        }
}
