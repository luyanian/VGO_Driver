package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class GetCityResponse extends BaseResponse {

    private List<CityData> data;

    public List<CityData> getData() {
        return data;
    }

    public void setData(List<CityData> data) {
        this.data = data;
    }

    public static class CityData {
        /**
         * cityName : 测试城市1
         * id : 1
         * stateId : 1
         * zipCode : 111
         */

        private String cityName;
        private String id;
        private String stateId;
        private String zipCode;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
