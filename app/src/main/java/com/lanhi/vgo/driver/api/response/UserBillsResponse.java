package com.lanhi.vgo.driver.api.response;

import java.util.List;

public class UserBillsResponse extends BaseResponse {

    /**
     * data : {"presentbalance":"0.0","accountbalance":"0.0","bill_detail":[{"accountName":"","createTime":"2019-01-16 00:00:00","distributionFee":0,"id":70,"orderCode":"DD201901151659066974","orderFee":203.06,"orderType":"7","payEmail":"","payOrderCode":"","realIncome":203.06,"rechargeState":"","rechargeTime":"","serviceFee":0,"settlementState":"1","settlementTime":"2019-01-16 00:00:00","shopName":"","subsidyFee":0,"userName":"","userType":"deliverer","userid":6}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * presentbalance : 0.0
         * accountbalance : 0.0
         * bill_detail : [{"accountName":"","createTime":"2019-01-16 00:00:00","distributionFee":0,"id":70,"orderCode":"DD201901151659066974","orderFee":203.06,"orderType":"7","payEmail":"","payOrderCode":"","realIncome":203.06,"rechargeState":"","rechargeTime":"","serviceFee":0,"settlementState":"1","settlementTime":"2019-01-16 00:00:00","shopName":"","subsidyFee":0,"userName":"","userType":"deliverer","userid":6}]
         */

        private String presentbalance;
        private String accountbalance;
        private String balancebalance;
        private List<BillDetailBean> bill_detail;

        public String getPresentbalance() {
            return presentbalance;
        }

        public void setPresentbalance(String presentbalance) {
            this.presentbalance = presentbalance;
        }

        public String getAccountbalance() {
            return accountbalance;
        }

        public void setAccountbalance(String accountbalance) {
            this.accountbalance = accountbalance;
        }

        public String getBalancebalance() {
            return balancebalance;
        }

        public void setBalancebalance(String balancebalance) {
            this.balancebalance = balancebalance;
        }

        public List<BillDetailBean> getBill_detail() {
            return bill_detail;
        }

        public void setBill_detail(List<BillDetailBean> bill_detail) {
            this.bill_detail = bill_detail;
        }

        public static class BillDetailBean {
            /**
             * accountName :
             * createTime : 2019-01-16 00:00:00
             * distributionFee : 0
             * id : 70
             * orderCode : DD201901151659066974
             * orderFee : 203.06
             * orderType : 7
             * payEmail :
             * payOrderCode :
             * realIncome : 203.06
             * rechargeState :
             * rechargeTime :
             * serviceFee : 0
             * settlementState : 1
             * settlementTime : 2019-01-16 00:00:00
             * shopName :
             * subsidyFee : 0
             * userName :
             * userType : deliverer
             * userid : 6
             */

            private String accountName;
            private String createTime;
            private double distributionFee;
            private int id;
            private String orderCode;
            private double orderFee;
            private String orderType;
            private String payEmail;
            private String payOrderCode;
            private double realIncome;
            private String rechargeState;
            private String rechargeTime;
            private double serviceFee;
            private String settlementState;
            private String settlementTime;
            private String shopName;
            private double subsidyFee;
            private String userName;
            private String userType;
            private int userid;

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public double getDistributionFee() {
                return distributionFee;
            }

            public void setDistributionFee(int distributionFee) {
                this.distributionFee = distributionFee;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public double getOrderFee() {
                return orderFee;
            }

            public void setOrderFee(double orderFee) {
                this.orderFee = orderFee;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getPayEmail() {
                return payEmail;
            }

            public void setPayEmail(String payEmail) {
                this.payEmail = payEmail;
            }

            public String getPayOrderCode() {
                return payOrderCode;
            }

            public void setPayOrderCode(String payOrderCode) {
                this.payOrderCode = payOrderCode;
            }

            public double getRealIncome() {
                return realIncome;
            }

            public void setRealIncome(double realIncome) {
                this.realIncome = realIncome;
            }

            public String getRechargeState() {
                return rechargeState;
            }

            public void setRechargeState(String rechargeState) {
                this.rechargeState = rechargeState;
            }

            public String getRechargeTime() {
                return rechargeTime;
            }

            public void setRechargeTime(String rechargeTime) {
                this.rechargeTime = rechargeTime;
            }

            public double getServiceFee() {
                return serviceFee;
            }

            public void setServiceFee(double serviceFee) {
                this.serviceFee = serviceFee;
            }

            public String getSettlementState() {
                return settlementState;
            }

            public void setSettlementState(String settlementState) {
                this.settlementState = settlementState;
            }

            public String getSettlementTime() {
                return settlementTime;
            }

            public void setSettlementTime(String settlementTime) {
                this.settlementTime = settlementTime;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public double getSubsidyFee() {
                return subsidyFee;
            }

            public void setSubsidyFee(double subsidyFee) {
                this.subsidyFee = subsidyFee;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }
        }
    }
}
