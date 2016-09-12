package com.example.classtest.datastorage;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.util.ArrayList;

public class ContentProviderActivity extends BaseActivity {

    TextView tv_message;
    TextView tv_message1;
    EditText et_search;
    Button btn_search;
    //    TextView tv_message2;
    TextView tv_item_id;
    TextView tv_item_name;
    SimpleCursorAdapter mCursorAdapter;
    ListView lv_message;


    @Override
    protected int setContent() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void initView() {
        tv_message = (TextView) findViewById(R.id.tv_cp);
        tv_message1 = (TextView) findViewById(R.id.tv_cp1);
//        tv_message2 = (TextView) findViewById(R.id.tv_cp3);
        et_search = (EditText) findViewById(R.id.et_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        tv_item_id = (TextView) findViewById(R.id.tv_item_cp_id);
        tv_item_name = (TextView) findViewById(R.id.tv_item_cp_name);
        lv_message = (ListView) findViewById(R.id.lv_cp_show);
//        queryImage();
//        querySingleImage();
        batchOperationContact();
    }

    @Override
    protected void setLisetner() {

    }

    /**
     * @description 使用contentprovider 的方式查询手机中的图片信息
     */
    private void queryImage() {
        //查询指定uri的content provider，返回一个游标
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//公共图片的uri（相当于一个表名）
                null,////相当于COLUMNS,这里null指的是返回整张表，使用"*"可能会报错
                null,////相当于WHERE
                null,////相当于WHERE ARGS
                null);
        //判断游标状态，不为0并且有数据时遍历
        if (cursor != null && cursor.getCount() != 0) {
            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则游标的位置会在-1停留
            cursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do {
                String id = cursor.getString(idIndex);
                String height = cursor.getString(heightIndex);
                temp += id + "\n" + height + "\n\n";
            } while (cursor.moveToNext());
            cursor.close();
            tv_message.setText(temp);
        } else {
            tv_message.setText("没有内容");
        }
    }

    /**
     * @description 使用ContentProvider的方式查询手机中的单张图片信息
     */
    private void querySingleImage() {
        Uri singleUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 39490);
        //查询指定uri的content provider，返回一个游标
        Cursor cursor = getContentResolver().query(
                singleUri,//公共图片的uri（相当于一个表名）
                null,////相当于COLUMNS,这里null指的是返回整张表，使用"*"可能会报错
                null,////相当于WHERE
                null,////相当于WHERE ARGS
                null);
        //判断游标状态，不为0并且有数据时遍历
        if (cursor != null && cursor.getCount() != 0) {
            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
            //游标移动到第一行，否则游标的位置会在-1停留
            cursor.moveToFirst();
            //用来存储临时拼接数据的字符串
            String temp = "";
            do {
                String id = cursor.getString(idIndex);
                String height = cursor.getString(heightIndex);
                temp += id + "\n" + height + "\n\n";
            } while (cursor.moveToNext());
            cursor.close();
            tv_message1.setText(temp);
        } else {
            tv_message1.setText("没有内容");
        }
    }

    /**
     * 使用ContentProvider的方式查询手机中的单张图片信息
     *
     * @param view 点击的视图
     */
    public void query(View view) {
        String[] mProjection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME};
        String search;
        String mSelectionClause = null;//查询的条件
        String[] mSelectionArgs = {""};//查询条件中占位符补全
        if (TextUtils.isEmpty(search = et_search.getText().toString())) {
            mSelectionClause = null;
            mSelectionArgs = null;
        } else {
            mSelectionClause = MediaStore.Images.Media._ID + "=?";
            mSelectionArgs[0] = search;
        }
        //构建简易适配器展示数据的控件列表
        int[] listItems = {
                R.id.tv_item_cp_id,
                R.id.tv_item_cp_name
        };
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//公共图片的uri（相当于一个表名）
                mProjection,
                mSelectionClause,
                mSelectionArgs,
                null);
        //判断游标状态
        if (cursor == null) {//游标为空时，打印错误信息
            Log.e("err", "游标为空");
        } else if (cursor.getCount() < 1) {//数据条数小于1时
            Toast.makeText(this, "查询条件错误或查询数据为空", Toast.LENGTH_SHORT).show();

        } else {//游标不为空，并且有数据

            //确定需要的数据的下标，减少IndexOrThrow的调用，提高效率
            int idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int displayIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            //游标移动到第一行，否则游标的位置会在-1停留
            cursor.moveToFirst();
            mCursorAdapter = new SimpleCursorAdapter(this,//上下文
                    R.layout.item_contentprovider,//字item布局
                    cursor, //保存结果的游标
                    mProjection, //要查询的列
                    listItems, //展示查询列每行内容的控件组
                    0);//
            lv_message.setAdapter(mCursorAdapter);
        }

    }

    public void insert(View view) {
        Uri newUri;
        //定义一条待插入的数据
        ContentValues mInsertValus = new ContentValues();
        mInsertValus.put(MediaStore.Images.Media.TITLE, "wu");
        mInsertValus.put(MediaStore.Images.Media.DISPLAY_NAME, "yixiong");

        newUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//要插入位置的uri
                mInsertValus);//待插入的数据

    }

    public void update(View view) {
        ContentValues mUpdateValus = new ContentValues();
        String selectionClause = MediaStore.Images.Media.DISPLAY_NAME + " like ?";
        String[] selectionArgs = {"yixiong"};
        int mRowsUpdate;
        mUpdateValus.put(MediaStore.Images.Media.DISPLAY_NAME, "已更改");
        mRowsUpdate = getContentResolver().update(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mUpdateValus,
                selectionClause,
                selectionArgs);
    }

    public void delete(View view) {
        String selectionClause = MediaStore.Images.Media.TITLE + " like ?";
        String[] selectionArgs = {"wu"};
        int mRowsDeleted = 0;
        mRowsDeleted = getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                selectionClause,
                selectionArgs);
    }

    /**
     * @description 批处理方法对通讯录进行操作
     */
    private void batchOperationContact() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(//插入数据
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "accountType")//类型，必填
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "accountName")//名字，必填
                        .build()
        );
        ops.add(//插入电话号码
                //要插入数据的uri
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        //根据批处理的第一次操作所返回的ContactsContract.Data.CONTACT_ID来进行插入数据操作
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        //DATA1代表了电话号码,给该联系人插入一条电话数据
                .withValue(ContactsContract.Data.DATA1, "e")
                        //在给Data表插入数据时必须制定该条数据的MIME类型
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .build()
        );
        String selectionClause = ContactsContract.RawContacts._ID + " = ?";//查询条件
        String[] selectionArgs = {"1"};//查询条件中占位符补齐
        ops.add(//删除数据
                ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                        .withSelection(selectionClause, selectionArgs)
                        .build()//构建
        );
        String[] selectionArgs1 = {"2"};
        ops.add(//断点查询数据
                ContentProviderOperation.newAssertQuery(ContactsContract.RawContacts.CONTENT_URI)
                        .withSelection(selectionClause, selectionArgs1)//查询的语句
                        .withExpectedCount(1)//期望查处的条数
                        .build()//构建


        );
        try {
            ContentProviderResult[] result =
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            String Temp = "";
            for (int i = 0; i < result.length; i++) {
                Temp += result[i].toString() + "\n\n";
            }
            tv_message.setText(Temp);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

}
