package com.example.classtest_intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * @description intent的起始页
 * created by WUYIXIONG on 2016-09-13
 */
public class StartActivity extends Activity implements View.OnClickListener {

    Button btn_component;
    Button btn_class;
    Button btn_className;
    Button btn_action;
    Button btn_DataAndType;
    Button btn_putExtra;
    Button btn_checkActivity;
    Button btn_multipleFilter;
    Button btn_filter;
    Button btn_IntentTest;

    public final String NEWACTION = "com.example.intent.NEWACTION";
    public final String OLDACTION = "com.example.intent.OLDACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //绑定控件
        btn_class = (Button) findViewById(R.id.btn_start_class);
        btn_className = (Button) findViewById(R.id.btn_start_classname);
        btn_component = (Button) findViewById(R.id.btn_start_component);
        btn_action = (Button) findViewById(R.id.btn_start_action);
        btn_DataAndType = (Button) findViewById(R.id.btn_start_dataandtype);
        btn_putExtra = (Button) findViewById(R.id.btn_start_putextra);
        btn_checkActivity = (Button) findViewById(R.id.btn_start_check);
        btn_multipleFilter = (Button) findViewById(R.id.btn_start_multipleFilter);
        btn_filter = (Button) findViewById(R.id.btn_start_filter);
        btn_IntentTest = (Button) findViewById(R.id.btn_start_IntentTest);

        //设置监听
        btn_className.setOnClickListener(this);
        btn_class.setOnClickListener(this);
        btn_component.setOnClickListener(this);
        btn_action.setOnClickListener(this);
        btn_DataAndType.setOnClickListener(this);
        btn_putExtra.setOnClickListener(this);
        btn_checkActivity.setOnClickListener(this);
        btn_multipleFilter.setOnClickListener(this);
        btn_filter.setOnClickListener(this);
        btn_IntentTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_component:
                startActivityByComponent();
                break;
            case R.id.btn_start_class:
                startActivityByClass();
                break;
            case R.id.btn_start_classname:
                starActivityByClassName();
                break;
            case R.id.btn_start_action:
                starActivityByAction();
                break;
            case R.id.btn_start_dataandtype:
                startActivityByDataAndTyp();
                break;
            case R.id.btn_start_putextra:
                startActivityWithBundle();
                break;
            case R.id.btn_start_check:
                checkActivity();
                break;
            case R.id.btn_start_multipleFilter:
                multipleFilter();
                break;
            case R.id.btn_start_filter:
                startActivityByFilter();
                break;
            case R.id.btn_start_IntentTest:
                startIntentTestActivity();
                break;
        }
    }

    /**
     * @descroption 用setComponent方法显式打开activity
     */
    private void startActivityByComponent() {
        //实例化一个intent
        Intent mIntent = new Intent();
        //一个指向系统设置页的组件名
        ComponentName componentName = new ComponentName(
                "com.android.settings",
                "com.android.settings.Settings");
        //给Intent设置要启动的组件名称(显式Intent),系统设置页
        mIntent.setComponent(componentName);
        //启动这一组件
        startActivity(mIntent);
    }

    /**
     * @descroption 用setClass方法显式打开activity 只能打开本应用中的activity
     */
    private void startActivityByClass() {
        //实例化一个intent
        Intent mIntent = new Intent();

        //给Intent设置要启动的组件名称(显式Intent),系统设置页
        mIntent.setClass(this, BActivity.class);
        //启动这一组件
        startActivity(mIntent);
    }

    /**
     * @descroption 用setClassName方法显式打开activity
     */
    private void starActivityByClassName() {
        //实例化一个intent
        Intent mIntent = new Intent();
        //给Intent设置要启动的组件名称(显式Intent),系统设置页
        mIntent.setClassName("com.android.contacts", "com.android.contacts.activities.PeopleActivity");
        //启动这一组件
        startActivity(mIntent);
    }

    /**
     * @descroption 用setAction方法隐式打开activity
     */
    private void starActivityByAction() {
        //实例化一个intent
        Intent mIntent = new Intent();
        //指定需要匹配的操作
        mIntent.setAction("com.example.action.MYACTION");
//        mIntent.setAction(Intent.ACTION_VIEW);
        //启动这一组件
        startActivity(mIntent);
    }

    /**
     * @descroption 通过指定Data和MIME来隐式启动某些组件
     */
    private void startActivityByDataAndTyp() {
        //确定图片在设备中的位置
        File file = new File(
                //文件的目录,外部共有存储器中的图片目录
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "abc.jpeg");
        Log.i("tig", file.getPath());
        Log.i("tig", file.exists() + " ");
        if (file.exists()) {
            //返回该文件的URI
            Uri mUri = Uri.fromFile(file);
            //声明一个默认的intent
            Intent mIntent = new Intent();
            //指定需要匹配的操作
            mIntent.setAction(Intent.ACTION_VIEW);
            //设置Intent的Data和MIME类型
            mIntent.setDataAndType(mUri, "image/*");
            //启动这一组件
            startActivity(mIntent);
        }
    }
    //启动某个activity的同时用bundle传递参数
    private void startActivityWithBundle() {
        Intent mIntent = new Intent(this, DActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("key", "bundle传递过来的数据");
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }

    /**
     * @description 检查是否有匹配的activity
     * 注释部分为强制使用意图选择器
     */
    private void checkActivity(){
        //创建一个隐式intent
        Intent mIntent = new Intent();
        //设置其动作
        mIntent.setAction(Intent.ACTION_SEND);
        //设置其类型
        mIntent.setType("text/plain");
//        //创建意图选择器
//        Intent mChooser = Intent.createChooser(mIntent, "选择应用");
        //判断系统组件中有无与之匹配的组件
        if (mIntent.resolveActivity(getPackageManager()) != null){
            startActivity(mIntent);
//            startActivity(mChooser);
        }else{
            Toast.makeText(this, "没有找到匹配的组件", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @description 多重过滤器匹配
     */
    private void multipleFilter(){
        //两组可过滤的Intent
        Intent newIntent = new Intent(NEWACTION);
        Intent oldIntent = new Intent(OLDACTION);
        //启动之一检查是否可过滤
        startActivity(oldIntent);

    }
    /**
     * @description 通过匹配某个/些 intent过滤器来启动组件
     */
    private void startActivityByFilter(){
        //指定一个隐式的Intent
        Intent mIntent = new Intent();
        //设置动作
        mIntent.setAction(Intent.ACTION_VIEW);
        //设置数据类型
        mIntent.setType("image/*");
        //检查并开启组件
        if (mIntent.resolveActivity(getPackageManager())!= null){
            startActivity(mIntent);
        }
    }

    /**
     * @description 测试intent过滤方法
     */
    private void startIntentTestActivity(){
        /*
        1.指定intent中的action和category，只要某个过滤器中存在相应的内容（过滤器中的元素可以超出但不可以缺少）
        则会匹配成功，不可单独使用category进行匹配必须与action结合

        2.Intent可以使用单独的data进行匹配，使用该Intent是实际上默认添加了类似VIEW和EDIT之类的操作

        3.若DATA中只指定了MIME类型，则默认的Uri会批匹配Content和File架构，若显示指定uri，则必须匹配该uri

        4.隐式Intent过滤器中必须有Action,否则不会正确匹配
         */
//        //Intent 中含有某个目标过滤器的Action则会匹配成功
//        //指定一个隐式Intent
//        Intent mIntent = new Intent(Intent.ACTION_VIEW);
//        //启动对应组件
//        startActivity(mIntent);


//        //Intent中含有目标过滤器中的某个category，则会成功匹配
//        //指定一个隐式Intent
//        Intent mIntent = new Intent();
//        //指定它的操作
//        mIntent.setAction(Intent.ACTION_VIEW);
//        //指定它的类别
//        mIntent.addCategory("com.example.classIntent.test");
//        //启动对应组件
//        startActivity(mIntent);

        //指定一个隐式的Intent
        Intent mIntent = new Intent();
        //指定它的数据类别
        mIntent.setType("image/*");
        //启动对应组件
        startActivity(mIntent);

        //检查对应的activity
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(mIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        Log.i("tag", list.size()+" ");

    }
}
