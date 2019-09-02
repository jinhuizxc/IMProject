package com.example.factory.net.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 登录:
 * I/logJson: 接收响应：[http://api-italker.qiujuer.net/api/account/login]
 *     返回json:{"code":1,"message":"ok","time":"2019-09-02T11:07:49.385",
 *     "result":{"user":{"id":"42199843-3424-4e28-8f8b-a42e65cdf48f","name":"jh","phone":"15773003724",
 *     "portrait":"http://italker.oss-cn-hongkong.aliyuncs.com/portrait/201908/6fc817ab809ebf412652499f09836ca2.jpg",
 *     "desc":"dd","sex":1,"follows":6,"following":6,"isFollow":false,"modifyAt":"2019-09-02T11:07:49.344"},
 *     "account":"15773003724","token":"NWNiMWRiZmQtZGQwNC00MjdhLWExOTgtODYwNjU3ZmM5Nzdh","isBind":true}}
 *
 * 发送信息:
 * I/logJson: 接收响应：[http://api-italker.qiujuer.net/api/msg]
 *     返回json:{"code":1,"message":"ok","time":"2019-09-02T11:09:14.215",
 *     "result":{"id":"1d2a21d4-39ba-4f4b-ac3e-154a96fba0a8","content":"ggg","attach":null,"type":1,
 *     "createAt":"2019-09-02T11:09:13.934","groupId":null,"senderId":"42199843-3424-4e28-8f8b-a42e65cdf48f",
 *     "receiverId":"b96d9cd6-8953-4144-b075-7e40a12da298"}}
 *
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //Chain 里包含了request和response
        Request request = chain.request();
        long t1 = System.nanoTime();//请求发起的时间
        Log.i("logUri",String.format("发送请求 %s on %s%n%s",request.url(),chain.connection(),request.headers()));
        Response response = chain.proceed(request);

        response.header("Content-Type", "application/json;charset=UTF-8");
        long t2 = System.nanoTime();//收到响应的时间
        //不能直接使用response.body（）.string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，
        // 我们需要创建出一个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        // wx 返回json:{"code":500,"msg":"服务器异常","data":""}
      /*  Log.i("logJson",String.format("接收响应：[%s] %n 返回json: %s %.1fms %n %s",
                response.request().url(),
                responseBody.string(),
                (t2-t1) /1e6d,
                response.headers()));*/
        return response;
    }
}
