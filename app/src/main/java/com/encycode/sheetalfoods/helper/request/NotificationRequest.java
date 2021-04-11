package com.encycode.sheetalfoods.helper.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationRequest {
    @SerializedName("notifications")
    @Expose
    private List<NotificationModel> notifications = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
