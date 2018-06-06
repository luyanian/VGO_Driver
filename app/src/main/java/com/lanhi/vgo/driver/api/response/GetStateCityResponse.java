package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class GetStateCityResponse extends BaseResponse {

    private List<StateCity> data;

    public List<StateCity> getData() {
        return data;
    }

    public void setData(List<StateCity> data) {
        this.data = data;
    }

    public static class StateCity {
        /**
         * id : 1
         * state_id : 1
         * city_name : 测试城市1
         * zip_code : 111
         * state_name : 测试州1
         */

        private String id;
        private String state_id;
        private String city_name;
        private String zip_code;
        private String state_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
    }
}
