package com.lanhi.vgo.driver.mvvm.model;

public class UserData {
    public final static String KEY_PHONE="phone";
    public final static String KEY_SMSCODE="smscode";
    public final static String KEY_PASSWORD="password";
    public final static String KEY_PASSWORD2="password2";
    private String phone;
    private String smsCode;
    private String oldPassword;
    private String password;
    private String password2;
    private String userName;
    private String shopName;
    private String shopHeaderImageUrl;
    private String zipCode;
    private String address;
    private String state;
    private String city;
    private String email;
    private String checkNum;
    private String routingNum;
    private String taxNum;
    private String refereeName;
    private String refereeTel;
    private int currentItem;
    private String msgTip;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopHeaderImageUrl() {
        return shopHeaderImageUrl;
    }

    public void setShopHeaderImageUrl(String shopHeaderImageUrl) {
        this.shopHeaderImageUrl = shopHeaderImageUrl;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getRoutingNum() {
        return routingNum;
    }

    public void setRoutingNum(String routingNum) {
        this.routingNum = routingNum;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public String getRefereeTel() {
        return refereeTel;
    }

    public void setRefereeTel(String refereeTel) {
        this.refereeTel = refereeTel;
    }

    public int getCurrentItem() {
        return currentItem;
    }
    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;

    }

    public String getMsgTip() {
        return msgTip;
    }

    public void setMsgTip(String msgTip) {
        this.msgTip = msgTip;
    }
}
