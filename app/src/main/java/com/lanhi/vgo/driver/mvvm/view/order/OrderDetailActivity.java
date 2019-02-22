package com.lanhi.vgo.driver.mvvm.view.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.ryon.utils.mutils.PhoneUtils;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.ryon.utils.mutils.ToastUtils;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.ApiRepository;
import com.lanhi.vgo.driver.api.response.AccessTokenResponse;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.OrderDetailResponse;
import com.lanhi.vgo.driver.common.GlobalParams;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.OrderDetailActivityBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.OrderViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;
import com.paypal.paypalretailsdk.AppInfo;
import com.paypal.paypalretailsdk.Merchant;
import com.paypal.paypalretailsdk.RetailSDK;
import com.paypal.paypalretailsdk.RetailSDKException;
import com.paypal.paypalretailsdk.SdkCredential;

import java.util.Set;

import io.reactivex.functions.Consumer;

@Route(path = "/order/detail")
public class OrderDetailActivity extends BaseActivity {
    private OrderDetailActivityBinding binding;
    private OrderViewModel orderViewModel;
    private String order_code;
    private OrderDetailResponse orderDetailResponse;
    private boolean isSandbox = true;
    private static final String SANDBOX_OAUTH = "http://pph-retail-sdk-sample.herokuapp.com/toPayPal/sandbox?returnTokenOnQueryString=true";
//    private static final String SANDBOX_OAUTH = "https://www.sandbox.paypal.com/signin/authorize?client_id=AVOfZFfzQbtWcdKFwr7FEHG-JKxJuQnT-uZnO1yl7FGjhUoUD2nPYZ-1yVsU6QHtFVh9tEz2G2iIy15_&response_type=code&scope=profile+email+address+phone+https://uri.paypal.com/services/paypalattributes+https://uri.paypal.com/services/invoicing&redirect_uri=http://admin.which-vgo.com/pay.html";
    private static final String LIVE_OAUTH = "https://www.paypal.com/signin/authorize";
    private String origin ="";
    private String destination="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.order_detail_activity);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }

            @Override
            public void onRightTextButton(View view) {
                super.onRightTextButton(view);
                if(TextUtils.isEmpty(origin)||TextUtils.isEmpty(destination)){
                    ToastUtils.showShort("地址解析失败");
                }
                String url = "https://www.google.com/maps/dir/?api=1&origin="+origin+"&destination="+destination+"&travelmode=driving";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        order_code = getIntent().getStringExtra("order_code");
        orderViewModel.getOrderDetailLiveData().observe(this, new Observer<OrderDetailResponse>() {
            @Override
            public void onChanged(@Nullable OrderDetailResponse orderDetailResponse) {
                OrderDetailActivity.this.orderDetailResponse = orderDetailResponse;
                if(orderDetailResponse!=null&&orderDetailResponse.getData()!=null&&orderDetailResponse.getData().size()>0&&orderDetailResponse.getData().get(0)!=null){
                    OrderDetailResponse.DataBean dataBean = orderDetailResponse.getData().get(0);
                    binding.setData(dataBean);
                    String lat = SPUtils.getInstance(SPConstants.LOCATION.NAME).getString(SPConstants.LOCATION.LAT);
                    String lng = SPUtils.getInstance(SPConstants.LOCATION.NAME).getString(SPConstants.LOCATION.LNG);
                    origin = lat+","+lng;
                    if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(dataBean.getOrder_code())
                            ||GlobalParams.ORDER_STATE.UNPICKUP.equals(dataBean.getOrder_code())){
                        destination = dataBean.getF_stateinfo()+dataBean.getF_ctiyinfo()+dataBean.getF_addressinfo();
                    }else{
                        destination = dataBean.getS_stateinfo()+dataBean.getS_ctiyinfo()+dataBean.getS_addressinfo();
                    }
                }
            }
        });
        binding.setListener(new OnEventListener(){
            @Override
            public void callPhone(View v, String phone) {
                super.callPhone(v, phone);
                if(!TextUtils.isEmpty(phone)){
                    PhoneUtils.dial(phone);
                }
            }
            @Override
            public void changeOrderState(View v, OrderDetailResponse.DataBean dataBean) {
                super.changeOrderState(v, dataBean);
                if(dataBean!=null) {
                    if(GlobalParams.ORDER_STATE.UNPICKUP.equals(dataBean.getOrder_state())) {
                        //执行取货业务
                        String latitude = SPUtils.getInstance(SPConstants.LOCATION.NAME).getString(SPConstants.LOCATION.LAT);
                        String longitude = SPUtils.getInstance(SPConstants.LOCATION.NAME).getString(SPConstants.LOCATION.LNG);
                        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
                            return;
                        }
                        orderViewModel.pickUpOrder(dataBean.getOrder_code(), latitude, longitude, new RObserver<BaseResponse>() {
                            @Override
                            public void onSuccess(BaseResponse baseResponse) {
                            orderViewModel.getOrderDetail(order_code);
                            ToastUtils.showShort(R.string.msg_option_successful);
                            }
                        });
                    }else if(GlobalParams.ORDER_STATE.ON_THE_WAY.equals(dataBean.getOrder_state())){
                        initSDK();
                    }
                }
            }
        });
        orderViewModel.getOrderDetail(order_code);
    }
    private void getSandboxAccessToken(){
//        String sandboxClientId = "AVOfZFfzQbtWcdKFwr7FEHG-JKxJuQnT-uZnO1yl7FGjhUoUD2nPYZ-1yVsU6QHtFVh9tEz2G2iIy15_";
//        String sandBoxSecret = "EEQBkrXO7-Q0BBlcsNy0hFLvc6Nk_PwXfrCeDQVk1zwCWbtcScMPoR-ZiS9xSSE5ntmGy6U07VLseywR";
        ApiRepository.getSandboxAccessToken().subscribe(new Consumer<AccessTokenResponse>() {
            @Override
            public void accept(AccessTokenResponse accessTokenResponse) throws Exception {
                LogUtils.d(accessTokenResponse.toString());
                SdkCredential credential = new SdkCredential("sandbox", accessTokenResponse.getAccess_token());
//                String refresh_url = "";
//                credential.setTokenRefreshCredentials(refresh_url);
                initializeMerchant(credential);
            }
        });

    }
    private void getLiveAccessToken(){
//        String liveClientId = "AUXrU2_1kCwZr36aFLmp4GLwVYOIRyfODUryfENZm-X925SysUzVBMRMryaKoAZ0urcuT4kiM3qh1vPs";
//        String liveSecret = "EP6kjkqa7Ysh7zZCive91mi47g2LcfAl0W5svO0CKKV0PoR9hlmRmVHwJXrHM-DOyhRJSqLL6pDeZZ_t";
        ApiRepository.getLiveAccessToken().subscribe(new Consumer<AccessTokenResponse>() {
            @Override
            public void accept(AccessTokenResponse accessTokenResponse) throws Exception {
                LogUtils.d(accessTokenResponse.toString());
                SdkCredential credential = new SdkCredential("live", accessTokenResponse.getAccess_token());
//                String refresh_url = "";
//                credential.setTokenRefreshCredentials(refresh_url);
                initializeMerchant(credential);
            }
        });

    }



    private void initSDK(){
        try {
            AppInfo info = new AppInfo("VGO Driver","v1.0","1");
            RetailSDK.initialize(getApplicationContext(), new RetailSDK.AppState() {
                @Override
                public Activity getCurrentActivity()
                {
                    return OrderDetailActivity.this;
                }


                @Override
                public boolean getIsTabletMode()
                {
                    return false;
                }
            }, info);
            /**
             * Add this observer to handle insecure network errors from the sdk
             */
            RetailSDK.addUntrustedNetworkObserver(new RetailSDK.UntrusterNetworkObserver() {
                @Override
                public void untrustedNetwork(RetailSDKException error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                            builder.setMessage("Insecure network. Please join a secure network and open the app again")
                                    .setCancelable(true)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                }
            });
            initAccessToken();
        } catch (RetailSDKException e) {
            e.printStackTrace();
        }
    }

    private void initAccessToken(){
//        if(isSandbox) {
//            String access_token = SPUtils.getInstance("paypalStore").getString("sandboxAccessToken");
//            String refresh_url = SPUtils.getInstance("paypalStore").getString("sandboxAccessRefreshUrl");
//            String env = SPUtils.getInstance("paypalStore").getString("sandboxAccessEnv");
//            if(TextUtils.isEmpty(access_token)||TextUtils.isEmpty(refresh_url)||TextUtils.isEmpty(env)){
//                startWebView(SANDBOX_OAUTH, isSandbox);
//            }else{
//                SdkCredential credential = new SdkCredential(env, access_token);
//                credential.setTokenRefreshCredentials(refresh_url);
//                initializeMerchant(credential);
//            }
//
//        }else{
//            String access_token = SPUtils.getInstance("paypalStore").getString("liveAccessToken");
//            String refresh_url = SPUtils.getInstance("paypalStore").getString("liveAccessRefreshUrl");
//            String env = SPUtils.getInstance("paypalStore").getString("liveAccessEnv");
//            if(TextUtils.isEmpty(access_token)||TextUtils.isEmpty(refresh_url)||TextUtils.isEmpty(env)){
//                startWebView(LIVE_OAUTH, isSandbox);
//            }else{
//                SdkCredential credential = new SdkCredential(env, access_token);
//                credential.setTokenRefreshCredentials(refresh_url);
//                initializeMerchant(credential);
//            }
//        }
        if(isSandbox){
            getSandboxAccessToken();
        }else{
            getLiveAccessToken();
        }
    }

    private void startWebView(String url, final boolean isSandBox){
        LogUtils.d("startWebView url: " + url + " isSandbox: " + isSandBox );
        final WebView webView = (WebView) findViewById(R.id.id_webView);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus(View.FOCUS_DOWN);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.d("shouldOverrideURLLoading: url: " + url);
                //String returnStringCheckParam = "retailsdksampleapp://oauth?sdk_token=";
                String returnStringCheckParam = "retailsdksampleapp://oauth?access_token=";

                // List<NameValuePair> parameters = URLEncodedUtils.parse(new URI(url));
                Uri uri = Uri.parse(url);
                Set<String> paramNames = uri.getQueryParameterNames();
                for (String key: paramNames) {
                    String value = uri.getQueryParameter(key);
                    LogUtils.d("shouldOverrideURLLoading: name: " + key + " value: " + value);
                }

                if (null != url && url.startsWith(returnStringCheckParam)) {
                    if (paramNames.contains("access_token") && paramNames.contains("refresh_url") && paramNames.contains("env")) {
                        String access_token = uri.getQueryParameter("access_token");
                        String refresh_url = uri.getQueryParameter("refresh_url");
                        String env = uri.getQueryParameter("env");
                        LogUtils.d("shouldOverrideURLLoading: access_token: " + access_token);
                        LogUtils.d("shouldOverrideURLLoading: refresh_url: " + refresh_url);
                        LogUtils.d("shouldOverrideURLLoading: env: " + env);
                        SdkCredential credential = new SdkCredential(env, access_token);
                        credential.setTokenRefreshCredentials(refresh_url);
                        //String compositeToken = url.substring(returnStringCheckParam.length());
                        //Log.d(LOG_TAG, "shouldOverrideURLLoading compositeToken: " + compositeToken);
                        if (isSandBox){
                            //LocalPreferences.storeSandboxMidTierToken(LoginActivity.this, compositeToken);
                            //startPaymentOptionsActivity(compositeToken, PayPalHereSDK.Sandbox);
                            //initializeMerchant(compositeToken, SW_REPOSITORY);
                            SPUtils.getInstance(SPConstants.PAYPAL.NAME).put(SPConstants.PAYPAL.SANDBOX_ACCESS_TOKEN,access_token);
                            SPUtils.getInstance(SPConstants.PAYPAL.NAME).put(SPConstants.PAYPAL.SANDBOX_ACCESS_REFRESH_URL,refresh_url);
                            SPUtils.getInstance(SPConstants.PAYPAL.NAME).put(SPConstants.PAYPAL.SANDBOX_ACCESS_ENV,env);
                            initializeMerchant(credential);
                        }else{
                            SPUtils.getInstance(SPConstants.PAYPAL.NAME).put(SPConstants.PAYPAL.LIVE_ACCESS_TOKEN,access_token);
                            SPUtils.getInstance(SPConstants.PAYPAL.NAME).put(SPConstants.PAYPAL.LIVE_ACCESS_REFRESH_URL,refresh_url);
                            SPUtils.getInstance(SPConstants.PAYPAL.NAME).put(SPConstants.PAYPAL.LIVE_ACCESS_ENV,env);
                            //LocalPreferences.storeLiveMidTierToken(LoginActivity.this, compositeToken);
                            // startPaymentOptionsActivity(compositeToken, PayPalHereSDK.Live);
                            initializeMerchant(credential);
                        }
                        webView.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }
        });
        webView.loadUrl(url);
    }
    private void initializeMerchant(SdkCredential credential){
        try {
            RetailSDK.initializeMerchant(credential, new RetailSDK.MerchantInitializedCallback() {
                @Override
                public void merchantInitialized(final RetailSDKException error, Merchant merchant) {
                    if (error != null) {
                        // handle error situation and try to re-initialize
                        SPUtils.getInstance(SPConstants.PAYPAL.NAME).clear();
//                        initAccessToken();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.d("RetailSDK initialize on Error:" + error.toString());
                                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                                builder.setTitle("Error");
                                builder.setMessage(error.getMessage());
                                builder.setCancelable(false);
                                builder.setNeutralButton("OK", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        LogUtils.d("RetailSDK Initialize error AlertDialog onClick");
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        });

                    } else {
                        // merchant initialization success - continue on to card reader connection
                        // code to search and connect
                        merchant.setReferrerCode("VGO_Driver");
                        Intent intent = new Intent(OrderDetailActivity.this,ChargeActivity.class);
                        startActivity(intent);
//                        RetailSDK.getDeviceManager().searchAndConnect(new DeviceManager.ConnectionCallback(){
//                            @Override
//                            public void connection(final RetailSDKException error, final PaymentDevice cardReader){
//                                if (error == null && cardReader != null){
//                                    // Successfully connected to card reader
//                                    // if connected reader is a BT reader, check for software update
//                                    RetailSDK.DeviceDiscoveredObserver discoveredObserver = new RetailSDK.DeviceDiscoveredObserver(){
//                                        @Override
//                                        public void deviceDiscovered(PaymentDevice device){
//                                            PaymentDevice.UpdateRequiredObserver updateRequiredObserver = new PaymentDevice.UpdateRequiredObserver(){
//                                                @Override
//                                                public void updateRequired(DeviceUpdate update){
//                                                    update.offer(new DeviceUpdate.CompletedCallback(){
//                                                        @Override
//                                                        public void completed(RetailSDKException error, Boolean deviceUpgraded){
//                                                            if (error != null){
//                                                                // Update was successful
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            };
//                                            device.addUpdateRequiredObserver(updateRequiredObserver);
//                                        }
//                                    };
//                                    RetailSDK.addDeviceDiscoveredObserver(discoveredObserver);
//
//                                    //执行paypal-here支付业务
//                                    Invoice invoice = new Invoice(null);
//                                    invoice.addItem("Item", new BigDecimal(1), new BigDecimal(1), 1, null);
//
//
//                                    RetailSDK.getTransactionManager().createTransaction(invoice, new TransactionManager.TransactionCallback()
//                                    {
//                                        @Override
//                                        public void transaction(RetailSDKException error, TransactionContext context){
//                                            context.setCompletedHandler(new TransactionContext.TransactionCompletedCallback(){
//                                                @Override
//                                                public void transactionCompleted(RetailSDKException error, TransactionRecord record){
//    //                                                                    OrderDetailActivity.this.transactionCompleted(error, record);
//                                                    ToastUtils.showShort(record.toString());
//                                                }
//                                            });
//
//                                            TransactionBeginOptions options = new TransactionBeginOptions();
//                                            options.setShowPromptInCardReader(true);
//                                            options.setShowPromptInApp(false);
//                                            options.setIsAuthCapture(false);
//                                            options.setAmountBasedTipping(false);
//    //                                                            options.setQuickChipEnabled(false);
//                                            options.setTippingOnReaderEnabled(false);
//                                            context.beginPayment(options);
//                                        }
//                                    });
//                                }
//                                else if (error != null){
//                                    // Connection to card reader failed
//                                }
//                            }
//                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
