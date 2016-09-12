package com.example.sqlite;

import android.provider.BaseColumns;

/**
 * Created by WUYIXIONG on 2016-8-29.
 * @description 电话类型sql契约类
 */
public class TypeEntry implements BaseColumns{
    //数据库路径
    public static final String FILE_PATH ="data/data/com.example.myapplication/database";
    //表名
    public static final String TABLE_NAME="phonetype";
    //列名
    public static final String COLUMNS_NAME_TYPE="Type";
    public static final String COLUMNS_NAME_SUBTABLE="SubTable";
    //创建表格的sql语句
    public static final String SQL_CREATE_TABLE="create table "+TABLE_NAME+"("+_ID+
            " integer primary key,"+COLUMNS_NAME_TYPE+" text,"+COLUMNS_NAME_SUBTABLE+" text)";
    //删除表格的sql语句
    public static final String SQL_DELETE_TABLE="drop table if exists "+TABLE_NAME;
    //要跳转到的表名
    public static final String SUB_LOCAL = "local";
    public static final String SUB_CATERING = "Catering";
    public static final String SUB_PUBLICSERVICE = "PublicService";
    public static final String SUB_OPERATOR = "Operator";
    public static final String SUB_EXPERSSAGE = "Expressage";
    public static final String SUB_TRAVEL= "Travel";
    public static final String SUB_BANK= "Bank";
    public static final String SUB_INSURANCE= "Insurance";
    public static final String SUB_AFTERSALE= "AfterSale";

}
