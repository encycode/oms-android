package com.encycode.sheetalfoods.helper;

import com.encycode.sheetalfoods.helper.request.CategoryRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsDeleteRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsEditRequest;
import com.encycode.sheetalfoods.helper.request.OrderDetailsRequest;
import com.encycode.sheetalfoods.helper.request.OrderPostRequest;
import com.encycode.sheetalfoods.helper.request.OrderRequest;
import com.encycode.sheetalfoods.helper.request.StaffOrderRequest;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface APIService {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginRequest> LoginPostRequest(@Field("username") String username,
                                @Field("password") String password);

    @Headers({"Accept: application/json"})
    @GET("api/categories")
    Call<CategoryRequest> CategoryGetRequest();

    @Headers({"Accept: application/json"})
    @GET("api/orders")
    Call<OrderRequest> OrderGetRequest();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/orders")
    Call<OrderPostRequest> OrderPostRequest(@Field("orderby") String orderby,
                                            @Field("category") int category);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/orders")
    Call<StaffOrderRequest> OrderStaffPostRequest(@Field("receiver_client") int receiver_client,
                                                  @Field("shop_name") String shop_name,
                                                  @Field("address") String address,
                                                  @Field("mobile") String mobile,
                                                  @Field("orderby") String orderby,
                                                  @Field("category") int category);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/order-details")
    Call<OrderDetailsRequest> OrderDetailsPostRequest(@Field("order") int order ,
                                               @Field("product") int product,
                                               @Field("carat_order") int carat_order);

    @Headers({"Accept: application/json"})
    @DELETE("api/orders/delete")
    Call<OrderPostRequest> OrderDeleteRequest(@Query("id") int id);

    @Headers({"Accept: application/json"})
    @DELETE("api/order-details/delete")
    Call<OrderDetailsDeleteRequest> OrderDetailsDeleteRequest(@Query("id") int id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT("/api/order-details/edit")
    Call<OrderDetailsEditRequest> OrderDetailsEditRequest(@Field("id") int id);
}
