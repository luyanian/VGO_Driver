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
         * f_ctiyinfo : 1
         * distribution_fee : 15
         * f_stateinfo : 1
         * f_addressinfo : 八角
         * order_state : 1
         * order_code : DD201804171653005704
         * subsidy_fee : 3
         */

        private String f_ctiyinfo;
        private String distribution_fee;
        private String f_stateinfo;
        private String f_addressinfo;
        private String order_state;
        private String order_code;
        private String subsidy_fee;

        public String getF_ctiyinfo() {
            return f_ctiyinfo;
        }

        public void setF_ctiyinfo(String f_ctiyinfo) {
            this.f_ctiyinfo = f_ctiyinfo;
        }

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

        public String getF_addressinfo() {
            return f_addressinfo;
        }

        public void setF_addressinfo(String f_addressinfo) {
            this.f_addressinfo = f_addressinfo;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getSubsidy_fee() {
            return subsidy_fee;
        }

        public void setSubsidy_fee(String subsidy_fee) {
            this.subsidy_fee = subsidy_fee;
        }
    }
}
