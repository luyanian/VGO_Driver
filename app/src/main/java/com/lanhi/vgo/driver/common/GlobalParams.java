package com.lanhi.vgo.driver.common;

public class GlobalParams {

    public static final String USER_TYPE="consignor";

    /*短信验证码的使用范围*/
    public static class SCOPE{
        public static final String REGIST="1";
        public static final String FIND_PASSWORD="2";
        public static final String LOGIN_WITH_CODE="3";
    }
    /*订单状态*/
    public static class ORDER_STATE{
        public static final String ALL="0"; //全部订单
        public static final String UNANSWEWD ="1"; //待抢单
        public static final String UNPICKUP="2";//待取货
        public static final String ON_THE_WAY ="3";//配送中
        public static final String COMPLETE ="4";//已完成
        public static final String CANCLE ="5";//已取消
    }

    /*支付方式*/
    public static class PAY_TYPE {
        public static final String UNPAY="0";//未支付	0
        public static final String CASH="1";//现金	1
        public static final String POS="2";//pos刷卡	2
    }
    /*账号状态*/
    public static class USER_STATE{
        public static final String APPLYING ="0";//审核中	0
        public static final String APPLY_FAILD="-1";//审核失败	-1
        public static final String APPLY_SUCCESS="1";//审核成功	1
        public static final String FROZEN="100";//账户冻结	100
    }
    /*登录方式*/
    public static class SIGNIN_TYPE{
        public static final String SIGNIN_WITH_PASSWORD="1";//账号密码登录	1
        public static final String SIGNIN_WITH_VERTIFY_CODE="2";//手机短信验证码登录	2
    }


}
