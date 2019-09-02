package com.example.factory.net;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.example.factory.net.interceptor.HeaderInterceptor;
import com.example.factory.net.interceptor.LoggingInterceptor;
import com.example.improject.common.Common;
import com.example.factory.Factory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的封装
 */
public class Network {

    private static Network instance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private static int connectTimeout = 10;
    private static int writeTimeout = 10;
    private static int readTimeout = 10;

    static {
        instance = new Network();
    }

    public static OkHttpClient getClient(){
        if (instance.okHttpClient != null){
            return instance.okHttpClient;
        }

        // 初始化okHttpClient
        instance.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                // 给所有的请求添加一个拦截器
                .addNetworkInterceptor(new HttpLoggingInterceptor(
                                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NonNull String message) {
                        LogUtils.d("拦截的网络请求: " + message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                // 添加logger拦截
                .addInterceptor(new LoggingInterceptor())
                // header拦截器
                .addInterceptor(new HeaderInterceptor())
                .build();
        return instance.okHttpClient;
    };

    // 构建一个Retrofit
    public static Retrofit getRetrofit(){
        if (instance.retrofit != null){
            return instance.retrofit;
        }
        // /得到一个OKClient
        OkHttpClient client = getClient();
        // Retrofit
        instance.retrofit = new Retrofit.Builder()
                // 设置client
                .client(client)
                // 设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .baseUrl(Common.Constant.API_URL).build();
        return instance.retrofit;
    }

    /**
     * 返回一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remote(){
        return Network.getRetrofit().create(RemoteService.class);
    }

}
