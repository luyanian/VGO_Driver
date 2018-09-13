package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class DistanceMatrixResponse extends BaseResponse {

    /**
     * destination_addresses : ["中国天津市西青区"]
     * origin_addresses : ["中国天津市南开区红旗南路观园公寓甲1"]
     * rows : [{"elements":[{"distance":{"text":"18.2 公里","value":18250},"duration":{"text":"32分钟","value":1942},"status":"OK"}]}]
     * status : OK
     */

    private String status;
    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<RowsBean> rows;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(List<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private List<ElementsBean> elements;

        public List<ElementsBean> getElements() {
            return elements;
        }

        public void setElements(List<ElementsBean> elements) {
            this.elements = elements;
        }

        public static class ElementsBean {
            /**
             * distance : {"text":"18.2 公里","value":18250}
             * duration : {"text":"32分钟","value":1942}
             * status : OK
             */

            private DistanceBean distance;
            private DurationBean duration;
            private String status;

            public DistanceBean getDistance() {
                return distance;
            }

            public void setDistance(DistanceBean distance) {
                this.distance = distance;
            }

            public DurationBean getDuration() {
                return duration;
            }

            public void setDuration(DurationBean duration) {
                this.duration = duration;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public static class DistanceBean {
                /**
                 * text : 18.2 公里
                 * value : 18250
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class DurationBean {
                /**
                 * text : 32分钟
                 * value : 1942
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }
        }
    }
}
