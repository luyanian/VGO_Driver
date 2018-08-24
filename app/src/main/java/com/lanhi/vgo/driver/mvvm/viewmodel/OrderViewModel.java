package com.lanhi.vgo.driver.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.LoginResponse;
import com.lanhi.vgo.driver.api.response.OrderDetailResponse;
import com.lanhi.vgo.driver.api.response.OrderListResponse;
import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;
import com.lanhi.vgo.driver.common.Common;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.common.SPKeys;
import com.lanhi.vgo.driver.mvvm.model.OrderData;
import com.lanhi.ryon.utils.mutils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderViewModel extends AndroidViewModel {

    private MutableLiveData<OrderData> orderPublishLiveData = new MutableLiveData<>();
    private MutableLiveData<List<OrderListResponse.OrderListBean>> orderListLiveData = new MutableLiveData<>();
    private MutableLiveData<OrderDetailResponse> orderDetailLiveData = new MutableLiveData<>();
    public OrderViewModel(@NonNull Application application) {
        super(application);
        OrderData orderData = new OrderData();
        UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
        if(userInfoData!=null){
            orderData.setConsignorName(userInfoData.getUser_name());
            orderData.setConsignorAddress(userInfoData.getAddressinfo());
            orderData.setConsignorCity(userInfoData.getCtiyinfo());
            orderData.setConsignorPhone(userInfoData.getAccount_number());
            orderData.setConsignorState(userInfoData.getStateinfo());
            orderData.setConsignorZipCode(userInfoData.getPostal_code());
            orderData.setMerchantid(userInfoData.getId());
        }
        orderPublishLiveData.setValue(orderData);
    }

    public MutableLiveData<OrderData> getOrderPublishLiveData() {
        return orderPublishLiveData;
    }

//    public void orderPublish(RObserver<BaseResponse> rObserver) {
//        OrderData orderData = getOrderPublishLiveData().getValue();
//        Map map = new HashMap();
//        map.put("tokenid", Common.getToken());
//        map.put("recipient",orderData.getRecipientName());
//        map.put("recipientPhone",orderData.getRecipientName());
//        map.put("recipientZipCode",orderData.getRecipientZipCode());
//        map.put("recipientAddress",orderData.getRecipientAddress());
//        map.put("recipientState",orderData.getRecipientState());
//        map.put("recipientCity",orderData.getRecipientCity());
//        map.put("consignor",orderData.getConsignorName());
//        map.put("consignorPhone",orderData.getConsignorPhone());
//        map.put("consignorAddress",orderData.getConsignorAddress());
//        map.put("consignorState",orderData.getConsignorState());
//        map.put("consignorCity",orderData.getConsignorCity());
//        map.put("consignorZipCode",orderData.getConsignorZipCode());
//        map.put("orderDesc",orderData.getOrderDesc());
//        map.put("goodsAmount",orderData.getGoodsAmount());
//        map.put("postageFee",orderData.getPostageFee());
//        map.put("postageTip",orderData.getPostageTip());
//        map.put("remark",orderData.getRemark());
//        map.put("merchantid",orderData.getMerchantid());
//        // TODO: 2018/4/13 发布订单  信息校验
//        ApiRepository.publishOrder(new Gson().toJson(map)).subscribe(rObserver);
//
//    }

    public void clearInputInfo() {
        OrderData orderData = new OrderData();
        UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
        if(userInfoData!=null){
            orderData.setConsignorName(userInfoData.getUser_name());
            orderData.setConsignorAddress(userInfoData.getAddressinfo());
            orderData.setConsignorCity(userInfoData.getCtiyinfo());
            orderData.setConsignorPhone(userInfoData.getAccount_number());
            orderData.setConsignorState(userInfoData.getStateinfo());
            orderData.setConsignorZipCode(userInfoData.getPostal_code());
            orderData.setMerchantid(userInfoData.getId());
            orderPublishLiveData.setValue(orderData);
        }
    }

    public MutableLiveData<List<OrderListResponse.OrderListBean>> getOrderListLiveData(){
        return orderListLiveData;
    }

    public synchronized void loadGrapOrderList(final int pagenum) {
        Map map = new HashMap();
        UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
        if(userInfoData!=null){
            map.put("delivererid",userInfoData.getId());
            map.put("tokenid", Common.getToken());
            map.put("pagenum",pagenum+"");
            map.put("pagecount",10+"");
            ApiRepository.getGrapOrderList(new Gson().toJson(map)).subscribe(new RObserver<OrderListResponse>() {
                @Override
                public void onSuccess(OrderListResponse orderListResponse) {
                    if(orderListResponse.getData()!=null){
                        if(pagenum<=1){
                            orderListLiveData.setValue(orderListResponse.getData());
                        }else {
                            List<OrderListResponse.OrderListBean> temp = orderListLiveData.getValue();
                            temp.addAll(orderListResponse.getData());
                            orderListLiveData.setValue(temp);
                        }
                    }else{
                        List<OrderListResponse.OrderListBean> temp = orderListLiveData.getValue();
                        temp.clear();
                        orderListLiveData.setValue(temp);
                    }
                }
            });
        }
    }

    public synchronized void loadOrderList(final String order_state, final int pagenum) {
        Map map = new HashMap();
        UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
        if(userInfoData!=null){
            map.put("distributorid",userInfoData.getId());
            map.put("order_state",order_state);
            map.put("tokenid", Common.getToken());
            map.put("pagenum",pagenum+"");
            map.put("pagecount",10+"");
            ApiRepository.getOrderList(new Gson().toJson(map)).subscribe(new RObserver<OrderListResponse>() {
                @Override
                public void onSuccess(OrderListResponse orderListResponse) {
                    if(orderListResponse.getData()!=null){
                        if(pagenum<=1){
                            orderListLiveData.setValue(orderListResponse.getData());
                        }else {
                            List<OrderListResponse.OrderListBean> temp = orderListLiveData.getValue();
                            temp.addAll(orderListResponse.getData());
                            orderListLiveData.setValue(temp);
                        }
                    }else{
                        List<OrderListResponse.OrderListBean> temp = orderListLiveData.getValue();
                        temp.clear();
                        orderListLiveData.setValue(temp);
                    }
                }
            });
        }
    }
    //抢单
    public void grapOrder(OrderListResponse.OrderListBean orderListBean,RObserver<BaseResponse> rObserver) {
        if(orderListBean==null){
            return;
        }
        UserInfoDataBean userInfoData = (UserInfoDataBean) SPUtils.getInstance().readObject(SPKeys.USER_INFO);
        if(userInfoData==null){
            return;
        }
        Map map = new HashMap();
        map.put("tokenid", Common.getToken());
        map.put("order_code",orderListBean.getOrder_code());
        map.put("distributorid",userInfoData.getId());
        ApiRepository.grapOrder(new Gson().toJson(map)).subscribe(rObserver);

    }

    public void getOrderDetail(String order_code) {
        Map map = new HashMap();
        map.put("tokenid", Common.getToken());
        map.put("order_code",order_code);
        ApiRepository.getOrderDetail(new Gson().toJson(map)).subscribe(new RObserver<OrderDetailResponse>() {
            @Override
            public void onSuccess(OrderDetailResponse orderDetailResponse) {
                orderDetailLiveData.setValue(orderDetailResponse);
            }
        });
    }

    public MutableLiveData<OrderDetailResponse> getOrderDetailLiveData(){
        return orderDetailLiveData;
    }
}
