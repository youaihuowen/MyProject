package com.example.robot;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements HttpGetDataListener {

    private HttpData httpData;
    List<ListData> list;
    private ListView lv_message;
    private Button btn_send;
    private EditText et_edit;
    TextAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        list = new ArrayList<ListData>();
        lv_message = (ListView) findViewById(R.id.lv_message);
        et_edit = (EditText) findViewById(R.id.et_edit);
        btn_send = (Button) findViewById(R.id.btn_send);
        ListData listData = new ListData(getWelcome(),ListData.robot);
        list.add(listData);
        adapter = new TextAdapter(list, this);
        lv_message.setAdapter(adapter);
    }

    @Override
    public void getDataUrl(String data) {
        parseText(data);
//        Log.i("tag", data);
    }

    /**
     * @param str
     * @description 解析从网络上得到的数据
     */
    public void parseText(String str) {
        try {
            JSONObject jb = new JSONObject(str);
//            System.out.println(jb.getString("code"));
//            System.out.println(jb.getString("text"));
            ListData data = new ListData(jb.getString("text"), ListData.robot);
            list.add(data);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送用户内容
     *
     * @param view
     */
    public void sendMessage(View view) {
        String message = et_edit.getText().toString();
        message = message.replace(" ", "");
        message = message.replace("\n", "");
        et_edit.setText("");
        ListData data = new ListData(message, ListData.people);
        list.add(data);
        adapter.notifyDataSetChanged();
        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=451efec510a4413d824b1c944a1b2f3b" +
                "&info=" + message, this).execute();
    }

    /**
     * 随机获取欢迎语
     *
     * @return 欢迎语
     */
    private String getWelcome() {
        //存放欢迎语的数组
        String[] welcomes = getResources().getStringArray(R.array.welcome);
        String welcome = welcomes[(int) (Math.random() * welcomes.length)];
        return welcome;
    }
}
