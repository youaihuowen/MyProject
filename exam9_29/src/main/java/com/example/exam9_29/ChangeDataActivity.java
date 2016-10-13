package com.example.exam9_29;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeDataActivity extends AppCompatActivity {

    String filePath;
    EditText et_name;
    EditText et_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changedata);
        et_name = (EditText) findViewById(R.id.et_name);
        et_num = (EditText) findViewById(R.id.et_num);
        filePath = "data/data/" + getPackageName() + "/databases";

    }

    /**
     * 添加电话
     * @param view
     */
    public void addMessage(View view) {
        String name = null;
        String phoneNum = null;
        String table = getIntent().getStringExtra("tableName");
        name=et_name.getText().toString();
        phoneNum=et_num.getText().toString();
        if(!name.isEmpty() && !phoneNum.isEmpty()){
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(filePath + "/phone.db", null);
            ContentValues mContentValues = new ContentValues();
            mContentValues.put(TableEntry.COLUMN_PHONE_TYPE, name);
            mContentValues.put(TableEntry.COLUMN_PHONE_NUM, phoneNum);
            db.insert(table, null, mContentValues);
            Intent mIntent = new Intent();
            mIntent.putExtra("tablename",table);
            setResult(1,mIntent);
            finish();
        }else {
            Toast.makeText(this,"名称或者号码不能为空", Toast.LENGTH_SHORT).show();
        }


    }
}
