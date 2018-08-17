package com.lanhi.vgo.driver.mvvm.view.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.adapter.OrderListAdapter;
import com.lanhi.vgo.driver.api.response.BaseResponse;
import com.lanhi.vgo.driver.api.response.OrderListResponse;
import com.lanhi.vgo.driver.common.GlobalParams;
import com.lanhi.vgo.driver.common.OnEventListener;
import com.lanhi.vgo.driver.common.RObserver;
import com.lanhi.vgo.driver.databinding.OrderGrapFragmentBinding;
import com.lanhi.vgo.driver.mvvm.model.StateCityData;
import com.lanhi.vgo.driver.mvvm.viewmodel.OrderViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class OrderGrapFragment extends Fragment {
    OrderGrapFragmentBinding binding;
    OrderViewModel orderViewModel;
    private OrderListAdapter orderListAdapter;
    private int pageNum = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.order_grap_fragment,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        initData();
        initEventListener();
    }

    private void initData() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        orderListAdapter = new OrderListAdapter(orderViewModel,new OnEventListener(){
            @Override
            public void grapOrder(View v, OrderListResponse.OrderListBean orderListBean) {
                super.grapOrder(v,orderListBean);
                if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(orderListBean.getOrder_state())){
                    orderViewModel.grapOrder(orderListBean,new RObserver<BaseResponse>() {
                        @Override
                        public void onSuccess(BaseResponse baseResponse) {
                            binding.refreshLayout.autoRefresh();
                        }
                    });
                }else{
                    ARouter.getInstance().build("/order/detail").withString("order_code",orderListBean.getOrder_code()).navigation();
                }
            }

            @Override
            public void viewOrderDetail(View v, OrderListResponse.OrderListBean orderListBean) {
                super.viewOrderDetail(v, orderListBean);
                ARouter.getInstance().build("/order/detail").withString("order_code",orderListBean.getOrder_code()).navigation();
            }
        });
        binding.recyclerView.setAdapter(orderListAdapter);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum=1;
                orderViewModel.loadOrderList(GlobalParams.ORDER_STATE.UNANSWEWD,pageNum);
                refreshlayout.finishRefresh(500/*,false*/);
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                orderViewModel.loadOrderList(GlobalParams.ORDER_STATE.UNANSWEWD,++pageNum);
                refreshlayout.finishLoadMore(500/*,false*/);
            }
        });
        orderViewModel.getOrderListLiveData().observe(this, new Observer<List<OrderListResponse.OrderListBean>>() {
            @Override
            public void onChanged(@Nullable List<OrderListResponse.OrderListBean> orderListBeans) {
                orderListAdapter.notifyData(orderListBeans);
            }
        });
    }

    private void initEventListener() {

    }

}
