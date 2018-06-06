package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class GetStatesResponse extends BaseResponse {

    private List<StateData> data;

    public List<StateData> getData() {
        return data;
    }

    public void setData(List<StateData> data) {
        this.data = data;
    }

    public static class StateData {
        /**
         * id : 1
         * stateName : 测试州1
         * zipCode : 123
         */

        private String id;
        private String stateName;
        private String zipCode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
