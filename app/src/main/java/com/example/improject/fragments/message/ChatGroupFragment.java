package com.example.improject.fragments.message;

import com.example.factory.model.db.Group;
import com.example.factory.model.db.view.MemberUserModel;
import com.example.factory.presenter.message.ChatContract;

import java.util.List;

/**
 * 群聊天界面实现
 */
public class ChatGroupFragment extends ChatFragment<Group>
        implements ChatContract.GroupView {
    @Override
    public void showAdminOption(boolean isAdmin) {

    }

    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, long moreCount) {

    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    public void onInit(Group group) {

    }

    @Override
    protected ChatContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutId() {
        return 0;
    }
}
