package com.example.common.widget.recycler;

public interface AdapterCallBack<T> {

    void update(T data, RecyclerAdapter.ViewHolder<T> holder);

}
