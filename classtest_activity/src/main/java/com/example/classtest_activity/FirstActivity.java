package com.example.classtest_activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

public class FirstActivity extends Activity {

    public static final String REQUEST = "request";
    public static final int REQUEST_CODE_LOGIN = 0;
    public static final int REQUEST_CODE_REGISTER = 1;
    public static final int REQUEST_CODE_OTHER = 2;
    public static final int REQUEST_CODE_CONTACTS = 3;

    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        tv_result = (TextView) findViewById(R.id.tv_first_result);
    }

    /**
     * @param view 点击的视图
     * @description 点击登录按钮后跳转到下一个页面
     */
    public void login(View view) {
        Intent mIntent = new Intent(this, SecondActivity.class);
        mIntent.putExtra(REQUEST, "Login");
        startActivityForResult(mIntent, REQUEST_CODE_LOGIN);
    }

    /**
     * @param view 点击的视图
     * @description 点击注册按钮后跳转到下一个页面
     */
    public void register(View view) {
        Intent mIntent = new Intent(this, SecondActivity.class);
        mIntent.putExtra(REQUEST, "Register");
        startActivityForResult(mIntent, REQUEST_CODE_REGISTER);
    }

    /**
     * @param view 点击的视图
     * @description 点击其他按钮后跳转到下一个页面
     */
    public void other(View view) {
        Intent mIntent = new Intent(this, SecondActivity.class);
        mIntent.putExtra(REQUEST, "Other");
        startActivityForResult(mIntent, REQUEST_CODE_OTHER);
    }

    /**
     * @param view 点击的视图
     * @description 点击通讯录按钮后跳转到下一个页面
     */
    public void contacts(View view) {
        Intent mIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(mIntent, REQUEST_CODE_CONTACTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                switch (requestCode) {
                    case REQUEST_CODE_LOGIN:
                        tv_result.setText(data.getStringExtra(SecondActivity.RESULT));
                        break;
                    case REQUEST_CODE_REGISTER:
                        tv_result.setText(data.getStringExtra(SecondActivity.RESULT));
                        break;
                    case REQUEST_CODE_CONTACTS:
                        Cursor cursor = getContentResolver().query(
                                data.getData(),
                                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                                null,
                                null,
                                null
                        );
                        if (cursor.moveToFirst()){// True if the cursor is not empty
                            int index = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
                            String name = cursor.getString(index);
                            tv_result.setText(name);
                        }

                }
                break;
            case Activity.RESULT_CANCELED:
                tv_result.setText(data.getStringExtra(SecondActivity.RESULT));
                break;
        }
    }
}
