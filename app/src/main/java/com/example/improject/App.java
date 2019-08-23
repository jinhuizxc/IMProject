package com.example.improject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.common.core.app.CommonApplication;
import com.example.factory.model.db.AppDataBase;
import com.example.factory.model.db.test.User;
import com.example.factory.model.db.test.User2Model;
import com.example.factory.model.db.test.User2Model_Table;
import com.example.improject.push.AppMessageReceiverService;
import com.example.improject.push.AppPushService;
import com.igexin.sdk.PushManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;
import java.util.Random;
import java.util.UUID;

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
public class App extends CommonApplication {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化DBFLOW
        FlowManager.init(this);

        initLogger();
        // 注册生命周期
        registerActivityLifecycleCallbacks(new PushInitializeLifecycle());


        // 测试
        testDB();
    }

    private void test1() {
        User2Model userModel = new User2Model();
        userModel.setName("UserModel");
        userModel.setAge(new Random().nextInt(100));
        userModel.save();
        userModel.update();
        userModel.delete();
    }

    private void testDB() {
        User user = new User();
        user.id = UUID.randomUUID();
        user.name = "kaka";
        user.age = 18;
        ModelAdapter<User> adapter = FlowManager.getModelAdapter(User.class);
        //插入User
        adapter.insert(user);
        //修改名字
        user.name = "Test";
        adapter.update(user);
        User userQuery = SQLite.select().from(User.class).querySingle();
        Log.e(TAG, "id=" + userQuery.id + ",name=" + userQuery.name + ",age=" + userQuery.age);


        test1();
        // DBFLOW在查询方法提高了很强大的支持，无论你是要排列、分组还是各种条件查询都可以满足
        query();

        // 事务操作、批量操作
        transAction();
        transActionAsync();

    }

    //异步事务
    private void transActionAsync() {
        FlowManager.getDatabase(AppDataBase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (int i = 0; i < 100; i++) {
                    User2Model userModel = new User2Model();
                    userModel.setName("UserModel");
                    userModel.setAge(new Random().nextInt(100));
                    userModel.save(databaseWrapper);
                }
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                Log.e(TAG, "onSuccess()");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                Log.e(TAG, "onError()");
            }
        }).build().execute();
    }

    private void transAction() {
        FlowManager.getDatabase(AppDataBase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (int i = 0; i < 100; i++) {
                    User2Model userModel = new User2Model();
                    userModel.setName("UserModel");
                    userModel.setAge(new Random().nextInt(100));
                    userModel.save(databaseWrapper);
                }
            }
        });
    }

    private void query() {
        List<User2Model> user2Models = SQLite.select().from(User2Model.class)
                .where(User2Model_Table.age.greaterThan(18),
                        User2Model_Table.name.eq("UserModel"))
                .orderBy(OrderBy.fromNameAlias(NameAlias.of("id")))
                .groupBy(NameAlias.of("id"))
                .queryList();
        if (user2Models.size() != 0) {
            for (User2Model user2Model : user2Models) {
                Log.e(TAG, "id=" + user2Model.getId() + ",name="
                        + user2Model.getName() + ",age=" + user2Model.getAge());
            }
        } else {
            Logger.d("数据为0");
        }
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
            // 推送注册消息接收服务
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


    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("IM_Project")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
