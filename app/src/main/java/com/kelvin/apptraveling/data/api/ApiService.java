package com.kelvin.apptraveling.data.api;

import com.kelvin.apptraveling.data.models.HotelResponse;
import com.kelvin.apptraveling.data.models.UserApi;
import com.kelvin.apptraveling.data.models.Hotel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/login")
    @FormUrlEncoded
    Call<UserApi> login(@Field("username") String username, @Field("password") String password);

    @GET("/listHotels")
    Call<HotelResponse> getHotels();
}
