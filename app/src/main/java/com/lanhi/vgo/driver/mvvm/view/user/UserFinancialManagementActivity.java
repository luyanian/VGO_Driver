package com.lanhi.vgo.driver.mvvm.view.user;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.ryon.utils.mutils.ToastUtils;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.adapter.UserBillsAdapter;
import com.lanhi.vgo.driver.api.response.UserBillsResponse;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.UserFinancialManagementActivityBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.UserViewModel;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

import org.w3c.dom.Text;

@Route(path = "/user/financial")
public class UserFinancialManagementActivity extends BaseActivity {
    private UserFinancialManagementActivityBinding binding;
    private UserViewModel userViewModel;
    private UserBillsAdapter userBillsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_financial_management_activity);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        userBillsAdapter = new UserBillsAdapter(this);
        binding.recyclerView.setAdapter(userBillsAdapter);
        userViewModel.getUserBills(new RObserver() {
            @Override
            public void onSuccess(Object o) {
                UserBillsResponse userBillsResponse = (UserBillsResponse) o;
                if(userBillsResponse!=null&&userBillsResponse.getData()!=null&&userBillsResponse.getData().getBill_detail()!=null){
                    binding.tvBalance.setText(userBillsResponse.getData().getAccountbalance()+"$");
                    if(TextUtils.isEmpty(userBillsResponse.getData().getBalancebalance())){
                        binding.tvBalanceBalance.setText(getResources().getString(R.string.txt_user_bills_balance_balance) + "0.0$");
                    }else {
                        binding.tvBalanceBalance.setText(getResources().getString(R.string.txt_user_bills_balance_balance) + userBillsResponse.getData().getBalancebalance() + "$");
                    }
                    if(TextUtils.isEmpty(userBillsResponse.getData().getPresentbalance())){
                        binding.tvPresentBalance.setText(getResources().getString(R.string.txt_user_bills_present_balance) +"0.0$");
                    }else {
                        binding.tvPresentBalance.setText(getResources().getString(R.string.txt_user_bills_present_balance) + userBillsResponse.getData().getPresentbalance() + "$");
                    }
                    userBillsAdapter.addDataList(userBillsResponse.getData().getBill_detail());
                }
            }
        });
    }
    public void rechage(View v){
        ToastUtils.showShort("rechage");
    }

    public void withdrawCash(View v){
        ToastUtils.showShort("withdrawCash");
    }
}
