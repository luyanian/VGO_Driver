package com.lanhi.vgo.driver.weight.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.TitlebarBinding;
import com.lanhi.ryon.utils.mutils.BarUtils;

/**
 * Created by Administrator on 2017/9/13.
 */
public class Titlebar extends RelativeLayout{
    private TitlebarBinding binding;

    public Titlebar(Context context) {
        super(context);
        init(context,null);
    }

    public Titlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.titlebar,this,true);
        setPaddingEquesBarHeight();
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Titlebar);
            String title = typedArray.getString(R.styleable.Titlebar_title);
            String rightButtonText = typedArray.getString(R.styleable.Titlebar_rightButtonText);
            boolean showLeftBack = typedArray.getBoolean(R.styleable.Titlebar_showLeftBack,false);
            boolean showRightTextButton = typedArray.getBoolean(R.styleable.Titlebar_showRightTextButton,false);

            binding.setTitle(title);
            binding.setShowLeftBack(showLeftBack);
            binding.setShowRightTextButton(showRightTextButton);
            binding.setRightButtonText(rightButtonText);
        }
    }

    public void setTitleBarOptions(TitleBarOptions titleBarOptions) {
        binding.setOptions(titleBarOptions);
    }

    public void setTitle(int text) {
        setTitle(getResources().getString(text));
    }

    public void setTitle(String text) {
        binding.setTitle(text);
    }

    public void setBtnBackVisibility(boolean isShow){
        binding.setShowLeftBack(isShow);
    }

    public void setPaddingEquesBarHeight() {
        ViewGroup.LayoutParams layoutParams =binding.rlTitleBar.getLayoutParams();
        layoutParams.height = layoutParams.height+ BarUtils.getStatusBarHeight();
        binding.rlTitleBar.setLayoutParams(layoutParams);
        binding.setPaddingTop(BarUtils.getStatusBarHeight());
    }
//    @BindingAdapter("app:title")
//    public static void setTitle(Titlebar titleBar,String title){
//        binding.setTitle(title);
//    }
//    @BindingAdapter("app:showLeftBack")
//    public static void setShowLeftBack(Titlebar titleBar,Boolean isShow){
//        binding.setShowLeftBack(isShow);
//    }
//    @BindingAdapter("app:showRightTextButton")
//    public static void setShowRightTextButton(Titlebar titleBar,Boolean isShow){
//        binding.setShowRightTextButton(isShow);
//    }
//    @BindingAdapter("app:rightButtonText")
//    public static void setRightButtonText(Titlebar titleBar,String text){
//        binding.setRightButtonText(text);
//    }
}
