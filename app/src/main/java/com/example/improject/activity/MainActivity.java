package com.example.improject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.factory.persistence.Account;
import com.example.improject.common.app.BaseActivity;
import com.example.improject.utils.NotificationUtils;
import com.example.improject.widget.PortraitView;
import com.example.improject.R;
import com.example.improject.fragments.main.ActiveFragment;
import com.example.improject.fragments.main.ContactFragment;
import com.example.improject.fragments.main.GroupFragment;
import com.example.improject.helper.NavHelper;
import com.mylhyl.circledialog.CircleDialog;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 4.10
 * 快速打造一款IM聊天项目
 * <p>
 * 图片选择器的实现
 */
public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    private NavHelper<Integer> mNavHelper;


    /**
     * MainActivity 显示的入口
     *
     * @param context 上下文
     */
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        NotificationUtils.checkNotification(MainActivity.this);

        // 初始化底部辅助工具类
        mNavHelper = new NavHelper<>(this, R.id.lay_container,
                getSupportFragmentManager(), this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));

        // 添加对底部按钮点击的监听
        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()) {
            // 判断用户信息是否完全，完全则走正常流程
            return super.initArgs(bundle);
        } else {
            UserActivity.startActivity(this);
            return false;
        }
    }

    /*private void checkOpenNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!NotificationUtils.isNotificationEnabled(this)) {
                new CircleDialog.Builder()
                        .setTitle("您还未开启系统通知，可能会影响消息的接收，要去开启吗？")
                        .setTitleColor(getResources().getColor(R.color.black))
                        .setWidth(0.8f)
                        .setCancelable(false)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // 跳转权限设置
                                NotificationUtils.gotoSet(MainActivity.this);
                            }
                        })
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show(getSupportFragmentManager());
            } else {
                LogUtils.e("onNext: " + "已开启通知权限");
            }
        }
    }*/

    @Override
    protected void initData() {
        super.initData();

        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.action_home, 0);
        // 初始化头像加载
        mPortrait.setup(Glide.with(this), Account.getUser());
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        PersonalActivity.startActivity(this, Account.getUserId());
    }

    @OnClick(R.id.im_search)
    void onSearchMenuClick() {
        // 在群的界面的时候，点击顶部的搜索就进入群搜索界面
        // 其他都为人搜索的界面
        int type = Objects.equals(mNavHelper.getCurrentTab().extra, R.string.title_group) ?
                SearchActivity.TYPE_GROUP : SearchActivity.TYPE_USER;
        SearchActivity.startActivity(this, type);
    }

    @OnClick(R.id.btn_action)
    void onActionClick() {
        AccountActivity.startActivity(this);
    }

    // 需要返回true表示处理，才能底部tab切换效果
//    boolean isFirst = true;
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() == R.id.action_home) {
//            mTitle.setText(item.getTitle());
//            ActiveFragment activeFragment = new ActiveFragment();
//            if (isFirst){
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.lay_container, activeFragment)
//                        .commit();
//                isFirst = false;
//            }else {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.lay_container, activeFragment)
//                        .commit();
//            }
//
//        } else if (item.getItemId() == R.id.action_group) {
//            mTitle.setText(item.getTitle());
//            GroupFragment groupFragment = new GroupFragment();
//            if (isFirst){
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.lay_container, groupFragment)
//                        .commit();
//                isFirst = false;
//            }else {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.lay_container, groupFragment)
//                        .commit();
//            }
//
//        }
//
//        Logger.d("size = " + getSupportFragmentManager().getFragments().size());
//
//        return true;
//    }

    /**
     * 当我们的底部导航被点击的时候触发
     *
     * @param item MenuItem
     * @return True 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 转接事件流到工具类中
        return mNavHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后回调的方法
     *
     * @param newTab 新的Tab
     * @param oldTab 就的Tab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        // 从额外字段中取出我们的Title资源Id
        mTitle.setText(newTab.extra);


        // 添加动画效果
        // 对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.title_home)) {
            // 主界面时隐藏
//            transY = 100;
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            // transY 默认为0 则显示
            if (Objects.equals(newTab.extra, R.string.title_group)) {
                // 群
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            } else {
                // 联系人
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }

        // 开始动画
        // 旋转，Y轴位移，弹性差值器，时间
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();


    }
}
