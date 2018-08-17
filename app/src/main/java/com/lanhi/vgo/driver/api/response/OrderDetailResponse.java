package com.lanhi.vgo.driver.api.response;
import java.util.List;

public class OrderDetailResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * endtime : null
         * f_postal_code : 112
         * order_state : 6
         * s_postal_code : 112
         * f_ctiyinfo : 4
         * remarks : 悲咒
         * s_stateinfo : null
         * paytime : null
         * order_code : DD201805041720241678
         * m_evaluation_grade : null
         * createtime : {"date":4,"day":5,"hours":17,"minutes":20,"month":4,"nanos":0,"seconds":24,"time":1525425624000,"timezoneOffset":-480,"year":118}
         * id : 13
         * s_latitude : 39.0936501
         * s_longitude : 117.1299332
         * s_ctiyinfo : null
         * goods_receipt_tel : 测控
         * f_tel : 18722423376
         * distributor : 11
         * yjqjtime : null
         * subsidy_fee : 1
         * f_stateinfo : 3
         * account_number : 18233132764
         * f_name : 陆
         * goods_receipt_name : 测控
         * merchant : 13
         * order_fee : 10
         * distribution_fee : 11
         * picktime : {"date":21,"day":4,"hours":17,"minutes":4,"month":5,"nanos":0,"seconds":23,"time":1529571863000,"timezoneOffset":-480,"year":118}
         * distribution_list : 配送平凡那
         * s_addressinfo : 南开区学府创新园
         * d_evaluation_grade : 5
         * f_addressinfo : 榕苑路5号留学生创业园
         * yjsdtime : null
         * payment_method : 1
         */

        private TimeBean endtime;
        private String f_postal_code;
        private String order_state;
        private String s_postal_code;
        private String f_ctiyinfo;
        private String remarks;
        private String s_stateinfo;
        private TimeBean paytime;
        private String order_code;
        private Object m_evaluation_grade;
        private TimeBean createtime;
        private String id;
        private String s_latitude;
        private String s_longitude;
        private String s_ctiyinfo;
        private String goods_receipt_tel;
        private String f_tel;
        private String distributor;
        private TimeBean yjqjtime;
        private String subsidy_fee;
        private String f_stateinfo;
        private String account_number;
        private String f_name;
        private String goods_receipt_name;
        private String merchant;
        private String order_fee;
        private String distribution_fee;
        private TimeBean picktime;
        private String distribution_list;
        private String s_addressinfo;
        private int d_evaluation_grade;
        private String f_addressinfo;
        private TimeBean yjsdtime;
        private String payment_method;

        public TimeBean getEndtime() {
            return endtime;
        }

        public void setEndtime(TimeBean endtime) {
            this.endtime = endtime;
        }

        public String getF_postal_code() {
            return f_postal_code;
        }

        public void setF_postal_code(String f_postal_code) {
            this.f_postal_code = f_postal_code;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getS_postal_code() {
            return s_postal_code;
        }

        public void setS_postal_code(String s_postal_code) {
            this.s_postal_code = s_postal_code;
        }

        public String getF_ctiyinfo() {
            return f_ctiyinfo;
        }

        public void setF_ctiyinfo(String f_ctiyinfo) {
            this.f_ctiyinfo = f_ctiyinfo;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getS_stateinfo() {
            return s_stateinfo;
        }

        public void setS_stateinfo(String s_stateinfo) {
            this.s_stateinfo = s_stateinfo;
        }

        public TimeBean getPaytime() {
            return paytime;
        }

        public void setPaytime(TimeBean paytime) {
            this.paytime = paytime;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public Object getM_evaluation_grade() {
            return m_evaluation_grade;
        }

        public void setM_evaluation_grade(Object m_evaluation_grade) {
            this.m_evaluation_grade = m_evaluation_grade;
        }

        public TimeBean getCreatetime() {
            return createtime;
        }

        public void setCreatetime(TimeBean createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getS_latitude() {
            return s_latitude;
        }

        public void setS_latitude(String s_latitude) {
            this.s_latitude = s_latitude;
        }

        public String getS_longitude() {
            return s_longitude;
        }

        public void setS_longitude(String s_longitude) {
            this.s_longitude = s_longitude;
        }

        public String getS_ctiyinfo() {
            return s_ctiyinfo;
        }

        public void setS_ctiyinfo(String s_ctiyinfo) {
            this.s_ctiyinfo = s_ctiyinfo;
        }

        public String getGoods_receipt_tel() {
            return goods_receipt_tel;
        }

        public void setGoods_receipt_tel(String goods_receipt_tel) {
            this.goods_receipt_tel = goods_receipt_tel;
        }

        public String getF_tel() {
            return f_tel;
        }

        public void setF_tel(String f_tel) {
            this.f_tel = f_tel;
        }

        public String getDistributor() {
            return distributor;
        }

        public void setDistributor(String distributor) {
            this.distributor = distributor;
        }

        public TimeBean getYjqjtime() {
            return yjqjtime;
        }

        public void setYjqjtime(TimeBean yjqjtime) {
            this.yjqjtime = yjqjtime;
        }

        public String getSubsidy_fee() {
            return subsidy_fee;
        }

        public void setSubsidy_fee(String subsidy_fee) {
            this.subsidy_fee = subsidy_fee;
        }

        public String getF_stateinfo() {
            return f_stateinfo;
        }

        public void setF_stateinfo(String f_stateinfo) {
            this.f_stateinfo = f_stateinfo;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public String getGoods_receipt_name() {
            return goods_receipt_name;
        }

        public void setGoods_receipt_name(String goods_receipt_name) {
            this.goods_receipt_name = goods_receipt_name;
        }

        public String getMerchant() {
            return merchant;
        }

        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        public String getOrder_fee() {
            return order_fee;
        }

        public void setOrder_fee(String order_fee) {
            this.order_fee = order_fee;
        }

        public String getDistribution_fee() {
            return distribution_fee;
        }

        public void setDistribution_fee(String distribution_fee) {
            this.distribution_fee = distribution_fee;
        }

        public TimeBean getPicktime() {
            return picktime;
        }

        public void setPicktime(TimeBean picktime) {
            this.picktime = picktime;
        }

        public String getDistribution_list() {
            return distribution_list;
        }

        public void setDistribution_list(String distribution_list) {
            this.distribution_list = distribution_list;
        }

        public String getS_addressinfo() {
            return s_addressinfo;
        }

        public void setS_addressinfo(String s_addressinfo) {
            this.s_addressinfo = s_addressinfo;
        }

        public int getD_evaluation_grade() {
            return d_evaluation_grade;
        }

        public void setD_evaluation_grade(int d_evaluation_grade) {
            this.d_evaluation_grade = d_evaluation_grade;
        }

        public String getF_addressinfo() {
            return f_addressinfo;
        }

        public void setF_addressinfo(String f_addressinfo) {
            this.f_addressinfo = f_addressinfo;
        }

        public TimeBean getYjsdtime() {
            return yjsdtime;
        }

        public void setYjsdtime(TimeBean yjsdtime) {
            this.yjsdtime = yjsdtime;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public static class TimeBean {
            /**
             * date : 4
             * day : 5
             * hours : 17
             * minutes : 20
             * month : 4
             * nanos : 0
             * seconds : 24
             * time : 1525425624000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
