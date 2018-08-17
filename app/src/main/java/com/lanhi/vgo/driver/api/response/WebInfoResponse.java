package com.lanhi.vgo.driver.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WebInfoResponse extends BaseResponse {


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
         * introduce_info : <p>2221111</p>
         */

        private int id;
        @SerializedName(value = "info", alternate = {"introduce_info", "agreement_info"})
        private String info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
