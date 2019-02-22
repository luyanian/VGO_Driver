package com.lanhi.vgo.driver.api;

import com.lanhi.vgo.driver.api.response.AccessTokenResponse;
import com.lanhi.vgo.driver.api.response.DistanceFeeResponse;
import com.lanhi.vgo.driver.api.response.DistanceMatrixResponse;
import com.lanhi.vgo.driver.api.response.ServiceScopeResponse;
import com.lanhi.vgo.driver.api.response.UserBillsResponse;
import com.lanhi.vgo.driver.api.response.WebInfoResponse;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.GetCityResponse;
import com.lanhi.vgo.driver.api.response.GetStateCityResponse;
import com.lanhi.vgo.driver.api.response.GetStatesResponse;
import com.lanhi.vgo.driver.api.response.GetVertificationResponse;
import com.lanhi.vgo.driver.api.response.HotlineResponse;
import com.lanhi.vgo.driver.api.response.LoginResponse;
import com.lanhi.vgo.driver.api.response.OrderDetailResponse;
import com.lanhi.vgo.driver.api.response.OrderListResponse;
import com.lanhi.vgo.driver.api.response.UploadFileResponse;
import com.lanhi.vgo.driver.api.response.UserInfoResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiService{

    @FormUrlEncoded
    @POST("appinterface/login.shtml")
    Observable<String> test(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/login.shtml")
    Observable<LoginResponse> login(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getverification.shtml")
    Observable<GetVertificationResponse> getVerification(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/register.shtml")
    Observable<BaseResponse> regist(@Field("str") String str);

    @POST("appinterface/getstate.shtml")
    Observable<GetStatesResponse> getStates();

    @FormUrlEncoded
    @POST("appinterface/getcity.shtml")
    Observable<GetCityResponse> getCity(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getstatecity.shtml")
    Observable<GetStateCityResponse> getStateCity(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/initpass.shtml")
    Observable<BaseResponse> resetPassword(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/updateuserinfo.shtml")
    Observable<BaseResponse> updateUserInfo(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/sendorders.shtml")
    Observable<BaseResponse> publishOrder(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/orderslist.shtml")
    Observable<OrderListResponse> getGrapOderList(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/orderslist_p.shtml")
    Observable<OrderListResponse> getOderList(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/grab_single.shtml")
    Observable<BaseResponse> grapOrder(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/orderinfo.shtml")
    Observable<OrderDetailResponse> getOrderDetail(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/pick_up.shtml")
    Observable<BaseResponse> pickUpOrder(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getuserinfo.shtml")
    Observable<UserInfoResponse> getUserInfo(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/update_username.shtml")
    Observable<BaseResponse> editUserName(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/update_user_num.shtml")
    Observable<BaseResponse> editUserAccountNum(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/user_update_pass.shtml")
    Observable<BaseResponse> editUserPassword(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/gethotline.shtml")
    Observable<HotlineResponse> getHotline(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getapp_introduce.shtml")
    Observable<WebInfoResponse> getAboutMeInfo(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getagreement.shtml")
    Observable<WebInfoResponse> getAgreenmentInfo(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/gps.shtml")
    Observable<BaseResponse> updateLatLng(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/editUserAppToken.shtml")
    Observable<BaseResponse> getUpdateFMCToken(@Field("str") String str);


    @Headers({
            "Accept: application/json",
    })
    @Multipart
    @POST("appinterface/upload_user_img.shtml")
    Observable<UploadFileResponse> uploadUserImg(@Part MultipartBody.Part myFile, @Part MultipartBody.Part tokenid, @Part MultipartBody.Part userid);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Accept:application/json",
            "Accept-Language:en_US",
            "Authorization:Basic QVZPZlpGZnpRYnRXY2RLRndyN0ZFSEctSkt4SnVRblQtdVpuTzF5bDdGR2poVW9VRDJuUFlaLTF5VnNVNlFIdEZWaDl0RXoyRzJpSXkxNV86RUVRQmtyWE83LVEwQkJsY3NOeTBoRkx2YzZOa19Qd1hmckNlRFFWazF6d0NXYnRjU2NNUG9SLVppUzl4U1NFNW50bUd5NlUwN1ZMc2V5d1I=",
    })
    @FormUrlEncoded
    @POST("https://api.sandbox.paypal.com/v1/oauth2/token")
    Observable<AccessTokenResponse> getSandboxAccessToken(@Field("grant_type") String grantType);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Accept:application/json",
            "Accept-Language:en_US",
            "Authorization:Basic QVVYclUyXzFrQ3dacjM2YUZMbXA0R0x3VllPSVJ5Zk9EVXJ5ZkVOWm0tWDkyNVN5c1V6VkJNUk1yeWFLb0FaMHVyY3VUNGtpTTNxaDF2UHM6RVA2a2prcWE3WXNoN3paQ2l2ZTkxbWk0N2cyTGNmQWwwVzVzdk8wQ0tLVjBQb1I5aGxtUm1WSHdKWHJITS1ET3loUkpTcUxMNnBEZVpaX3Q=",
    })
    @FormUrlEncoded
    @POST("https://api.sandbox.paypal.com/v1/oauth2/token")
    Observable<AccessTokenResponse> getLiveAccessToken(@Field("grant_type") String grantType);

    @Headers({
            "Accept: application/json",
    })
    @Multipart
    @POST("appinterface/upload_shop_img.shtml")
    Observable<UploadFileResponse> uploadShopImg(@Part MultipartBody.Part myFile, @Part MultipartBody.Part tokenid, @Part MultipartBody.Part userid);

    @GET()
    Observable<DistanceMatrixResponse> getDistanceMetrix(@Url String str);

    @FormUrlEncoded
    @POST("appinterface/distribution_fee.shtml")
    Observable<DistanceFeeResponse> getDistanceFee(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/update_min_average.shtml")
    Observable<ServiceScopeResponse> updateServiceScope(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getmin_average.shtml")
    Observable<ServiceScopeResponse> getServiceScope(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/getAverage.shtml")
    Observable<ServiceScopeResponse> getUserScore(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/appinterface/bEvaluateList_d.shtml")
    Observable<ServiceScopeResponse> getUserScoreRecord(@Field("str") String str);

    @FormUrlEncoded
    @POST("appinterface/my_bill.shtml")
    Observable<UserBillsResponse> getUserBills(@Field("str") String str);
}