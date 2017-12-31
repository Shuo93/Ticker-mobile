package com.ticker.mobile.model;

/**
 * Created by Shuo on 2017/12/31.
 */

public class SpinnerItem {

    private String id;

    private String text;

    public SpinnerItem(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
