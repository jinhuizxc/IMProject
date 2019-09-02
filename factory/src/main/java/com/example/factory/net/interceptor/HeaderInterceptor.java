package com.example.factory.net.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.example.factory.persistence.Account;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HeaderInterceptor implements Interceptor {
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

        Response response = chain.proceed(newRequest);
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Logger.d("接收响应: [%s] %n 返回json: %s %n %s",
                response.request().url(),
                responseBody.string(),
                response.headers());
        // 返回
        return response;
    }
}
