package com.example.improject.push.old;

import android.content.Context;
import android.util.Log;

import com.example.factory.Factory;
import com.example.factory.data.helper.AccountHelper;
import com.example.factory.persistence.Account;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.orhanobut.logger.Logger;


/**
 * 个推接收消息的IntentService，用以接收具体的消息信息
 * 替换之前老版本的消息广播
 */
public class AppMessageReceiverService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int i) {
        // 返回消息接收进程id，当前APP中就是AppPushService进程id
        Log.i(TAG, "onReceiveServicePid() called with: context = [" + context + "], i = [" + i + "]");
    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        // 当设备成功在个推注册时返回个推唯一设备码
        Log.i(TAG, "onReceiveClientId() called with: context = [" + context + "], s = [" + s + "]");
        // 当Id初始化的时候
        // 获取设备Id
        onClientInit(s);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        // 当接收到透传消息时回调，跟之前广播接收消息一样
        Log.i(TAG, "onReceiveMessageData() called with: context = [" + context + "], gtTransmitMessage = [" + gtTransmitMessage + "]");

        // 拿到透传消息的实体信息转换为字符串
        byte[] payload = gtTransmitMessage.getPayload();
        if (payload != null) {
            String message = new String(payload);
            onMessageArrived(message);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        // 当设备状态变化时回调，是否在线
        Log.i(TAG, "onReceiveOnlineState() called with: context = [" + context + "], b = [" + b + "]");
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        // 当个推Command命名返回时回调
        Log.i(TAG, "onReceiveCommandResult() called with: context = [" + context + "], gtCmdMessage = [" + gtCmdMessage + "]");
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {
        // 当通知栏消息达到时回调
        Log.i(TAG, "onNotificationMessageArrived() called with: context = [" + context + "], gtNotificationMessage = [" + gtNotificationMessage + "]");
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        // 当通知栏消息点击时回调
        Log.i(TAG, "onNotificationMessageClicked() called with: context = [" + context + "], gtNotificationMessage = [" + gtNotificationMessage + "]");
    }


    /**
     * 当Id初始化的试试
     *
     * @param cid 设备Id
     */
    private void onClientInit(String cid) {
        // 设置设备Id
        Account.setPushId(cid);
        if (Account.isLogin()) {
            // 账户登录状态，进行一次PushId绑定
            // 没有登录是不能绑定PushId的
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 消息达到时
     *
     * @param message 新消息
     *
     *推送过来的新消息: [{"type":200,"content":"{\"id\":\"d9214d43-4e9f-48f5-a797-3dda8c9823f5\",\"content\":\"解决\",\"attach\":null,\"type\":1,
     *                \"createAt\":\"2019-08-30T11:48:39.710\",\"groupId\":null,\"senderId\":\"42199843-3424-4e28-8f8b-a42e65cdf48f\",
     *                \"receiverId\":\"b96d9cd6-8953-4144-b075-7e40a12da298\"}","createAt":"2019-08-30T11:48:39.861"}]
     *
     */
    private void onMessageArrived(String message) {
        Logger.d("推送过来的新消息: " + message);
        // 交给Factory处理
        Factory.dispatchPush(message);
    }
}
