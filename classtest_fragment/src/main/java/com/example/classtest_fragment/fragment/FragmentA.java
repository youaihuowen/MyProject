package com.example.classtest_fragment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.classtest_fragment.R;


/**
 * Created by WUYIXIONG on 2016-10-10
 * FragmentA
 */
public class FragmentA extends Fragment implements View.OnClickListener{

    private Button btn_show;
    private Button btn_change;


    /**
     * 碎片中按钮点击的回调接口
     */
    public interface OnFragButtonListener{
        /**
         * 当Fragment中的按钮被点击时调用
         */
        void onClickSync(String data);

        /**
         *当Fragment中的问题解决完毕时被调用
         * @param result 计算结果
         */
        void onClickAsync(int result);
    }

    OnFragButtonListener onFragButtonListener=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("tag", "A_onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        btn_change = (Button) view.findViewById(R.id.btn_fragA_change);
        btn_show = (Button) view.findViewById(R.id.btn_fragA_show_sync);


        return view;
    }

    /**
     * 改变activity中按钮的字体
     */
    private void change() {
        btn_change = (Button) getActivity().findViewById(R.id.btn_frag2_add);
        btn_change.setText("change");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("tag", "A_onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("tag", "A_onActivityCreated");
        //要等activity布局加载完后才能修改其中的控件
        btn_change.setOnClickListener(this);
        btn_show.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragButtonListener=(OnFragButtonListener)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("tag", "A_onAttach");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fragA_change:
                change();
                break;
            case R.id.btn_fragA_show_sync:
                if (onFragButtonListener != null){
                    onFragButtonListener.onClickSync("测试参数");
                }
                break;
        }
    }

    /**
     * 解决activity提出的问题的方法(加法)
     * @param a 加数1
     * @param b 加数2
     */
    public void excuteQuestion(int a,int b){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onFragButtonListener.onClickAsync(a+b);
    }
}
