package com.example.classtest.datastorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CacheActivity extends BaseActivity {

    //文件名
    private final String FILE_NAME="cacheFile";
    //文件内容
    private final String DATA="HELLO CACHE FILE";
    //显示文件内容的text
    TextView tv_cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_cache;
    }

    @Override
    protected void initView() {
        tv_cache=(TextView) findViewById(R.id.tv_cache);
        //载入写文件的方法
        writeFile(FILE_NAME);
        //载入读文件的方法
        readFile(FILE_NAME);
        deleteCache(getCacheDir()+"/"+FILE_NAME);
    }

    @Override
    protected void setLisetner() {

    }

    /**
     * @description 写缓冲文件的函数
     * @param fileName  文件名
     */
    private void writeFile(String fileName){
        //根据缓存文件创建一个file
        File file=new File(getCacheDir(),fileName);

        try {
            //开启缓冲文件的输出流
            FileOutputStream output=new FileOutputStream(file);
            //向文件中写数据
            output.write(DATA.getBytes("UTF-8"));
            //关闭流
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @description 从cache文件中读入数据
     * @param fileName 文件名
     */
    private void readFile(String fileName){
        //根据缓存文件创建一个file
        File file=new File(getCacheDir(),fileName);
        //临时存放数据的字符串
        String temp="";
        //数据缓冲区
        byte[] buffer=new byte[1024];
        try {
            //开启缓冲文件的输入流
            FileInputStream input=new FileInputStream(file);
            int count=0;
            while ((count=input.read(buffer))!=-1){
                temp+=new String(buffer,0,count);
            }
            input.close();
            //内部存储器文件系统绝对目录
            temp+="\n\n getFilesDir="+getFilesDir()+"\n\n";
            //创建或者打开一个内部存储空间的目录
            temp+="getDir="+getDir("cache",MODE_PRIVATE)+"\n\n";
            //获得文件列表(缓存文件夹中的)
            File cachePath=getCacheDir();
            //遍历缓存文件夹下的文件，打印路径
            for(File cacheFile:cachePath.listFiles()){
                temp+="cacheFile="+cacheFile.getPath()+"\n\n";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        //在ui上更新数据
        tv_cache.setText(temp);
    }

    private void deleteCache(String fileName){
        File file=new File(fileName);
        file.delete();
    }
}
