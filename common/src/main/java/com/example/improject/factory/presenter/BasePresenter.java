package com.example.improject.factory.presenter;

/**
 * 基类Presenter
 *
 * BasePresenter与View建立关联
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {

    private T mView;

    // 构造方法，处理2者之间关系;
    public BasePresenter(T mView) {
//        this.mView = mView;
        setView(mView);
    }

    /**
     * 设置一个View，子类可以复写
     */

    @SuppressWarnings("unchecked")
    private void setView(T view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    /**
     * 给子类使用的获取View的操作
     * 不允许复写
     *
     * @return View
     */
    protected final T getView() {
        return mView;
    }

    @Override
    public void start() {
        // 开始的时候进行Loading调用
        T view = mView;
        if (view != null){
            view.showLoading();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void destroy() {
        T view = mView;
        if (view != null) {
            // 把Presenter设置为NULL
            view.setPresenter(null);
        }
    }
}
