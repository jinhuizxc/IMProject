package com.example.improject.factory.presenter;

/**
 * 对RecyclerView进行的一个简单的Presenter封装
 */
public class BaseRecyclerPresenter<ViewMode, View extends BaseContract.RecyclerView>
        extends BasePresenter<View> {

    public BaseRecyclerPresenter(View mView) {
        super(mView);
    }
}
