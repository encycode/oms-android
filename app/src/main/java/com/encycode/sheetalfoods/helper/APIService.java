package com.encycode.sheetalfoods.helper;

import com.encycode.sheetalfoods.helper.request.Category;
import com.encycode.sheetalfoods.helper.request.CategoryRequest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIService {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginRequest> LoginPostRequest(@Field("username") String username,
                                @Field("password") String password);

    @Headers({"Accept: application/json"})
    @GET("api/categories")
    Call<CategoryRequest> CategoryGetRequest();


}
