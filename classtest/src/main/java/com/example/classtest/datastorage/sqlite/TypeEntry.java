package com.example.classtest.datastorage.sqlite;

import android.provider.BaseColumns;

/**
 * Created by WUYIXIONG on 2016-8-29.
 * @description 电话类型sql契约类
 */
public class TypeEntry implements BaseColumns{
    //表名
    public static final String TABLE_NAME="phonetype";
    //列名
    public static final String COLUMNS_NAME_TYPE="type";
    public static final String COLUMNS_NAME_SUBTABLE="subtable";
    //创建表格的sql语句
    public static final String SQL_CREATE_TABLE="create table "+TABLE_NAME+"("+_ID+
            " integer primary key,"+COLUMNS_NAME_TYPE+" text,"+COLUMNS_NAME_SUBTABLE+" text)";
    //删除表格的sql语句
    public static final String SQL_DELETE_TABLE="drop table if exists "+TABLE_NAME;


}
