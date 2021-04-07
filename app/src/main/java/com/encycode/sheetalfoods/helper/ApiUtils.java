package com.encycode.sheetalfoods.helper;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://oms.encycode.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
