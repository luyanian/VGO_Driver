package com.lanhi.vgo.driver.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.StateCityItemBinding;
import com.lanhi.vgo.driver.mvvm.model.StateCityData;

import java.util.ArrayList;
import java.util.List;

public class StateCityAdapter extends BaseAdapter {
    private Context context;
    private List<StateCityData> list = new ArrayList<>();

    public StateCityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null||convertView.getTag()==null){
            StateCityItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.state_city_item, parent, false);
            viewHolder = new ViewHolder(binding);
            binding.getRoot().setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.binding.setModel(list.get(position));
        return viewHolder.binding.getRoot();
    }

    static class ViewHolder {
        StateCityItemBinding binding;

        public ViewHolder(StateCityItemBinding binding) {
            this.binding = binding;
        }
    }

    public void changeData(List<StateCityData> mlist){
        list.clear();
        list.addAll(mlist);
        this.notifyDataSetChanged();
    }
}
