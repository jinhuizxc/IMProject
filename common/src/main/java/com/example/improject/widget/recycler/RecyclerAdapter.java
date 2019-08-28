package com.example.improject.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.improject.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Recycler的封装
 * <p>
 * 1. 定义泛型T
 */
public abstract class RecyclerAdapter<T>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<T>>
        implements View.OnClickListener, View.OnLongClickListener,
        AdapterCallBack<T> {

    private List<T> mDataList;

    private AdapterListener<T> mListener;

    /**
     * 构造函数模块
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<T> mListener) {
        this(new ArrayList<T>(), mListener);
    }

    public RecyclerAdapter(List<T> mDataList, AdapterListener<T> mListener) {
        this.mDataList = mDataList;
        this.mListener = mListener;
    }

    /**
     * 创建一个ViewHolder
     *
     * @param parent RecyclerView
     * @param type   界面类型, 约定为xml布局的id
     * @return
     */
    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(type, parent, false);
        // 通过子类必须实现的方法，得到一个ViewHolder
        ViewHolder<T> holder = onCreateViewHolder(root, type);

        // 设置view的tag为ViewHolder, 进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);
        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        holder.unbinder = ButterKnife.bind(holder, root);
        holder.callBack = this;

        return holder;
    }


    /**
     * 复写默认的布局类型返回
     * @param position
     * @return  类型，其实复写返回的都是xml文件的id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的返回类型
     * @param position
     * @param data
     * @return
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, T data);

    protected abstract ViewHolder<T> onCreateViewHolder(View root, int type);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        // 得到需要绑定的数据
        T data = mDataList.get(position);
        // 触发Holder的绑定方法
        holder.onBind(data);
    }

    /**
     * 得到当前集合的数据数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知插入
     * @param data
     */
    public void add(T data){
        mDataList.add(data);
//        notifyDataSetChanged();
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一推数据，并通知这段集合更新
     * @param dataList
     */
    public void add(T... dataList){
        if (dataList != null && dataList.length > 0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入一推数据，并通知这段集合更新
     * @param dataList
     */
    public void add(Collection<T> dataList){
        if (dataList != null && dataList.size() > 0){
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     */
    public void replace(Collection<T> dataList){
        mDataList.clear();
        if (dataList == null || dataList.size() == 0){
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null){
            // 得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
//            this.mListener.onItemClick(viewHolder, (T) viewHolder.mData);
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public void update(T data, ViewHolder<T> holder) {
        // 得到当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            // 进行数据的移除与更新
            mDataList.remove(pos);
            mDataList.add(pos, data);
            // 通知这个坐标下的数据有更新
            notifyItemChanged(pos);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null){
            // 得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }


    /**
     * 设置适配器的监听
     * @param mListener
     */
    public void setListener(AdapterListener<T> mListener) {
        this.mListener = mListener;
    }

    /**
     * 返回整个集合
     *
     * @return List<Data>
     */
    public List<T> getItems() {
        return mDataList;
    }

    /**
     * 点击监听
     * @param <T>
     */
    public interface AdapterListener<T>{
        void onItemClick(RecyclerAdapter.ViewHolder holder, T data);
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, T data);
    }

    /**
     * 对回调接口做一次实现AdapterListener
     *
     * @param <T>
     */
    public static abstract class AdapterListenerImpl<T> implements AdapterListener<T>{

        @Override
        public void onItemClick(ViewHolder holder, T data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, T data) {

        }
    }

    // 数据与viewHolder绑定在一起
    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

        private Unbinder unbinder;
        protected T mData;
        private AdapterCallBack<T> callBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data
         */
        void bind(T data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候的回调，必须复写
         *
         * @param data
         */
        protected abstract void onBind(T data);


        /**
         * holder自己对自己定义的holder进行更新操作
         *
         * @param data
         */
        public void updateData(T data) {
            if (this.callBack != null) {
                this.callBack.update(data, this);
            }
        }
    }
}
