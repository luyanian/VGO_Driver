package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class DistanceFeeResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * additional_fee : 4
         * base_fee : 1.12
         */

        private int id;
        private double additional_fee;
        private double base_fee;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getAdditional_fee() {
            return additional_fee;
        }

        public void setAdditional_fee(double additional_fee) {
            this.additional_fee = additional_fee;
        }

        public double getBase_fee() {
            return base_fee;
        }

        public void setBase_fee(double base_fee) {
            this.base_fee = base_fee;
        }
    }
}
