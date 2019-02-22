package com.lanhi.vgo.driver.mvvm.view.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
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
import com.lanhi.vgo.driver.databinding.OrderListFragmentBinding;
import com.lanhi.vgo.driver.mvvm.viewmodel.OrderViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class OrderListFragment extends Fragment implements View.OnClickListener{
    OrderListFragmentBinding binding;
    OrderViewModel orderViewModel;
    private Drawable drawableSelect;
    private Drawable drawableUnSelect;
    private int pageNum = 0;
    private String orderstate = GlobalParams.ORDER_STATE.ALL;
    private OrderListAdapter orderListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.order_list_fragment,container,false);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTab();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        orderListAdapter = new OrderListAdapter(orderViewModel,new OnEventListener(){
            @Override
            public void grapOrder(View v, OrderListResponse.OrderListBean orderListBean) {
                super.grapOrder(v,orderListBean);
                if(GlobalParams.ORDER_STATE.UNANSWEWD.equals(orderListBean.getOrder_state())){

//                    orderViewModel.grapOrder(orderListBean, location, new RObserver<BaseResponse>() {
//                        @Override
//                        public void onSuccess(BaseResponse baseResponse) {
//                            binding.refreshLayout.autoRefresh();
//                        }
//                    });
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
                orderViewModel.loadOrderList(orderstate,pageNum);
                refreshlayout.finishRefresh(500/*,false*/);
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                orderViewModel.loadOrderList(orderstate,++pageNum);
                refreshlayout.finishLoadMore(500/*,false*/);
            }
        });
        orderViewModel.getOrderListLiveData().observe(this, new Observer<List<OrderListResponse.OrderListBean>>() {
            @Override
            public void onChanged(@Nullable List<OrderListResponse.OrderListBean> orderListBeans) {
                orderListAdapter.notifyData(orderListBeans);
            }
        });
        onClick(binding.llTab1);
    }

    private void initTab() {
        drawableSelect = getResources().getDrawable(R.drawable.icon_line_h);
        drawableSelect.setBounds(0, 0, drawableSelect.getMinimumWidth(), drawableSelect.getMinimumHeight());
        drawableUnSelect = getResources().getDrawable(R.drawable.shap_tiled_trans);
        drawableUnSelect.setBounds(0, 0, drawableUnSelect.getMinimumWidth(), drawableUnSelect.getMinimumHeight());

        binding.llTab1.setOnClickListener(this);
        binding.llTab2.setOnClickListener(this);
        binding.llTab3.setOnClickListener(this);
        binding.llTab4.setOnClickListener(this);
        binding.llTab5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideAllTab();
        switch (v.getId()){
            case R.id.ll_tab_1:
                binding.imgTab1.setSelected(true);
                binding.tvTab1.setSelected(true);
                binding.tvTab1.setCompoundDrawables(null, null, null, drawableSelect);
                orderstate = GlobalParams.ORDER_STATE.ALL;
                binding.refreshLayout.autoRefresh();
                break;
            case R.id.ll_tab_2:
                binding.imgTab2.setSelected(true);
                binding.tvTab2.setSelected(true);
                binding.tvTab2.setCompoundDrawables(null, null, null, drawableSelect);
                orderstate = GlobalParams.ORDER_STATE.UNANSWEWD;
                binding.refreshLayout.autoRefresh();
                break;
            case R.id.ll_tab_3:
                binding.imgTab3.setSelected(true);
                binding.tvTab3.setSelected(true);
                binding.tvTab3.setCompoundDrawables(null, null, null, drawableSelect);
                orderstate = GlobalParams.ORDER_STATE.UNPICKUP;
                binding.refreshLayout.autoRefresh();
                break;
            case R.id.ll_tab_4:
                binding.imgTab4.setSelected(true);
                binding.tvTab4.setSelected(true);
                binding.tvTab4.setCompoundDrawables(null, null, null, drawableSelect);
                orderstate = GlobalParams.ORDER_STATE.ON_THE_WAY;
                binding.refreshLayout.autoRefresh();
                break;
            case R.id.ll_tab_5:
                binding.imgTab5.setSelected(true);
                binding.tvTab5.setSelected(true);
                binding.tvTab5.setCompoundDrawables(null, null, null, drawableSelect);
                orderstate = GlobalParams.ORDER_STATE.COMPLETE;
                binding.refreshLayout.autoRefresh();
                break;
        }
    }

    private void hideAllTab() {
        binding.imgTab1.setSelected(false);
        binding.tvTab1.setSelected(false);
        binding.tvTab1.setCompoundDrawables(null, null, null, drawableUnSelect);

        binding.imgTab2.setSelected(false);
        binding.tvTab2.setSelected(false);
        binding.tvTab2.setCompoundDrawables(null, null, null, drawableUnSelect);

        binding.imgTab3.setSelected(false);
        binding.tvTab3.setSelected(false);
        binding.tvTab3.setCompoundDrawables(null, null, null, drawableUnSelect);

        binding.imgTab4.setSelected(false);
        binding.tvTab4.setSelected(false);
        binding.tvTab4.setCompoundDrawables(null, null, null, drawableUnSelect);

        binding.imgTab5.setSelected(false);
        binding.tvTab5.setSelected(false);
        binding.tvTab5.setCompoundDrawables(null, null, null, drawableUnSelect);
    }
}
