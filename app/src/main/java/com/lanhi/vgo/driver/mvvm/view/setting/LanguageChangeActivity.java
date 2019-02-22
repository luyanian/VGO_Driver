package com.lanhi.vgo.driver.mvvm.view.setting;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lanhi.ryon.utils.mutils.language.LanguageType;
import com.lanhi.ryon.utils.mutils.language.MultiLanguageUtil;
import com.lanhi.vgo.driver.BaseActivity;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.LaguageChangeActivityBinding;
import com.lanhi.vgo.driver.mvvm.view.MainActivity;
import com.lanhi.vgo.driver.mvvm.view.user.LoginActivity;
import com.lanhi.vgo.driver.weight.titlebar.TitleBarOptions;

@Route(path = "/language/change")
public class LanguageChangeActivity extends BaseActivity {
    LaguageChangeActivityBinding binding;
    private int currentSelect;
    private boolean isShowBtnBack = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.laguage_change_activity);
        isShowBtnBack = getIntent().getBooleanExtra("isShowBtnBack",false);
        binding.titlebar.setTitleBarOptions(new TitleBarOptions(){
            @Override
            public void onLeftBack(View view) {
                super.onLeftBack(view);
                finish();
            }
        });
        binding.titlebar.setBtnBackVisibility(isShowBtnBack);
        MultiLanguageUtil.init();
        int languageType = MultiLanguageUtil.getInstance().getLanguageType();
        onLanguageChanged(languageType);
    }
    public void onLanguageChanged(int languageType){
        currentSelect = languageType;
        if(languageType==LanguageType.LANGUAGE_CHINESE){
            binding.rbLanguageZh.setSelected(true);
            binding.rbLanguageEn.setSelected(false);
            binding.rbLanguageEs.setSelected(false);
        }else if(languageType==LanguageType.LANGUAGE_EN){
            binding.rbLanguageZh.setSelected(false);
            binding.rbLanguageEn.setSelected(true);
            binding.rbLanguageEs.setSelected(false);
        }else if(languageType==LanguageType.LANGUAGE_ES){
            binding.rbLanguageZh.setSelected(false);
            binding.rbLanguageEn.setSelected(false);
            binding.rbLanguageEs.setSelected(true);
        }
    }
    public void sure(View v){
        MultiLanguageUtil.getInstance().updateLanguage(currentSelect);
        if(isShowBtnBack) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        finish();
    }
    public void onSelectZH(View v){
        onLanguageChanged(LanguageType.LANGUAGE_CHINESE);
    }
    public void onSelectEN(View v){
        onLanguageChanged(LanguageType.LANGUAGE_EN);
    }
    public void onSelectES(View v){
        onLanguageChanged(LanguageType.LANGUAGE_ES);
    }
}