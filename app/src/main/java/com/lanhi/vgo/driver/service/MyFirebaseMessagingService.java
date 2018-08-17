package com.lanhi.vgo.driver.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lanhi.ryon.utils.mutils.LogUtils;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import com.lanhi.vgo.driver.common.Common;
import com.lanhi.vgo.driver.mvvm.view.order.OrderDetailActivity;

import java.util.Map;

/**
 * 一项继承 FirebaseMessagingService 的服务。
 * 如果您希望在后台进行除接收应用通知之外的消息处理，则必须添加此服务。
 * 要接收前台应用中的通知、接收数据有效负载以及发送上行消息等，您必须继承此服务。
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LogUtils.d("firebase",remoteMessage.toString());
        if (remoteMessage.getNotification() != null) {
            LogUtils.d("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }else{
            LogUtils.d("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Map<String,String> map = remoteMessage.getData();
        if(map.containsKey("orderId")){
            String orderState = map.get("orderState");
            String orderId = map.get("orderId");
            String content = Common.getNotifyOrderContent(orderState);
            Intent intent = new Intent(App.getInstance().getApplicationContext(),OrderDetailActivity.class);
            intent.putExtra("order_code",orderId);
            showNotification("VGO Driver","content",intent);
        }else{

        }
    }

    private void showNotification(String title, String content, Intent intent) {
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(App.getInstance().getApplicationContext(),"1");
        mBuilder.setContentTitle(title)//设置通知栏标题
                .setContentText(content)
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON

        PendingIntent pendingIntent = PendingIntent.getActivity(App.getInstance().getApplicationContext(), 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(1,notification);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }
}
