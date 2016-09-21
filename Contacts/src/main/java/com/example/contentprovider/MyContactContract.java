package com.example.contentprovider;

import android.net.Uri;

/**
 * @description 协定类
 * Created by WUYIXIONG on 2016-9-13.
 */
public final class MyContactContract {
    public static final String AUTHORITY = "com.example.contacts";
    public static final Uri AUTHORITY_URI = Uri.parse("content://"+AUTHORITY);
    public static final String TABLENAME = "phoneType";
    public static final Uri TABLENAME_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLENAME);
    public static final String DATABASE_PATH = "data/data/com.example.myapplication/database";
    public static final String COLUMNS_NAME_TYPE = "Type";

}
