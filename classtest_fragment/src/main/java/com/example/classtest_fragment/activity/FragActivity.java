package com.example.classtest_fragment.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.classtest_fragment.R;
import com.example.classtest_fragment.fragment.FragmentA;
import com.example.classtest_fragment.fragment.FragmentB;
import com.example.classtest_fragment.fragment.FragmentC;
import com.example.classtest_fragment.fragment.FragmentD;

import java.util.ArrayList;
import java.util.List;

public class FragActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    FragmentA fragmentA;//碎片A
    FragmentB fragmentB;//碎片B
    FragmentC fragmentC;//碎片B
    FragmentD fragmentD;//碎片B

    FragmentManager fm;//碎片管理器
    FragmentTransaction ft;//碎片事务

    RadioGroup rg;//单选按钮组

    //fragment list 碎片列表
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
        //加载单选组
        rg=(RadioGroup)findViewById(R.id.rg_main);
        //获得碎片管理器
        fm=getSupportFragmentManager();
        //给单选按钮设置监听
        rg.setOnCheckedChangeListener(this);


        //若FragmentA未实例化过
        if (fragmentA==null){
            //实例化
            fragmentA=new FragmentA();
            //实例化一个碎片事务
            ft=fm.beginTransaction();
            //执行一个添加碎片的事务
            ft.add(R.id.fl_container,fragmentA);
            //提交事务
            ft.commit();
            //添加至碎片列表
            fragmentList.add(fragmentA);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        hideAllFragment();
        switch (checkedId){
            case R.id.rb_a:
                //若FragmentA未实例化过
                if (fragmentA==null){
                    //实例化
                    fragmentA=new FragmentA();
                    //实例化一个碎片事务
                    ft=fm.beginTransaction();
                    //执行一个添加碎片的事务
                    ft.add(R.id.fl_container,fragmentA);
                    //提交事务
                    ft.commit();
                    //添加至碎片列表
                    fragmentList.add(fragmentA);
                }else {
                fm.beginTransaction().show(fragmentA).commit();
                }
                break;
            case R.id.rb_b:
                //若FragmentB未实例化过
                if (fragmentB==null) {
                    //实例化
                    fragmentB = new FragmentB();
                    //添加到碎片管理器中
                    fm.beginTransaction().add(R.id.fl_container, fragmentB).commit();
                    //添加至碎片列表
                    fragmentList.add(fragmentB);
                }else {
                    fm.beginTransaction().show(fragmentB).commit();
                }
                break;
            case R.id.rb_c:
                //若FragmentC未实例化过
                if (fragmentC==null) {
                    //实例化
                    fragmentC = new FragmentC();
                    //添加到碎片管理器中
                    fm.beginTransaction().add(R.id.fl_container, fragmentC).commit();
                    //添加至碎片列表
                    fragmentList.add(fragmentC);
                }else {
                    fm.beginTransaction().show(fragmentC).commit();
                }
                break;
            case R.id.rb_d:
                //若FragmentD未实例化过
                if (fragmentD==null) {
                    //实例化
                    fragmentD = new FragmentD();
                    //添加到碎片管理器中
                    fm.beginTransaction().add(R.id.fl_container, fragmentD).commit();
                    //添加至碎片列表
                    fragmentList.add(fragmentD);
                }else {
                    fm.beginTransaction().show(fragmentD).commit();
                }
                break;
        }
    }

    private void hideAllFragment(){
        for (Fragment f:fragmentList){
            fm.beginTransaction().hide(f).commit();
        }
    }
}
