package com.example.improject.widget.recycler;

public interface AdapterCallBack<T> {

    void update(T data, RecyclerAdapter.ViewHolder<T> holder);

}
