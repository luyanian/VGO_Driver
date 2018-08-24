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
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.lanhi.vgo.driver.R;
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

public class StateCityViewModel extends AndroidViewModel {

    public ObservableField<StateCityData> obvSelectedConsignorState = new ObservableField<>();
    public static ObservableField<StateCityData> obvSelectedConsignorCity = new ObservableField<>();

    private static SimpleArrayMap<String,GetCityResponse> mapStateCity = new SimpleArrayMap<>();
    private MutableLiveData<GetStatesResponse> stateLiveData = new MutableLiveData<>();
    private static MutableLiveData<StateCityData> currentConsignorSelectedStateData = new MutableLiveData<>();
    private static MutableLiveData<StateCityData> currentConsignorSelectedCityData = new MutableLiveData<>();
    private static MutableLiveData<String> consignorAddressData = new MutableLiveData<>();
    private static MutableLiveData<String> reciptAddressData = new MutableLiveData<>();

    public ObservableField<StateCityData> obvSelectedReciptState = new ObservableField<>();
    public static ObservableField<StateCityData> obvSelectedReciptCity = new ObservableField<>();
    private static MutableLiveData<StateCityData> currentReciptSelectedStateData = new MutableLiveData<>();
    private static MutableLiveData<StateCityData> currentReciptSelectedCityData = new MutableLiveData<>();

    public StateCityViewModel(@NonNull Application application) {
        super(application);
        loadStates();
    }

    private void loadStates() {
        getStates(new RObserver<GetStatesResponse>() {
            @Override
            public void onSuccess(final GetStatesResponse statesResponse) {
                if(statesResponse.getData()!=null&&statesResponse.getData().size()>0){
                    final List<GetStatesResponse.StateData> temp = statesResponse.getData();
                    for (int i = 0; i < temp.size(); i++) {
                        final GetStatesResponse.StateData stateData = temp.get(i);
                        if(i==temp.size()-1) {
                            getCitys(stateData.getId(), new RObserver<GetCityResponse>() {
                                @Override
                                public void onSuccess(GetCityResponse getCityResponse) {
                                    mapStateCity.put(stateData.getId(), getCityResponse);
                                    stateLiveData.setValue(statesResponse);
                                }
                            });
                        }else{
                            getCitys(stateData.getId(), new RObserver<GetCityResponse>() {
                                @Override
                                public void onSuccess(GetCityResponse getCityResponse) {
                                    mapStateCity.put(stateData.getId(), getCityResponse);
                                }
                            });
                        }
                    }

                }

            }
        });
    }
    public ObservableField<StateCityData> getObservableSelectedConsignorState(){
        return obvSelectedConsignorState;
    }
    public ObservableField<StateCityData> getObservableSelectedConsignorCity(){
        return obvSelectedConsignorCity;
    }
    public ObservableField<StateCityData> getObservableSelectedReciptState(){
        return obvSelectedReciptState;
    }
    public ObservableField<StateCityData> getObservableSelectedReciptCity(){
        return obvSelectedReciptCity;
    }
    public MutableLiveData<GetStatesResponse> getStateLiveData(){
        return stateLiveData;
    }
    public MutableLiveData<StateCityData> getCurrentConsignorSelectedStateData(){
        return currentConsignorSelectedStateData;
    }
    public static MutableLiveData<StateCityData> getCurrentConsignorSelectedCityData(){
        return currentConsignorSelectedCityData;
    }
    public MutableLiveData<StateCityData> getCurrentReciptSelectedStateData(){
        return currentReciptSelectedStateData;
    }
    public static MutableLiveData<StateCityData> getCurrentReciptSelectedCityData(){
        return currentReciptSelectedCityData;
    }
    public static MutableLiveData<String> getConsignorAddressData(){
        return consignorAddressData;
    }
    public static MutableLiveData<String> getReciptAddressData(){
        return reciptAddressData;
    }

    private static AdapterView.OnItemSelectedListener onConsignorStateSelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            StateCityAdapter stateCityAdapter = (StateCityAdapter) parent.getAdapter();
            if(stateCityAdapter==null){
                return;
            }
            final StateCityData stateCityData = (StateCityData) stateCityAdapter.getItem(position);
            if(stateCityData != null) {
                currentConsignorSelectedStateData.setValue(stateCityData);
                if(!"-1".equals(stateCityData.getId())){
                    if(!mapStateCity.containsKey(stateCityData.getStateId())||mapStateCity.get(stateCityData.getStateId())==null) {
                        getCitys(stateCityData.getStateId(), new RObserver<GetCityResponse>() {
                            @Override
                            public void onSuccess(GetCityResponse getCityResponse) {
                                mapStateCity.put(stateCityData.getStateId(),getCityResponse);
                            }
                        });
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private static AdapterView.OnItemSelectedListener onConsignorCitySelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            StateCityAdapter stateCityAdapter = (StateCityAdapter) parent.getAdapter();
            if(stateCityAdapter==null){
                return;
            }
            final StateCityData stateCityData = (StateCityData) stateCityAdapter.getItem(position);
            if(stateCityData != null) {
                currentConsignorSelectedCityData.setValue(stateCityData);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private static AdapterView.OnItemSelectedListener onReciptStateSelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            StateCityAdapter stateCityAdapter = (StateCityAdapter) parent.getAdapter();
            if(stateCityAdapter==null){
                return;
            }
            final StateCityData stateCityData = (StateCityData) stateCityAdapter.getItem(position);
            if(stateCityData != null) {
                currentReciptSelectedStateData.setValue(stateCityData);
                if(!"-1".equals(stateCityData.getId())){
                    if(!mapStateCity.containsKey(stateCityData.getStateId())||mapStateCity.get(stateCityData.getStateId())==null) {
                        getCitys(stateCityData.getStateId(), new RObserver<GetCityResponse>() {
                            @Override
                            public void onSuccess(GetCityResponse getCityResponse) {
                                mapStateCity.put(stateCityData.getStateId(),getCityResponse);
                            }
                        });
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private static AdapterView.OnItemSelectedListener onReciptCitySelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            StateCityAdapter stateCityAdapter = (StateCityAdapter) parent.getAdapter();
            if(stateCityAdapter==null){
                return;
            }
            final StateCityData stateCityData = (StateCityData) stateCityAdapter.getItem(position);
            if(stateCityData != null) {
                    currentReciptSelectedCityData.setValue(stateCityData);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    @BindingAdapter(value = {"bind:selectedPlanet", "bind:selectedPlanetAttrChanged"}, requireAll = false)
    public static void bindPlanetSelected(final AppCompatSpinner spinner, StateCityData stateCityData, final InverseBindingListener inverseBindingListener) {
        if(spinner.getId()==R.id.spiner_consignor_state){
            spinner.setOnItemSelectedListener(onConsignorStateSelectedListener);
            spinner.setSelection(getStateCityPositionInAdapter((StateCityAdapter) spinner.getAdapter(),stateCityData));
        }else if(spinner.getId()==R.id.spiner_consignor_city){
            spinner.setOnItemSelectedListener(onConsignorCitySelectedListener);
            spinner.setSelection(getStateCityPositionInAdapter((StateCityAdapter) spinner.getAdapter(),stateCityData));
        }
//        else if(spinner.getId()== R.id.spiner_recipt_state){
//            spinner.setOnItemSelectedListener(onReciptStateSelectedListener);
//            spinner.setSelection(getStateCityPositionInAdapter((StateCityAdapter) spinner.getAdapter(),stateCityData));
//        }else if(spinner.getId()==R.id.spiner_recipt_city){
//            spinner.setOnItemSelectedListener(onReciptCitySelectedListener);
//            spinner.setSelection(getStateCityPositionInAdapter((StateCityAdapter) spinner.getAdapter(),stateCityData));
//        }
    }

    @InverseBindingAdapter(attribute = "bind:selectedPlanet", event = "bind:selectedPlanetAttrChanged")
    public static StateCityData captureSelectedPlanet(AppCompatSpinner spinner) {
        return (StateCityData) spinner.getSelectedItem();
    }

    public void onConsignorAddressChanged(Editable editable) {
        consignorAddressData.setValue(editable.toString());
    }
    public void onReciptAddressChanged(Editable editable) {
        reciptAddressData.setValue(editable.toString());
    }

    public void onConsignorZipcodTextChanged(CharSequence s, int start, int before, int count) {
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

                StateCityData stateCityData2 = new StateCityData();
                stateCityData2.setType(StateCityData.CITY);
                stateCityData2.setId(stateCity.getId());
                stateCityData2.setStateId(stateCity.getState_id());
                stateCityData2.setZipCode(stateCity.getZip_code());
                stateCityData2.setName(stateCity.getCity_name());
                stateCityData1.setSelecteCityId(stateCityData2.getId());

                obvSelectedConsignorState.set(stateCityData1);
                currentConsignorSelectedStateData.setValue(stateCityData1);
                currentConsignorSelectedCityData.setValue(stateCityData2);
            }
        });
    }

    public void onReciptZipcodTextChanged(CharSequence s, int start, int before, int count) {
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

                StateCityData stateCityData2 = new StateCityData();
                stateCityData2.setType(StateCityData.CITY);
                stateCityData2.setId(stateCity.getId());
                stateCityData2.setStateId(stateCity.getState_id());
                stateCityData2.setZipCode(stateCity.getZip_code());
                stateCityData2.setName(stateCity.getCity_name());
                stateCityData1.setSelecteCityId(stateCityData2.getId());

                obvSelectedReciptState.set(stateCityData1);
                currentReciptSelectedStateData.setValue(stateCityData1);
                currentReciptSelectedCityData.setValue(stateCityData2);
            }
        });
    }


    @BindingConversion
    public static String convertPlanetToString(StateCityData stateCityData) {
        return stateCityData != null? stateCityData.getName() : null;
    }

    private static Integer getStateCityPositionInAdapter(StateCityAdapter adapter, StateCityData stateCityData) {
        if (adapter != null&&stateCityData!=null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                StateCityData item = (StateCityData) adapter.getItem(i);
                if (item != null && item.getName() != null && item.getName().equals(stateCityData.getName())) {
                    return i;
                }
            }
        }
        return 0;
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
                stateCityData.setSelecteCityId("-1");
                stateCityData.setType(StateCityData.STATE);
                list.add(stateCityData);
            }
        }
        return list;
    }
    public List<StateCityData> getCurrentCitiesData(final String stateId){
        final List<StateCityData> list = new ArrayList<>();
        if("-1".equals(stateId)){
            return list;
        }
        if(!mapStateCity.containsKey(stateId)||mapStateCity.get(stateId)==null) {
            getCitys(stateId, new RObserver<GetCityResponse>() {
                @Override
                public void onSuccess(GetCityResponse getCityResponse) {
                    mapStateCity.put(stateId,getCityResponse);
                    List<GetCityResponse.CityData> temp = getCityResponse.getData();
                    if (temp != null && temp.size() > 0) {
                        for (GetCityResponse.CityData cityData : temp) {
                            StateCityData stateCityData = new StateCityData();
                            stateCityData.setId(cityData.getId());
                            stateCityData.setName(cityData.getCityName());
                            stateCityData.setZipCode(cityData.getZipCode());
                            stateCityData.setStateId(cityData.getStateId());
                            stateCityData.setType(StateCityData.CITY);
                            list.add(stateCityData);
                        }
                    }
                }
            });
        }else {
            List<GetCityResponse.CityData> temp = mapStateCity.get(stateId).getData();
            if (temp != null && temp.size() > 0) {
                for (GetCityResponse.CityData cityData : temp) {
                    StateCityData stateCityData = new StateCityData();
                    stateCityData.setId(cityData.getId());
                    stateCityData.setName(cityData.getCityName());
                    stateCityData.setZipCode(cityData.getZipCode());
                    stateCityData.setStateId(cityData.getStateId());
                    stateCityData.setType(StateCityData.CITY);
                    list.add(stateCityData);
                }
            }
        }
        return list;
    }
    public void setSelectCityById(String type,String stateId,String cityId) {
        if(TextUtils.isEmpty(stateId)){
            stateId="-1";
        }
        if(TextUtils.isEmpty(cityId)){
            cityId="-1";
            obvSelectedReciptCity.set(null);
        }else {
            if (mapStateCity.get(stateId)!=null&&mapStateCity.get(stateId).getData() != null && mapStateCity.get(stateId).getData().size() > 0) {
                List<GetCityResponse.CityData> temp = mapStateCity.get(stateId).getData();
                for (GetCityResponse.CityData cityData : temp) {
                    if (cityData.getId().equals(cityId)) {
                        StateCityData stateCityData = new StateCityData();
                        stateCityData.setId(cityData.getId());
                        stateCityData.setName(cityData.getCityName());
                        stateCityData.setZipCode(cityData.getZipCode());
                        stateCityData.setStateId(cityData.getStateId());
                        stateCityData.setType(StateCityData.CITY);
                        if ("consignor".equals(type)) {
                            obvSelectedConsignorCity.set(stateCityData);
                        } else if ("recipt".equals(type)) {
                            obvSelectedReciptCity.set(stateCityData);
                        }
                    }
                }
            }
        }
    }

    public void setSelectStateById(String type,String stateId,String selectCityId) {
        List<GetStatesResponse.StateData> list = getStateLiveData().getValue().getData();
        for (GetStatesResponse.StateData stateData : list) {
            if (stateId.equals(stateData.getId())) {
                StateCityData stateCityData = new StateCityData();
                stateCityData.setId(stateData.getId());
                stateCityData.setName(stateData.getStateName());
                stateCityData.setZipCode(stateData.getZipCode());
                stateCityData.setStateId(stateData.getId());
                stateCityData.setType(StateCityData.STATE);
                stateCityData.setSelecteCityId(selectCityId);
                if("consignor".equals(type)) {
                    obvSelectedConsignorState.set(stateCityData);
                    currentConsignorSelectedStateData.setValue(stateCityData);
                }else if("recipt".equals(type)){
                    obvSelectedReciptState.set(stateCityData);
                    currentReciptSelectedStateData.setValue(stateCityData);
                }
            }
        }
    }
}
