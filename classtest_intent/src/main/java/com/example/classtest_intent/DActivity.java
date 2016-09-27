package com.example.classtest_intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DActivity extends AppCompatActivity {
    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        tv_show = (TextView) findViewById(R.id.tv_d_show);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            tv_show.setText(bundle.getString("key"));
        }
    }
    /**
     * @description 用FLAG_ACTIVITY_NEW_TASK跳转到 E Activity
     * @param view
     */
    public void startEActivity_newTask(View view){
        Intent mIntent =new Intent(this, EActivity.class);
        //如果该句注释掉的化会默认在当前栈中加入一个activity
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }
}
