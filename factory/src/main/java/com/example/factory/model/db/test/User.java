package com.example.factory.model.db.test;

import com.example.factory.model.db.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.UUID;

@Table(database = AppDataBase.class)
public class User {
    @PrimaryKey
    public
    UUID id;

    @Column
    public
    String name;

    @Column
    public
    int age;
}