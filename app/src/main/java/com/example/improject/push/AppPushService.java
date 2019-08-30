package com.example.improject.push;

import android.content.Intent;
import android.os.IBinder;

import com.igexin.sdk.GTServiceManager;
import com.igexin.sdk.PushService;

/**
 * TODO 配置自定义推送服务
 *
 * 为了让推送服务在部分主流机型上更稳定运行，从2.9.5.0版本开始，个推支持第三方应用配置使用自定义Service来作为推送服务运行的载体。
 *
 *     在项目源码中添加一个继承自com.igexin.sdk.PushService的自定义Service：
 *
 * package com.getui.demo;
 *
 * // 仅2.13.1.0及以上版本才能直接extends PushService，低于此版本请延用之前实现方式
 * public class AppPushServiceOld extends com.igexin.sdk.PushService {
 *
 * }
 *
 * 此类空实现即可.
 *
 * TODO 低版本实现：
 *
 *  个推推送SDK新版本Service支持
 *  该Service用以与个推服务器通讯，维持长链接；所以需要独立进程
 *  当收到消息后可以通过广播方式传递出来，或者通过GTIntentService传递消息出来
 *  比如：{@link AppMessageReceiverService}
 *
 *  public class AppPushServiceOld extends Service {
 *     public AppPushServiceOld() {
 *     }
 *
 *     @Override
 *     public void onCreate() {
 *         super.onCreate();
 *         GTServiceManager.getInstance().onCreate(this);
 *     }
 *
 *     @Override
 *     public int onStartCommand(Intent intent, int flags, int startId) {
 *         super.onStartCommand(intent, flags, startId);
 *         return GTServiceManager.getInstance().onStartCommand(this, intent, flags, startId);
 *     }
 *
 *     @Override
 *     public IBinder onBind(Intent intent) {
 *         return GTServiceManager.getInstance().onBind(intent);
 *     }
 *
 *     @Override
 *     public void onDestroy() {
 *         super.onDestroy();
 *         GTServiceManager.getInstance().onDestroy();
 *     }
 *
 *     @Override
 *     public void onLowMemory() {
 *         super.onLowMemory();
 *         GTServiceManager.getInstance().onLowMemory();
 *     }
 * }
 *
 *
 *
 */
public class AppPushService extends PushService {
}