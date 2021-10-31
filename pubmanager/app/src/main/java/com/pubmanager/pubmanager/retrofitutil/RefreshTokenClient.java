package com.pubmanager.pubmanager.retrofitutil;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshTokenClient {

    public void getRefreshToken(String email, String password){

        String token = null;
        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("email", "naresh22dd.ddd@gmail.com");
        bodyObj.addProperty("password", "test@123");

        Call<JsonObject> call = api.getRefreshToken(bodyObj);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("onResponse:", response.toString());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("onResponse|success:", response.body().toString());

                    JsonObject jsonObject = response.body();
                    JsonElement tokenElement = jsonObject.get("token");
                    if(tokenElement != null){
                        String token = tokenElement.getAsString();

                        /*SharedPreferences sp= getSharedPreferences("key", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed=sp.edit();
                        ed.putInt("value", your_value);
                        ed.commit();*/

                    }

                } else {
                    Log.e("onResponse|failure:", "Error in getGenericJson:" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("onFailure|error:", t.getMessage());
                Log.e("onFailure|request:", call.request().toString());
            }
        });

    }

}
