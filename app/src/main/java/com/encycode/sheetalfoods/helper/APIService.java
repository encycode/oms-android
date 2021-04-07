package com.encycode.sheetalfoods.helper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIService {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginRequest> LoginPostRequest(@Field("username") String username,
                                @Field("password") String password);
}
