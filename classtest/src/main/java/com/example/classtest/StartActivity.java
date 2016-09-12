package com.example.classtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.classtest.animation.MainActivity;
import com.example.classtest.datastorage.SharedPreActivity;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    Button btn_anim;
    Button btn_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        btn_anim=(Button) findViewById(R.id.btn_start_anim);
        btn_data=(Button) findViewById(R.id.btn_start_data);
    }

    @Override
    protected void setLisetner() {
        btn_data.setOnClickListener(this);
        btn_anim.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_anim:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_start_data:
                startActivity(new Intent(this, SharedPreActivity.class));
                break;
        }
    }
}
