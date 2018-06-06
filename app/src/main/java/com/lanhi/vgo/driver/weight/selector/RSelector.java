package com.lanhi.vgo.driver.weight.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.RselectorBinding;

/**
 * Created by Administrator on 2017/9/13.
 */

public class RSelector extends LinearLayout implements View.OnClickListener{
    private RselectorBinding binding;
    private int currentItem = 0;
    private RSelectorChangeLisener listenser;
    private Drawable drawableSelect;
    private Drawable drawableUnSelect;

    public RSelector(Context context) {
        super(context);
        init(context,null);
    }

    public RSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    public void init(Context context,AttributeSet attrs){
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.rselector,this,true);

        drawableSelect = getResources().getDrawable(R.drawable.icon_line_h);
        drawableSelect.setBounds(0, 0, drawableSelect.getMinimumWidth(), drawableSelect.getMinimumHeight());
        drawableUnSelect = getResources().getDrawable(R.drawable.icon_line_d);
        drawableUnSelect.setBounds(0, 0, drawableUnSelect.getMinimumWidth(), drawableUnSelect.getMinimumHeight());

        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RSelector);
            String leftTitle = typedArray.getString(R.styleable.RSelector_leftTitle);
            String rightTitle = typedArray.getString(R.styleable.RSelector_rightTitle);
            currentItem = typedArray.getInt(R.styleable.RSelector_checkItem,0);
            binding.setLeftTitle(leftTitle);
            binding.setRightTitle(rightTitle);
        }
        binding.tvItem1.setOnClickListener(this);
        binding.tvItem2.setOnClickListener(this);
        onCheck(currentItem);
    }

    public int getCurrentItem(){
        return currentItem;
    }

    private void onCheck(int currentItem) {
        if(currentItem==0){
            onClick(binding.tvItem1);
        }else{
            onClick(binding.tvItem2);
        }

    }

    public void setRSelectorChangeListener(RSelectorChangeLisener listenser){
        this.listenser = listenser;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_item1:
                this.currentItem = 0;
                binding.tvItem2.setSelected(false);
                binding.tvItem2.setCompoundDrawables(null, null, null, drawableUnSelect);
                binding.tvItem1.setSelected(true);
                binding.tvItem1.setCompoundDrawables(null, null, null, drawableSelect);
                if(listenser!=null){
                    listenser.onLeftClick();
                }
                break;
            case R.id.tv_item2:
                this.currentItem = 1;
                binding.tvItem1.setSelected(false);
                binding.tvItem1.setCompoundDrawables(null, null, null, drawableUnSelect);
                binding.tvItem2.setSelected(true);
                binding.tvItem2.setCompoundDrawables(null, null, null, drawableSelect);
                if(listenser!=null){
                    listenser.onRightClick();
                }
                break;
        }
    }
}
