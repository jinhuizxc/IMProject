package com.example.factory.net;

import android.text.TextUtils;

import com.example.improject.Common;
import com.example.factory.Factory;
import com.example.factory.persistence.Account;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 拿到我们的请求
                        Request original = chain.request();
                        // 重新进行build
                        Request.Builder builder = original.newBuilder();
                        if (!TextUtils.isEmpty(Account.getToken())){
                            // 注入一个token
                            builder.addHeader("token", Account.getToken());
                        }
                        builder.addHeader("Content-Type", "application/json");
                        Request newRequest = builder.build();
                        // 返回
                        return chain.proceed(newRequest);
                    }
                })
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

}
