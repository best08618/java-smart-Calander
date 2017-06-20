package com.example.wyshin.calander;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MyItem {

    private Drawable icon;
    private Bitmap bm;
    private String name;
    private String contents;
    private String time;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}