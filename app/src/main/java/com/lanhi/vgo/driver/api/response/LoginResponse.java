package com.lanhi.vgo.driver.api.response;

import com.lanhi.vgo.driver.api.response.bean.UserInfoDataBean;

import java.io.Serializable;
import java.util.List;

public class LoginResponse extends BaseResponse {

    private List<UserInfoDataBean> data;

    public List<UserInfoDataBean> getData() {
        return data;
    }

    public void setData(List<UserInfoDataBean> data) {
        this.data = data;
    }
}
