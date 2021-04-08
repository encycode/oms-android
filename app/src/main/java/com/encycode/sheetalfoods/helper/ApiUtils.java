package com.encycode.sheetalfoods.helper;

import android.content.Context;

public class ApiUtils {

    private Context my_conext;
    private ApiUtils() {}
    public ApiUtils(Context context) {
        my_conext= context;
    }
    public static final String BASE_URL = "https://oms.encycode.com/";

    public APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL,my_conext).create(APIService.class);
    }
}
