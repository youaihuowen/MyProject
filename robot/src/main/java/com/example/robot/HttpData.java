package com.example.robot;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by WUYIXIONG on 2016-9-18.
 *
 * @description 接收网络上的数据
 */
public class HttpData extends AsyncTask<String, Void, String> {
    private HttpClient mHttpClient;
    private HttpPost mHttpPost;
    private String url;
    private HttpResponse mHttpresponse;
    private HttpEntity mHttpEntity;
    private InputStream in;
    private HttpGetDataListener listener;

    public HttpData(String url, HttpGetDataListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            mHttpClient = new DefaultHttpClient();
            mHttpPost = new HttpPost(url);
            mHttpresponse = mHttpClient.execute(mHttpPost);
            mHttpEntity = mHttpresponse.getEntity();
            in = mHttpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.getDataUrl(s);
        super.onPostExecute(s);
    }

}
