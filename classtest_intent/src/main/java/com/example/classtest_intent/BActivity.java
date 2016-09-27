package com.example.classtest_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

    }

    /**
     * @description 正常的方法跳转到C Activity
     * @param view
     */
    public void startCActivity(View view){
        startActivity(new Intent(this, CActivity.class));
    }
    /**
     * @description 用FLAG_ACTIVITY_NO_HISTORY方法跳转到C Activity
     * @param view
     */
    public void startCActivity_noHistory(View view){
        Intent mIntent = new Intent(this, CActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(mIntent);
    }
}
