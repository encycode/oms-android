package com.encycode.sheetalfoods.helper.request;

public class Notification {

    String image,title,desc;

    public Notification(String image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
