package com.example.classtest.datastorage;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.classtest.BaseActivity;
import com.example.classtest.R;

import java.io.File;

public class ExternalStorageActivity extends BaseActivity implements View.OnClickListener {
    //创建公共外部存储器相册目录按钮
    Button btn_mkAlbum;
    //创建私有外部存储器手机硬盘目录按钮
    Button btn_mkPrivate;
    Button btn_mkPrivatePhone;
    //自定义相册文件文件名
    private static final String ALBUM_FILE="MyAlbum";
    //自定义外部存储器私有文件文件名
    private static final String PRIVATE_FILE="MyPrivateFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_external_storage;
    }

    @Override
    protected void initView() {
        btn_mkAlbum=(Button) findViewById(R.id.btn_exter_mkAlbum);
        btn_mkPrivate=(Button) findViewById(R.id.btn_exter_mkPrivate);
        btn_mkPrivatePhone=(Button) findViewById(R.id.btn_exter_mkPrivatePhone);
    }

    @Override
    protected void setLisetner() {
        btn_mkAlbum.setOnClickListener(this);
        btn_mkPrivate.setOnClickListener(this);
        btn_mkPrivatePhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exter_mkAlbum:
                creatAlbum(ALBUM_FILE);
                break;
            case R.id.btn_exter_mkPrivate:
                createPrivate(PRIVATE_FILE);
                break;
            case R.id.btn_exter_mkPrivatePhone:
                createPrivatePhone(PRIVATE_FILE);
                break;
        }
    }

    /**
     * @description 检查设备是否为可读状态
     * @return 是否可读
     */
    private boolean checkReadState(){
        //获得当前状态
        String state= Environment.getExternalStorageState();
        //判断是否可写
        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ||
                Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    /**
     * description 检查设备是否为可写状态
     * @return 是否可写
     */
    private boolean checkWriteState(){
        //获得当前状态
        String state=Environment.getExternalStorageState();
        //判断是否可写
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    /**
     * @description 创建一个相册目录下的文件
     * @param fileName 目录或文件名
     */
    private void creatAlbum(String fileName){
        //检查设备可写状态
        if (checkWriteState()){
            //根据相册目录创建一个file对象
            File file=new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES),fileName);
            //创建一个多级目录，返回成功或者失败的布尔值
//            Log.e("tag", checkWriteState()+"");
//            Log.e("tag", file.getPath());
            if(!file.mkdirs()){
                //若失败，提示
                Toast.makeText(this,"creat directory failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @description 创建一个私有外部存储器目录(一般只在手机硬盘中)
     * @param fileName 目录或文件名
     */
    private void createPrivate(String fileName){
        if (checkWriteState()){
            //根据相册目录创建一个File对象
            File file=new File(getExternalFilesDir(null),fileName);
            //创建一个多级目录，返回成功或者失败的布尔值
            if (!file.exists() && !file.mkdirs()){
                Toast.makeText(this,"creat directory failed",Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     *@description 创建一个私有外部存储器目录(手机硬盘中)
     * @param fileName 文件名和路径名
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createPrivatePhone(String fileName){
        if (checkWriteState()){
            //根据相册目录创建一个File对象
            //0为一般手机硬盘,1一般为SD卡，这里可能会根据设备类型产生差异
            File file=new File(getExternalFilesDirs(null)[0],
                    fileName);
            //创建一个多级目录，返回成功或者失败的布尔值
            if (!file.exists() && !file.mkdirs()){
                Toast.makeText(this,"creat directory failed",Toast.LENGTH_SHORT).show();
            }

        }
    }


}
