package com.example.classtest_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);
    }

    /**
     * @description 在第一个栈的末尾添加C Activity
     * @param view
     */
    public void startCActivity(View view){
        Intent mIntent = new Intent(this, CActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
    /**
     * @description 在第一个栈的末尾用FLAG_ACTIVITY_SINGLE_TO方法添加C Activity
     * @param view
     */
    public void startCActivity_singleTop(View view){
        Intent mIntent = new Intent(this, CActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(mIntent);
    }
}
