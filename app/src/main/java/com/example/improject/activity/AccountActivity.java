package com.example.improject.activity;

import android.content.Context;
import android.content.Intent;

import com.example.common.core.app.BaseActivity;
import com.example.improject.R;
import com.example.improject.fragments.user.UpdateInfoFragment;

public class AccountActivity extends BaseActivity {


    /**
     * 账户Activity显示的入口
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, new UpdateInfoFragment())
                .commit();

    }
}
