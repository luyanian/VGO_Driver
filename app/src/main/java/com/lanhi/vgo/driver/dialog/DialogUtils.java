package com.lanhi.vgo.driver.dialog;

import android.content.Context;

/**
 * Created by Administrator on 2017/9/21.
 */

public class DialogUtils {
    public static DialogUtils dialogUtils;
    public synchronized static DialogUtils getInstance(){
        if(dialogUtils==null){
            synchronized (DialogUtils.class){
                dialogUtils=new DialogUtils();
            }
        }
        return dialogUtils;
    }
    static MsgTipDialog msgTipDialog;
    public synchronized MsgTipDialog createMsgTipDialog(Context context){
        if(msgTipDialog!=null){
            if(msgTipDialog.isShowing()){
                msgTipDialog.dismiss();
            }
            msgTipDialog=null;
        }
        msgTipDialog = new MsgTipDialog(context);
        return msgTipDialog;
    }

}
