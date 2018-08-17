package com.lanhi.vgo.driver.common;

import android.Manifest;
import android.view.View;

import com.lanhi.vgo.driver.api.response.OrderListResponse;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

public class OnEventListener {
    public void login(View v){}
    public void signOut(View v){}
    public void getVerCode(View v){}
    public void vertifySMSCode(View v){};
    public void regist(View v){};
    public void toRegistActivity(View v){};
    public void toForgotPwdActivity(View v){};
    public void resetPassword(View v){};
    public void editPassword(View v,String oldPassword,String newPassword,String newPassword2){};

    public void orderPublish(View v){}
    public void cancelOrderPublish(View v){}
    public void cancelOrder(View v, OrderListResponse.OrderListBean orderListBean){}
    public void viewOrderDetail(View v, OrderListResponse.OrderListBean orderListBean){}
    public void viewUserInfo(View v){}
    public void viewUserShop(View v){}
    public void viewUserAccunt(View v){}
    public void viewUserFinancial(View v){}
    public void viewUserMore(View v){}
    public void viewWebView(View v,int flag){}

    public void viewUpdateShopName(View v,String shopeName){}
    public void updateShopImg(View v){}
    public void shopNameEdit(View v,String shopName){}
    public void userNameEdit(View v,String userName){}
    public void userAccountNumEdit(View v,String checkNum,String routingNum){}
    public void viewUserAccountInfo(View v){}
    public void viewPasswordEdit(View v){}
    public void onclickHotLine(View v,String phone){}
    public void callDelevery(View v,String phone){}
    public void grapOrder(View v, OrderListResponse.OrderListBean orderListBean){}
}
