package com.android.superli.btremote.bean;

public class KeyBean {
    public int vid;
    public String name;
    public String key;

    public KeyBean(int vid, String key) {
        this.vid = vid;
        this.key = key;
    }

    public KeyBean(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public KeyBean(int vid, String name, String key) {
        this.vid = vid;
        this.name = name;
        this.key = key;
    }
}
