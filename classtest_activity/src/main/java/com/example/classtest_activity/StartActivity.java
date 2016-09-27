package com.example.classtest_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity implements View.OnClickListener{

    Button btn_forResult;//开启startActivityForResult的按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        setLisnter();

    }

    public void initView(){
        btn_forResult=(Button) findViewById(R.id.btn_start_forResult);
    }
    public void setLisnter(){
        btn_forResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_forResult:
                startActivity(new Intent(this, FirstActivity.class));
                break;
        }
    }
}
