package com.example.robot;

/**
 * Created by WUYIXIONG on 2016-9-26.
 */

public class ListData {

    public static final int robot = 1;
    public static final int people = 0;
    private int flag;

    private String content;

    public ListData(String content, int flag) {
        this.content = content;
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
