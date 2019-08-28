package com.example.improject;

/**
 * 登录账号
 * 15773003724   123456  jh
 *
 * 13113017500   123456  cs
 *
 */
public class Common {

    /**
     * 一些不可变的永恒的参数
     * 通常用于一些配置
     */
    public interface Constant {
        // 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

        // 基础的网络请求地址
        // 本地地址，需要自己配置为本地局域网电脑ip地址
//         String API_URL = "http://172.22.153.1:8080/api/";
        //  IPv4 地址 . . . . . . . . . . . . : 192.168.88.111
        // 无线局域网  IPv4 地址 . . . . . . . . . . . . : 172.22.153.1

        // 远程公共地址，不保证永久有效
        String API_URL = "http://api-italker.qiujuer.net/api/";

        // 最大的上传图片大小860kb
        long MAX_UPLOAD_IMAGE_LENGTH = 860 * 1024;
    }

}
