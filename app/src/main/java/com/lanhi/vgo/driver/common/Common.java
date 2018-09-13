package com.lanhi.vgo.driver.common;

import android.text.TextUtils;
import android.view.View;
import com.lanhi.ryon.utils.mutils.TimeUtils;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.RSAEncryptor;
import com.lanhi.ryon.utils.mutils.RegexUtils;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.vgo.driver.api.response.DistanceMatrixResponse;
import com.lanhi.vgo.driver.api.response.OrderDetailResponse;
import com.orhanobut.logger.Logger;
import java.util.Date;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Common {
    public static String rsaEncrypt(String json){
        try {
//            String str = new String(json.getBytes(),"utf-8");
            RSAEncryptor rsaEncryptor = new RSAEncryptor();
            String testRSAEnWith64 = rsaEncryptor.encode(json);
            Logger.d(json +" \n rsa encrypt : "+testRSAEnWith64);
            return testRSAEnWith64;
        }catch (Exception e){
            Logger.e(e.getMessage());
            return "";
        }
//        return "ShdLmpg+vOwkCGKmp2E8gvpnrtDko2mmBheOVE1t9NK1REf120JIseusDCMdMp7IhxEptmpLPY/p6T/LoYJ+AJhye5fkYxjWKJV31OqJ+I9426L7w8yfo91wS76AyVmVUPG4HR3UVXlG6jHudFUJqGxpsk0NaDzuKnGVEOqTe50=";
    }
    public static String rsaDecrypt(String text){
        try {
            RSAEncryptor rsaEncryptor = new RSAEncryptor();
            String testRSADeWith64 = rsaEncryptor.decode(text);
            Logger.d(text +" \n rsa decrypt : "+testRSADeWith64);
            return testRSADeWith64;
        }catch (Exception e){
            Logger.e(e.getMessage());
            return "";
        }
    }
    public static String getToken(){
        return SPUtils.getInstance().getString(SPKeys.TOKENID);
    }

    public static String getOrderStateString(String state){
        String stateString = "";
        if(TextUtils.isEmpty(state)){
            return stateString;
        }
        switch (state){
            case GlobalParams.ORDER_STATE.CANCLE:
                stateString = App.getInstance().getResources().getString(R.string.order_state_cancle);
                break;
            case GlobalParams.ORDER_STATE.COMPLETE:
                stateString = App.getInstance().getResources().getString(R.string.order_state_complete);
                break;
            case GlobalParams.ORDER_STATE.ON_THE_WAY:
                stateString = App.getInstance().getResources().getString(R.string.order_state_on_the_way);
                break;
            case GlobalParams.ORDER_STATE.UNANSWEWD:
                stateString = App.getInstance().getResources().getString(R.string.order_state_unanswewd);
                break;
            case GlobalParams.ORDER_STATE.UNPICKUP:
                stateString = App.getInstance().getResources().getString(R.string.order_state_unpickup);
                break;
            case GlobalParams.ORDER_STATE.UNEVALATED:
                stateString = App.getInstance().getResources().getString(R.string.order_state_unevaluate);
                break;
        }

        return stateString;
    }
    public static String getBtnTextWithOrderState(String state){
        String stateString = "";
        if(TextUtils.isEmpty(state)){
            return stateString;
        }
        switch (state){
            case GlobalParams.ORDER_STATE.CANCLE:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.UNEVALATED:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.COMPLETE:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.ON_THE_WAY:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.UNANSWEWD:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_publish_grap);
                break;
            case GlobalParams.ORDER_STATE.UNPICKUP:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            default:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
        }

        return stateString;
    }

    public static String getSecurityPhoneNum(String phone){
        if(TextUtils.isEmpty(phone)){
            return "";
        }else {
            return RegexUtils.getSecurityPhoneNum(phone);
        }
    }
    //配送员名称是否显示
    public static int isOrderDriverNameViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)){
            return View.GONE;
        }else{
            return View.VISIBLE;
        }
    }
    //订单费用是否显示
    public static int isOrderFeeViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)||GlobalParams.ORDER_STATE.UNPICKUP.equals(state)){
            return View.GONE;
        }else{
            return View.VISIBLE;
        }
    }
    //收货人，联系方式，地址 是否显示
    public static int isReceiverViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)||GlobalParams.ORDER_STATE.UNPICKUP.equals(state)){
            return View.GONE;
        }else{
            return View.VISIBLE;
        }
    }
    //地址显示信息格式化
    public static String getAddress(String address,String city,String state){
        return address+","+city+","+state;
    }
    //预计取货时间view是否显示
    public static int getOrderEstimatePikeViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNPICKUP.equals(state)){
            return View.VISIBLE;
        }
        return View.GONE;
    }
    //取货时间view是否显示
    public static int getOrderPickTimeViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)||GlobalParams.ORDER_STATE.UNPICKUP.equals(state)){
            return View.GONE;
        }
        return View.VISIBLE;
    }
    //预计送达货时间view是否显示
    public static int getOrderEstimateArriveViewVisible(String state){
        if(GlobalParams.ORDER_STATE.ON_THE_WAY.equals(state)){
            return View.VISIBLE;
        }
        return View.GONE;
    }
    //送达时间view是否显示
    public static int getOrderArriveTimeViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)
                ||GlobalParams.ORDER_STATE.UNPICKUP.equals(state)
                ||GlobalParams.ORDER_STATE.ON_THE_WAY.equals(state)){
            return View.GONE;
        }
        return View.VISIBLE;
    }
    //支付方式是否显示
    public static int getPayTypeViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)
                ||GlobalParams.ORDER_STATE.UNPICKUP.equals(state)
                ||GlobalParams.ORDER_STATE.ON_THE_WAY.equals(state)){
            return View.GONE;
        }
        return View.VISIBLE;
    }
    //送达时间view是否显示
    public static int getEvaluateViewVisible(String state){
        if(GlobalParams.ORDER_STATE.COMPLETE.equals(state)){
            return View.VISIBLE;
        }
        return View.GONE;
    }
    //联系配送员是否显示
    public static int getDeliveryViewVisible(String state){
        if(GlobalParams.ORDER_STATE.UNPICKUP.equals(state)
                ||GlobalParams.ORDER_STATE.ON_THE_WAY.equals(state)){
            return View.VISIBLE;
        }
        return View.GONE;
    }
    //支付方式
    public static String getPayTypeString(String state){
        String payType = "";
        if(GlobalParams.PAY_TYPE.CASH.equals(state)){
            payType = App.getInstance().getResources().getString(R.string.txt_order_pay_with_cash);
        }else if(GlobalParams.PAY_TYPE.POS.equals(state)){
            payType = App.getInstance().getResources().getString(R.string.txt_order_pay_with_pos);
        }else{
            payType = App.getInstance().getResources().getString(R.string.txt_order_unpay);
        }
        return payType;
    }
    //格式化时间显示信息
    public static String formatDateTime(Date data){
        String dataTime = "";
        if(data!=null){
            dataTime = TimeUtils.date2String(data);
        }
        return dataTime;
    }
    //格式化时间
    public static String formatDateTime(OrderDetailResponse.DataBean.TimeBean data){
        String dataTime = "";
        if(data!=null){
            dataTime = TimeUtils.millis2String(data.getTime());
        }
        return dataTime;
    }

    //根据订单状态显示通知内容
    public static String getNotifyOrderContent(String state){
        if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(state)){
            return "Receive the new order push, click to quickly grab the order!";
        }else if(GlobalParams.ORDER_STATE.UNPICKUP.equals(state)){
            return "There's a driver for you,click to see the details.";
        }else if(GlobalParams.ORDER_STATE.ON_THE_WAY.equals(state)){
            return "The driver takes the goods,click to see the details.";
        }else if(GlobalParams.ORDER_STATE.UNEVALATED.equals(state)){
            return "The goods have been delivered,click to see the details.";
        }
        return "Click to see the details.";
    }

    //根据提现状态显示通知内容
    public static String getNotifyCashContent(String state){
        if("1".equals(state)){
            return "Your cash application has been passed,click to see the details.";
        }else if("0".equals(state)){
            return "Your cash application has not been approved,click to see the reason.";
        }
        return "Click to see the details.";
    }

    //google map  计算两地之间的距离
    public static void getDistance(String from,String to,Consumer<DistanceMatrixResponse> consumer){
        String url ="https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                +from
                +"&destinations="
                +to
                +"&departure_time=now&key=AIzaSyBzeTTg-Jm3WrfOzjeezHQHwhBcnZejzYI";
        ApiRepository.getDistanceMetrix(url).subscribe(consumer);
    }

}