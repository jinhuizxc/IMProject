package com.example.common.core.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在界面未初始化之前调用的初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())){
            setContentView(getLayoutId());
            initWidget();
            initData();
        }else {
            finish();
        }
    }

    protected void initWindows(){

    }

    /**
     * 初始化传递参数
     * 参数正确返回true， 参数错误返回false
     * @return
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * 点击导航上面返回
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 得到当前Activity下的所有Fragment
        @SuppressLint("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if (fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        // 如果有直接Return
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }

    /**
     * 获取资源id
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 初始化控件
     */
    protected void initWidget(){
        ButterKnife.bind(this);
    };

    /**
     * 初始化数据
     */
    protected void initData(){

    };


}
