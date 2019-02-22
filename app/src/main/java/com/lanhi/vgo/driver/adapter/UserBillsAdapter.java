package com.lanhi.vgo.driver.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.UserBillsResponse;
import com.lanhi.vgo.driver.databinding.UserFinancialBillsItem1Binding;

import java.util.ArrayList;
import java.util.List;

public class UserBillsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<UserBillsResponse.DataBean.BillDetailBean> bills = new ArrayList<>();

    public UserBillsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void cleanAllData(){
        bills.clear();
        notifyDataSetChanged();
    }

    public void addDataList(List<UserBillsResponse.DataBean.BillDetailBean> bills){
        this.bills.addAll(bills);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserFinancialBillsItem1Binding userFinancialBillsItem1Binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.user_financial_bills_item1,parent,false);
        return new ViewHolder(userFinancialBillsItem1Binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserFinancialBillsItem1Binding userFinancialBillsItem1Binding = DataBindingUtil.getBinding(holder.itemView);
        UserBillsResponse.DataBean.BillDetailBean billDetailBean = bills.get(position);
        //2=推荐奖励 3=充值 8=提现 6=订单金额收入
        if("2".equals(billDetailBean.getOrderType())){
            userFinancialBillsItem1Binding.llRow1.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow2.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow3.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.llRow4.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.llRow5.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.tvLine3.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.tvRow1.setText(mContext.getResources().getString(R.string.txt_user_bills_recommend_date)+billDetailBean.getCreateTime());
            userFinancialBillsItem1Binding.tvRow2.setText(mContext.getResources().getString(R.string.txt_user_bills_recommend_reward)+billDetailBean.getOrderFee()+"$");

        }else if("3".equals(billDetailBean.getOrderType())){
            userFinancialBillsItem1Binding.llRow1.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow2.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow3.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.llRow4.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.llRow5.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.tvLine3.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.tvRow1.setText(mContext.getResources().getString(R.string.txt_user_bills_rechange_date)+billDetailBean.getCreateTime());
            userFinancialBillsItem1Binding.tvRow2.setText(mContext.getResources().getString(R.string.txt_user_bills_cosume_fee)+billDetailBean.getOrderFee()+"$");
        }else if("8".equals(billDetailBean.getOrderType())){
            userFinancialBillsItem1Binding.llRow1.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow2.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow3.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.llRow4.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.llRow5.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.tvLine3.setVisibility(View.GONE);
            userFinancialBillsItem1Binding.tvRow1.setText(mContext.getResources().getString(R.string.txt_user_bills_withdraw_cash_date)+billDetailBean.getCreateTime());
            userFinancialBillsItem1Binding.tvRow2.setText(mContext.getResources().getString(R.string.txt_user_bills_withdraw_cash_amount)+billDetailBean.getOrderFee()+"$");

        }else if("6".equals(billDetailBean.getOrderType())){
            userFinancialBillsItem1Binding.llRow1.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow2.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow3.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow4.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.llRow5.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.tvLine3.setVisibility(View.VISIBLE);
            userFinancialBillsItem1Binding.tvRow1.setText(mContext.getResources().getString(R.string.txt_user_bills_order_num)+billDetailBean.getPayOrderCode());
            userFinancialBillsItem1Binding.tvRow2.setText(mContext.getResources().getString(R.string.txt_user_bills_cosume_fee)+billDetailBean.getOrderFee()+"$");
            userFinancialBillsItem1Binding.tvRow3.setText(mContext.getResources().getString(R.string.txt_user_bills_drive_fee)+billDetailBean.getDistributionFee()+"$");
            userFinancialBillsItem1Binding.tvRow4.setText(mContext.getResources().getString(R.string.txt_user_bills_service_fee)+billDetailBean.getServiceFee()+"$");
//            double realIncome = billDetailBean.getDistributionFee()+billDetailBean.getSubsidyFee();
            userFinancialBillsItem1Binding.tvRow5.setText(mContext.getResources().getString(R.string.txt_user_bills_real_income)+billDetailBean.getRealIncome()+"$");
        }
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
