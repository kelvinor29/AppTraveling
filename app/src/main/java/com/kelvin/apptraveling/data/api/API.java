package com.kelvin.apptraveling.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API { // Retrofit
    public static final String BASE_URL = "https://01394d44-8918-4a1d-8059-629c50c25e87.mock.pstmn.io";

    public ApiService service;

    public API(String url) {
        service = getRetrofit(url).create(ApiService.class);
    }

    private Retrofit getRetrofit(String url) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build()
                );
        builder.addConverterFactory(GsonConverterFactory.create()); // Conversion de json a objetos java

        return builder.build();
    }
}
