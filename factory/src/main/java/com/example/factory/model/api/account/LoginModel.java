package com.example.factory.model.api.account;

/**
 * 登录的model
 *
 * 登录的用户信息：LoginModel{account='15773003724', password='123456', pushId='84c75ff49a5dfd00dbe1d7956f61eb5a'}
 *
 */
public class LoginModel {

    private String account;  // 用户名
    private String password; // 密码
    private String pushId;   // 推送id

    public LoginModel(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public LoginModel(String account, String password, String pushId) {
        this.account = account;
        this.password = password;
        this.pushId = pushId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", pushId='" + pushId + '\'' +
                '}';
    }
}
