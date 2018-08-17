package com.lanhi.vgo.driver.mvvm.view.setting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.SettingWebviewBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/setting/webview")
public class WebViewActivity extends BaseActivity{

    UserViewModel userViewModel;
    SettingWebviewBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.setting_webview);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        int flag = getIntent().getIntExtra("flag",FLAG.FLAG_ABOUTME);

        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        if(flag==FLAG.FLAG_ABOUTME){
            binding.titlebar.setTitle(R.string.txt_more_about_me);
            userViewModel.getAboutMeInfo();
        }else if(flag == FLAG.FLAG_AGREENMENT){
            binding.titlebar.setTitle(R.string.txt_more_agreement);
            userViewModel.getAgreenmentInfo();
        }
        WebSettings webSettings = binding.webview.getSettings();
        //支持缩放，默认为true。
        webSettings .setSupportZoom(false);
        //调整图片至适合webview的大小
        webSettings .setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings .setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings .setDefaultTextEncodingName("utf-8");
        //设置自动加载图片
        webSettings .setLoadsImagesAutomatically(true);
        //获取触摸焦点
        binding.webview.requestFocusFromTouch();
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        userViewModel.getAboutAgreenmentInfoMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.webview.loadDataWithBaseURL(null, s, "text/html", "UTF-8", null);
//                binding.webview.loadData(s, "text/html; charset=UTF-8", null);
            }
        });

    }

    public static class FLAG{
        public static final int FLAG_ABOUTME=0x01;
        public static final int FLAG_AGREENMENT=0x02;
    }
}
