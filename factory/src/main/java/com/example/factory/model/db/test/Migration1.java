package com.example.factory.model.db.test;

import com.example.factory.model.db.AppDataBase;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * 数据库升级（增加表、修改表字段等）
 * 如果要增加表，除了要创建表以外，还要修改数据库版本号，如果没有修改数据库版本号，那么再调表对象的时候就会报出一个经典错误，no such table，但凡报这个错误都是你数据版本号没有修改。
 * 如果要修改表的字段或者增加字段，相对来说稍微麻烦一点。DBFLOW专门提供了AlterTableMigration<T>这个类来修改表相关的资料。以User2Model这个表为例，假如说我们要增加一个字段timeStamp是long类型的，不仅改数据库版本号，同时在类对象中增加 @Column
 * private long timeStamp;  字段以外，还需要增加咱们刚才提到的AlterTableMigration的子类来修改
 */
@Migration(version = AppDataBase.VERSION, database = AppDataBase.class)
public class Migration1 extends AlterTableMigration<User2Model> {
    public Migration1(Class<User2Model> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.INTEGER, User2Model_Table.timeStamp.getNameAlias().name());
    }
}
