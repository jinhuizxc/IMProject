package com.example.improject.push;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.orhanobut.logger.Logger;

/**
 * 接收推送服务事件
 *
 * 在项目源码中添加一个继承自com.igexin.sdk.GTIntentService的类，用于接收CID、透传消息以及其他推送服务事件。请参考下列代码实现各个事件回调方法：
 *
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 *
 */
public class AppMessageReceiverService extends GTIntentService {

    public AppMessageReceiverService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        // 返回消息接收进程id，当前APP中就是AppPushService进程id
        Log.i(TAG, "onReceiveServicePid() called with: context = [" + context + "], pid = [" + pid + "]");
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理方式，详看SDK demo
        // 当接收到透传消息时回调，跟之前广播接收消息一样
        Log.i(TAG, "onReceiveMessageData() called with: context = [" + context + "], msg = [" + msg + "]");
        // 拿到透传消息的实体信息转换为字符串
        byte[] payload = msg.getPayload();
        if (payload != null) {
            String message = new String(payload);
            onMessageArrived(message);
        }
    }



    // 接收 cid

    /**
     * I/GTIntentService: onReceiveClientId() called with:
     * context = [com.example.improject.push.AppMessageReceiverService@45fbc3],
     * clientid = [abeb80dbee49a55892f876738a7d4b14]
     * @param context
     * @param clientid
     */
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        // 当设备成功在个推注册时返回个推唯一设备码
        Log.i(TAG, "onReceiveClientId() called with: context = [" + context + "], clientid = [" + clientid + "]");
        // 当Id初始化的时候
        // 获取设备Id
        onClientInit(clientid);
    }

    // cid 离线上线通知

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        // 当设备状态变化时回调，是否在线
        Log.i(TAG, "onReceiveOnlineState() called with: context = [" + context + "], online = [" + online + "]");
    }
    // 各种事件处理回执

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        // 当个推Command命名返回时回调
        Log.i(TAG, "onReceiveCommandResult() called with: context = [" + context + "], cmdMessage = [" + cmdMessage + "]");
    }
    // 通知到达，只有个推通道下发的通知会回调此方法

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
        // 当通知栏消息达到时回调
        Log.i(TAG, "onNotificationMessageArrived() called with: context = [" + context + "], msg = [" + msg + "]");
    }
    // 通知点击，只有个推通道下发的通知会回调此方法

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        // 当通知栏消息点击时回调
        Log.i(TAG, "onNotificationMessageClicked() called with: context = [" + context + "], msg = [" + msg + "]");
    }


    /**
     * 消息达到时
     *
     * @param message 新消息
     */
    public void onMessageArrived(String message){

    }

    /**
     * 当Id初始化的试试
     *
     * @param clientid 设备Id
     */
    private void onClientInit(String clientid) {

    }
}
