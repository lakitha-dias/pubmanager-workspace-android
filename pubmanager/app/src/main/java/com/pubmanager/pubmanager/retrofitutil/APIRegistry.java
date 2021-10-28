package com.pubmanager.pubmanager.retrofitutil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @Headers("Content-Type:application/json")
    @POST("categories/1/transactions")
    Call<JsonObject> createTransaction(@Body JsonObject body, @Header("Authorization") String auth);

    @Headers("Content-Type:application/json")
    @POST("categories")
    Call<JsonObject> createCategory(@Body JsonObject body, @Header("Authorization") String auth);

    @Headers("Content-Type:application/json")
    @POST("categories/0/transactions/find-transactions-by-datetime")
    Call<JsonArray> getTransactionsByDateTime(@Body JsonObject body, @Header("Authorization") String auth);


    @Headers("Content-Type:application/json")
    @GET("categories")
    Call<JsonArray> getAllCategories(@Header("Authorization") String auth);
}
