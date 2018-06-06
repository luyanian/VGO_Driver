package com.lanhi.vgo.driver.common;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.ApiConstants;
import com.lanhi.vgo.driver.api.RSAEncryptor;
import com.lanhi.vgo.driver.api.response.LoginResponse;
import com.lanhi.ryon.utils.mutils.AppUtils;
import com.lanhi.ryon.utils.mutils.PhoneUtils;
import com.lanhi.ryon.utils.mutils.RegexUtils;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.security.PublicKey;

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

    public static String getOrderStateString(String orderid){
        String stateString = "";
        switch (orderid){
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
        }

        return stateString;
    }
    public static String getBtnTextWithOrderState(String orderid){
        String stateString = "";
        switch (orderid){
            case GlobalParams.ORDER_STATE.CANCLE:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.COMPLETE:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.ON_THE_WAY:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_view_detail);
                break;
            case GlobalParams.ORDER_STATE.UNANSWEWD:
                stateString = App.getInstance().getResources().getString(R.string.btn_order_list_publish_cancel);
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

}
