package com.example.classtest.datastorage;

import android.os.Bundle;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class InternalStorageActivity extends BaseActivity {

    TextView tv_raw;
    TextView tv_file;
    //要新建file的名字
    private String FILENAME="internal file";
    //要写入file的字符串
    private String FILEDATA="写入file的数据";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_internal;
    }

    @Override
    protected void initView() {
        tv_file=(TextView) findViewById(R.id.tv_internal_file);
        tv_raw=(TextView) findViewById(R.id.tv_internal_raw);

        writeFile(FILENAME);//创建文件写入内容
        readFile(FILENAME);//从file中读出内容
        readRaw();//从raw中读出内容
    }

    @Override
    protected void setLisetner() {

    }

    /**
     * @description 向内部存储器中写入文件
     * @param name  写入文件的文件名
     */
    private void writeFile(String name){
        try {
            //创建或者替换一个文件，第一个参数为文件名，第二个参数为文件模式
            FileOutputStream output=openFileOutput(name,MODE_PRIVATE);
            //向文件中写入数据
            output.write(FILEDATA.getBytes("UTF-8"));
            //关闭数据流
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 读取内部存储器中的文件
     * @param name 读取文件的文件名
     */
    private void readFile(String name){
        //运行期文件
        try {
            //创建读取文件的输入流
            FileInputStream input=openFileInput(name);
            //创建保存数据的临时字符
            String temp="";
            //创建缓冲区
            byte[] buffer=new byte[1024];
            //向缓冲区中循环读入数据，直至数据读完（读到-1时）
            while (input.read(buffer)!= -1){
                temp=temp+new String(buffer);
            }
            //关闭数据流
            input.close();
            //更新在UI上
            tv_file.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void readRaw(){
        //编译期文件
        try {
            //创建读取raw文件下文件的输入流
            InputStream ip = getResources().openRawResource(R.raw.rawfile);
            //创建保存数据的临时字符
            String temp="";
            //创建缓冲区
            byte[] buffer=new byte[1024];
            //向缓冲区中循环读入数据，直至数据读完（读到-1时）
            while (ip.read(buffer)!= -1){
                temp=temp+new String(buffer);
            }
            //关闭数据流
            ip.close();
            //更新在UI上
            tv_raw.setText(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
