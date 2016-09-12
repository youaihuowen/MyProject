package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.sqlite.TypeEntry;

/**
 * @description 通讯录内容提供者
 * Created by WUYIXIONG on 2016-9-12.
 */
public class MyContentProvider extends ContentProvider {
    //实例化一个urimatcher对象
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //给内容提供者添加一条匹配TypeEntry.TABLE_NAME的uri,uri类型为1
        uriMatcher.addURI("com.example.contacts.provider", TypeEntry.TABLE_NAME, 1);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(TypeEntry.FILE_PATH + "/phone.db", null);
        //要返回给用户的游标
        Cursor mCursor = null;
        switch (uriMatcher.match(uri)) {
            case 1:
                mCursor = db.query(TypeEntry.TABLE_NAME,//表名
                        projection,//要查询的列
                        selection,//查询条件
                        selectionArgs,//查询条件中的参数
                        null,//GROUP BY
                        null,//HAVING
                        sortOrder);//ORDER BY
                break;
        }

        return mCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String mType = null;
        switch (uriMatcher.match(uri)) {
            case 1:
                mType = "vnd.android.cursor.dir/com.example.contacts.PhoneType";
                break;
        }
        return mType;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
