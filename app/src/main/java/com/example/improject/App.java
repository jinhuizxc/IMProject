package com.example.improject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.improject.common.app.BaseApplication;
import com.example.improject.push.AppMessageReceiverService;
import com.example.improject.push.AppPushService;
import com.igexin.sdk.PushManager;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * 个推集成:
 * 应用名称：ImProject
 * appId：zo9V9CS82o6UiMNG0urM63
 * appSecret：r6bbcykLjhAfoEha4MTCB3
 * appKey：W3LBIwK3JqA9hsPO1Hlm17
 * 应用包名：  com.example.improject
 * 应用签名：E1:9D:40:7C:FB:D8:EF:B2:92:F9:07:27:2F:ED:09:3D:27:2D:87:BC
 * masterSecret：【个推】 j2IZzUiliCATJ3RrU7uJG8
 * <p>
 * 数据库DBFlow:
 * <p>
 * Android 阿里云OSS上传
 * https://blog.csdn.net/qq_31939617/article/details/90256659
 *
 * 安卓手把手教你结合阿里云OSS存储实现视频（音频，图片）的上传与下载
 * https://blog.csdn.net/u012534831/article/details/51240385
 */
public class App extends BaseApplication {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化DBFLOW
        FlowManager.init(this);

        // 注册生命周期
        registerActivityLifecycleCallbacks(new PushInitializeLifecycle());

    }

    @Override
    protected void showAccountView(Context context) {
        // 登录界面的显示

    }

    /**
     * 个推服务在部分手机上极易容易回收，可放Resumed中唤起
     */
    private class PushInitializeLifecycle implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            // 推送进行初始化
            PushManager.getInstance().initialize(App.this, AppPushService.class);
            PushManager.getInstance().registerPushIntentService(App.this, AppMessageReceiverService.class);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

}
