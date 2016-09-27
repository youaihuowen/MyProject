package com.example.classtest_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

    }

    /**
     * 常规方法打开D Activity
     * @param view
     */
    public void startDActivity(View view){
        startActivity(new Intent(this, DActivity.class));
    }

    /**
     * @description 用FLAG_ACTIVITY_CLEAR_TOP跳转到 B Activity
     * @param view
     */
    public void startBActivity(View view){
        Intent mIntent = new Intent(this, BActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
    }
    /**
     * @description 用FLAG_ACTIVITY_NEW_TASK跳转到 D Activity
     * @param view
     */
    public void startDActivity_newTask(View view){
        Intent mIntent = new Intent(this, DActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
}
