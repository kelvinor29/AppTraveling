package com.kelvin.apptraveling.data.api;

import com.kelvin.apptraveling.data.model.UserApi;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    @FormUrlEncoded
    Call<UserApi> login(@Field("username") String username, @Field("password") String password);
}
