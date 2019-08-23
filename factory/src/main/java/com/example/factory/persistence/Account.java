package com.example.factory.persistence;

import android.content.Context;

/**
 * 账户信息
 */
public class Account {

    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";

    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";

    // 设备的推送Id
    private static String pushId;
    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;
    // 登录状态的Token，用来接口请求
    private static String token;
    // 登录的用户ID
    private static String userId;
    // 登录的账户
    private static String account;

    /**
     * 存储数据到XML文件，持久化
     */
    public static void save(Context context){
        // 获取数据持久化的SP

    }

    /**
     * 获取当前登录的Token
     *
     * @return Token
     */
    public static String getToken() {
        return token;
    }
}
