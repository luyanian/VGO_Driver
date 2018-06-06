package com.lanhi.vgo.driver.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.OrderListResponse;
import com.lanhi.vgo.driver.common.GlobalParams;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.OrderListItemBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter {

    private OrderViewModel orderViewModel;
    private OnEventListener eventListener;
    private List<OrderListResponse.OrderListBean> data = new ArrayList<>();

    public OrderListAdapter(OrderViewModel orderViewModel,OnEventListener eventListener) {
        this.orderViewModel = orderViewModel;
        this.eventListener = eventListener;
    }

    public void notifyData(List<OrderListResponse.OrderListBean> data){
        this.data.clear();
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_item,parent,false);
        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OrderListResponse.OrderListBean user = data.get(position);
        BindingHolder bindingHolder = (BindingHolder) holder;
        bindingHolder.getBinding().setData(user);
        bindingHolder.getBinding().setEvent(eventListener);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private OrderListItemBinding binding;
        public BindingHolder(View itemView) {
            super(itemView);
        }
        public OrderListItemBinding getBinding() {
            return binding;
        }
        public void setBinding(OrderListItemBinding binding) {
            this.binding = binding;
        }
    }

}