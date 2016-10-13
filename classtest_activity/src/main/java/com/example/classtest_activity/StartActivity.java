package com.example.classtest_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends BaseActivity implements View.OnClickListener{

    Button btn_forResult;//开启startActivityForResult的按钮
    Button btn_finishActivity;//演示在本activity关闭另一个activity的按钮
    Button btn_oneKeyExit;
    public static final int REQUEST_CODE_FINISH=1;
    public static final int REQUEST_CODE_EXIT=2;

    @Override
    protected int setContent() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
            btn_forResult=(Button) findViewById(R.id.btn_start_forResult);
            btn_finishActivity=(Button) findViewById(R.id.btn_start_finishActivity);
            btn_oneKeyExit=(Button) findViewById(R.id.btn_start_oneKeyExit);
    }


    @Override
    protected void setLisetner() {
        btn_forResult.setOnClickListener(this);
        btn_finishActivity.setOnClickListener(this);
        btn_oneKeyExit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent mIntent=new Intent();
        switch (v.getId()){
            case R.id.btn_start_forResult:
                startActivity(new Intent(this, FirstActivity.class));
                break;
            case R.id.btn_start_finishActivity:
                mIntent.setClass(this,FirstActivity.class);
                startActivityForResult(mIntent, REQUEST_CODE_FINISH);
                break;
            case R.id.btn_start_oneKeyExit:
                mIntent.setClass(this,AActivity.class);
                startActivityForResult(mIntent,REQUEST_CODE_EXIT);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finishActivity(REQUEST_CODE_FINISH);
    }
}
