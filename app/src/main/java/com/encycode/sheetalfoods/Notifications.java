package com.encycode.sheetalfoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.NotificationAdapter;
import com.encycode.sheetalfoods.helper.request.Notification;
import com.encycode.sheetalfoods.helper.request.NotificationModel;
import com.encycode.sheetalfoods.helper.request.NotificationRequest;
import com.encycode.sheetalfoods.helper.request.StaffOrderRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends AppCompatActivity {

    RecyclerView  recyclerView;
    private APIService mAPIService;
    List<NotificationModel> notificationList;
    List<NotificationModel> notificationDeleteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.notificationRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAPIService = new ApiUtils(Notifications.this).getAPIService();

        List<Notification> notifications = new ArrayList<>();

        notifications.add(new Notification("","Offer 1","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 2","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 3","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 4","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));
        notifications.add(new Notification("","Offer 5","A paragraph is a series of related sentences developing a central idea, called the topic. Try to think about paragraphs in terms of thematic unity: a paragraph is a sentence or a group of sentences that supports one central, unified idea. Paragraphs add one idea at a time to your broader argument."));

        NotificationAdapter adapter = new NotificationAdapter(this);
        adapter.setNotifications(notifications);

        recyclerView.setAdapter(adapter);
//        getNotification();
//        deleteNotification(1);
    }
    public void getNotification() {
//        loading.startLoading();
        mAPIService.NotificationGetRequest().enqueue(new Callback<NotificationRequest>() {
            @Override
            public void onResponse(Call<NotificationRequest> call, Response<NotificationRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        notificationList = response.body().getNotifications();
                        for (int i=0;i<notificationList.size();i++){
                            Long id = notificationList.get(i).getId();
                            String title = notificationList.get(i).getTitle();
                            String description = notificationList.get(i).getDescription();
                            String image = notificationList.get(i).getImage();
                            Long userId = notificationList.get(i).getUserId();
                            String createdAt = notificationList.get(i).getCreatedAt();
                            String updatedAt = notificationList.get(i).getUpdatedAt();
                            String deletedAt = notificationList.get(i).getDeletedAt();
                            Log.i("log notification title", "onResponse: " + title);
                            Log.i("log notification image", "onResponse: " + image);
                            Log.i("log notification userid", "onResponse: " + userId);
                        }
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
//                        loading.endLoading();
                        Toast.makeText(Notifications.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }

    public void deleteNotification(int id) {
//        loading.startLoading();
        mAPIService.DeleteNotificationRequest(id).enqueue(new Callback<NotificationRequest>() {
            @Override
            public void onResponse(Call<NotificationRequest> call, Response<NotificationRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Delete Notifications", "post submitted to API." + response.body().getMessage());
                        notificationDeleteList = response.body().getNotifications();
                        for (int i=0;i<notificationDeleteList.size();i++){
                            Long id = notificationDeleteList.get(i).getId();
                            String title = notificationDeleteList.get(i).getTitle();
                            String description = notificationDeleteList.get(i).getDescription();
                            String image = notificationDeleteList.get(i).getImage();
                            Long userId = notificationDeleteList.get(i).getUserId();
                            String createdAt = notificationDeleteList.get(i).getCreatedAt();
                            String updatedAt = notificationDeleteList.get(i).getUpdatedAt();
                            String deletedAt = notificationDeleteList.get(i).getDeletedAt();
                            Log.i("log notification title", "onResponse: " + title);
                            Log.i("log notification image", "onResponse: " + image);
                            Log.i("log notification userid", "onResponse: " + userId);
                        }
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
//                        loading.endLoading();
                        Toast.makeText(Notifications.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }
}