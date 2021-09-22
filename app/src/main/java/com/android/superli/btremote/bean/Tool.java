package com.android.superli.btremote.bean;

public class Tool {
    public int icon;
    public String name;
    public String toolName;
    public Class<?> aclass;


    public Tool(String name, String toolName, Class<?> aclass) {
        this.name = name;
        this.toolName = toolName;
        this.aclass = aclass;
    }

    public Tool(int icon, String name, Class<?> aclass) {
        this.icon = icon;
        this.name = name;
        this.aclass = aclass;
    }
}
