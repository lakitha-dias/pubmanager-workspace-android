package com.pubmanager.pubmanager.retrofitutil;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.Call;

public interface APIRegistry {

    @Headers("Content-Type:application/json")
    @POST("users/register")
    Call<JsonObject> performSignUp(@Body JsonObject body);

    @FormUrlEncoded
    @POST("users/login")
    Call<APISignUpResponse> performSignIn(@Field("email") String email,
                                          @Field("password") String password);

    @FormUrlEncoded
    @POST("users/login")
    Call<APISignUpResponse> performRegenerateToken(@Field("email") String email,
                                                   @Field("password") String password);
}
