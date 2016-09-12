package com.example.exam9_9.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.exam9_9.R;

public class DetailsActivity extends BaseActivity {
    TextView tv_name;
    TextView tv_message;

    @Override
    protected int setContent() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        tv_message = (TextView) findViewById(R.id.tv_detail_message);
        tv_name = (TextView) findViewById(R.id.tv_detail_name);
        initText();
    }

    @Override
    protected void setLisetner() {

    }
    private void initText(){
        Bundle bundle=getIntent().getBundleExtra("sms");
        String name = (String) bundle.get("name");
        String message = (String) bundle.get("message");
        tv_name.setText("来自："+name);
        tv_message.setText(message);
    }
}
