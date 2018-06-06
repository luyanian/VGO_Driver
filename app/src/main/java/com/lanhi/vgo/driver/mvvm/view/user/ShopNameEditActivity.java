package com.lanhi.vgo.driver.mvvm.view.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.databinding.UserShopNameEditActivityBinding;
import com.lanhi.vgo.driver.mvvm.model.UserData;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;
@Route(path = "/user/shop/name/edit")
public class ShopNameEditActivity extends BaseActivity {
    private UserShopNameEditActivityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_shop_name_edit_activity);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        UserData userData = new UserData();
        String shopeName = getIntent().getStringExtra("shopeName");
        userData.setShopName(shopeName);
        binding.setData(userData);
        binding.setEvent(new OnEventListener(){
            @Override
            public void shopNameEdit(View v, String shopName) {
                super.shopNameEdit(v, shopName);
                // TODO: 2018/5/10  
            }
        });
    }
}
