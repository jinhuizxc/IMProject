package com.example.factory.model.db;

import com.example.factory.utils.DiffUiDataCallback;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 我们APP中的基础的一个BaseDbModel，
 *  基础了数据库框架DbFlow中的基础类
 *  同时定义类我们需要的方法
 *
 *  TODO 作者这么做的意思是让BaseDbModel支持更多数据类型，支持泛型
 */
public abstract class BaseDbModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model> {
}
