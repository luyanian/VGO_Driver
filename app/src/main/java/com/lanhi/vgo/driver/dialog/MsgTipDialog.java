package com.lanhi.vgo.driver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.databinding.MsgTipDialogBinding;

/**
 * Created by Administrator on 2017/9/21.
 */

public class MsgTipDialog extends Dialog {

    private DialogOptions dialogOptions;
    MsgTipDialogBinding binding;
    public MsgTipDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.msg_tip_dialog,null,false);
        setContentView(binding.getRoot());
        Window window = this.getWindow();
        window.getDecorView().setPadding(80, 0, 80, 0);
        WindowManager.LayoutParams attr = window.getAttributes();
        if (attr != null) {
            attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
            attr.gravity = Gravity.CENTER;
            window.setAttributes(attr);
        }
    }

    public MsgTipDialog setMsg(int msg) {
        binding.setMsg(App.getInstance().getResources().getString(R.string.msg_location_provider_disable));
        return this;
    }

    public MsgTipDialog setDialogOptons(final DialogOptions dialogOptons) {
        binding.setEvent(new DialogOptions(){
            @Override
            public void sure(View v) {
                super.sure(v);
                if (dialogOptons != null) {
                    dialogOptons.sure(v);
                }
                dismiss();
            }

            @Override
            public void cancle(View v) {
                super.cancle(v);
                if(dialogOptons!=null){
                    dialogOptons.cancle(v);
                }
                dismiss();
            }
        });
        return this;
    }

    public MsgTipDialog hideLeftBtn(){
        binding.btnButtonCancle.setVisibility(View.GONE);
        binding.tvLines.setVisibility(View.GONE);
        return this;
    }

}
