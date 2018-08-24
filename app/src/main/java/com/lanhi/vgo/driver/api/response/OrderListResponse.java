package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class OrderListResponse extends BaseResponse {


    private List<OrderListBean> data;

    public List<OrderListBean> getData() {
        return data;
    }

    public void setData(List<OrderListBean> data) {
        this.data = data;
    }

    public static class OrderListBean {
        /**
         * distribution_fee : 53.92
         * f_stateinfo : 3
         * s_ctiyinfo : 天津市
         * order_state : 1
         * s_addressinfo : 西青区精武镇国兴家园
         * f_ctiyinfo : 4
         * f_addressinfo : 天津市和平区南马路11号
         * s_stateinfo : 天津市
         * order_code : DD201808231728133933
         * shop_name : 白
         * subsidy_fee : null
         */

        private String distribution_fee;
        private String f_stateinfo;
        private String s_ctiyinfo;
        private String order_state;
        private String s_addressinfo;
        private String f_ctiyinfo;
        private String f_addressinfo;
        private String s_stateinfo;
        private String order_code;
        private String shop_name;
        private String subsidy_fee;

        public String getDistribution_fee() {
            return distribution_fee;
        }

        public void setDistribution_fee(String distribution_fee) {
            this.distribution_fee = distribution_fee;
        }

        public String getF_stateinfo() {
            return f_stateinfo;
        }

        public void setF_stateinfo(String f_stateinfo) {
            this.f_stateinfo = f_stateinfo;
        }

        public String getS_ctiyinfo() {
            return s_ctiyinfo;
        }

        public void setS_ctiyinfo(String s_ctiyinfo) {
            this.s_ctiyinfo = s_ctiyinfo;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getS_addressinfo() {
            return s_addressinfo;
        }

        public void setS_addressinfo(String s_addressinfo) {
            this.s_addressinfo = s_addressinfo;
        }

        public String getF_ctiyinfo() {
            return f_ctiyinfo;
        }

        public void setF_ctiyinfo(String f_ctiyinfo) {
            this.f_ctiyinfo = f_ctiyinfo;
        }

        public String getF_addressinfo() {
            return f_addressinfo;
        }

        public void setF_addressinfo(String f_addressinfo) {
            this.f_addressinfo = f_addressinfo;
        }

        public String getS_stateinfo() {
            return s_stateinfo;
        }

        public void setS_stateinfo(String s_stateinfo) {
            this.s_stateinfo = s_stateinfo;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getSubsidy_fee() {
            return subsidy_fee;
        }

        public void setSubsidy_fee(String subsidy_fee) {
            this.subsidy_fee = subsidy_fee;
        }

    }
}
