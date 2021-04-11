package com.encycode.sheetalfoods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.helper.APIError;
import com.encycode.sheetalfoods.helper.APIService;
import com.encycode.sheetalfoods.helper.ApiUtils;
import com.encycode.sheetalfoods.helper.NotificationAdapter;
import com.encycode.sheetalfoods.helper.ProgressLoading;
import com.encycode.sheetalfoods.helper.request.Notification;
import com.encycode.sheetalfoods.helper.request.NotificationModel;
import com.encycode.sheetalfoods.helper.request.NotificationRequest;
import com.encycode.sheetalfoods.helper.request.StaffOrderRequest;
import com.encycode.sheetalfoods.viewmodels.NotificationsViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends AppCompatActivity {

    RecyclerView recyclerView;
    private APIService mAPIService;
    List<NotificationModel> notificationList;
    List<NotificationModel> notificationDeleteList;
    Toolbar toolbar;
    ProgressLoading loading;
    NotificationsViewModel viewModel;
    TextView noNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        loading = new ProgressLoading(Notifications.this);
        recyclerView = findViewById(R.id.notificationRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noNoti = findViewById(R.id.noNotificationTv);
        NotificationAdapter adapter = new NotificationAdapter(this);
        recyclerView.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setTitle("Notifications");
        toolbar.setTitleTextColor(getColor(R.color.white));
        toolbar.setBackgroundColor(getColor(R.color.buttonDefault));
        setActionBar(toolbar);

        mAPIService = new ApiUtils(Notifications.this).getAPIService();

        getNotification();
        viewModel = ViewModelProviders.of(Notifications.this).get(NotificationsViewModel.class);
        viewModel.getAllNotifications().observe(this, new Observer<List<com.encycode.sheetalfoods.entity.Notifications>>() {
            @Override
            public void onChanged(List<com.encycode.sheetalfoods.entity.Notifications> notifications) {
                if(notifications.size() == 0){
                   noNoti.setVisibility(View.VISIBLE);
                   recyclerView.setVisibility(View.GONE);
                }
                adapter.setNotifications(notifications);
                //Toast.makeText(Notifications.this, "item count : "+adapter.getItemCount(), Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(adapter);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new AlertDialog.Builder(Notifications.this)
                        .setTitle("Title")
                        .setMessage("Do you really want to delete ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteNotification(adapter.getNotificationAt(viewHolder.getAdapterPosition()));
                                adapter.notifyDataSetChanged();
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
//        deleteNotification(1);
    }

    public void getNotification() {
        //loading.startLoading();
        mAPIService.NotificationGetRequest().enqueue(new Callback<NotificationRequest>() {
            @Override
            public void onResponse(Call<NotificationRequest> call, Response<NotificationRequest> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.i("Create Order Request", "post submitted to API." + response.body().getMessage());
                        notificationList = response.body().getNotifications();
                        for (int i = 0; i < notificationList.size(); i++) {
                            int id = notificationList.get(i).getId().intValue();
                            String title = notificationList.get(i).getTitle();
                            String description = notificationList.get(i).getDescription();
                            String image = notificationList.get(i).getImage();
                            int userId = notificationList.get(i).getUserId().intValue();
                            String createdAt = notificationList.get(i).getCreatedAt();
                            String updatedAt = notificationList.get(i).getUpdatedAt();
                            String deletedAt = notificationList.get(i).getDeletedAt();
                            Log.i("log notification title", "onResponseNoti: " + title);
                            Log.i("log notification image", "onResponse: " + image);
                            Log.i("log notification userid", "onResponse: " + userId);
                            viewModel.insert(new com.encycode.sheetalfoods.entity.Notifications(id, title, description, image, userId, createdAt, updatedAt, deletedAt));
                            //loading.endLoading();
                        }
                    }
                } else {
                    if (response.code() == 401) {
                        APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                        Log.i("Create Order Request", "post submitted to API." + message.getMessage());
//                        loading.endLoading();
                        Toast.makeText(Notifications.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        //loading.endLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationRequest> call, Throwable t) {
                Log.e("error", t.getMessage());
            }

        });
    }

    public void deleteNotification(com.encycode.sheetalfoods.entity.Notifications notifications) {
        //loading.startLoading();
        int id = notifications.getId();
        mAPIService.DeleteNotificationRequest(id).enqueue(new Callback<NotificationRequest>() {
            @Override
            public void onResponse(Call<NotificationRequest> call, Response<NotificationRequest> response) {

                if (response.isSuccessful()) {
                    viewModel.delete(notifications);
                    if (response.code() == 200) {
                        Log.i("Delete Notifications", "post submitted to API." + response.body().getMessage());
                        notificationDeleteList = response.body().getNotifications();
                        for (int i = 0; i < notificationDeleteList.size(); i++) {
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