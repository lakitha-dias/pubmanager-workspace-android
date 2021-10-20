package com.pubmanager.pubmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.databinding.ActivitySignUpBinding;
import com.pubmanager.pubmanager.retrofitutil.APISignUpResponse;
import com.pubmanager.pubmanager.retrofitutil.APIClient;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        signUpBinding.signinHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            }
        });


        signUpBinding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignUp();
                // show progress bar
                //signUpBinding.
            }
        });
    }

    private void performSignUp(){
        String firstName = signUpBinding.firstName.getText().toString();
        String lastName = signUpBinding.lastName.getText().toString();
        String email = signUpBinding.email.getText().toString();
        String password = signUpBinding.password.getText().toString();



              /*  String BASE_URL = "http://10.0.2.2:8082/api/";
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                APIRegistry api = retrofit.create(APIRegistry.class);
                Call<JsonObject> jsonObjectCall = api.performSignUp(firstName,lastName,email,password);
                jsonObjectCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        Log.e("onResponse|response:", response.body().toString());
                        Log.e("onFailure|request:", call.request().toString());
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

                        Log.e("onFailure|request:", call.request().toString());
                        Log.e("onFailure|error:", t.toString());
                    }
                });*/

            /* Call<APISignUpResponse> call = APIClient.getRetrofitInstance().create(APIRegistry.class).performSignUp(firstName,lastName,email,password);
             call.enqueue(new Callback<APISignUpResponse>() {
                 @Override
                 public void onResponse(Call<APISignUpResponse> call, Response<APISignUpResponse> response) {

                     if(response.body() != null) {
                         Log.e("onResponse|response:", response.body().toString());
                         Log.e("onFailure|request:", call.request().toString());
                     }

                 }

                 @Override
                 public void onFailure(Call<APISignUpResponse> call, Throwable t) {
                     Log.e("onFailure|request:", call.request().toString());
                     Log.e("onFailure|error:", t.toString());
                 }
             });*/


        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("firstName", firstName);
        bodyObj.addProperty("lastName", lastName);
        bodyObj.addProperty("email", email);
        bodyObj.addProperty("password", password);

       /* RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("firstName", firstName)
                .addFormDataPart("lastName", lastName)
                .addFormDataPart("email", email)
                .addFormDataPart("password", password)
                .build();*/

        Call<JsonObject> call = api.performSignUp(bodyObj);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("onResponse:", response.toString());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("onResponse|success:", response.body().toString());
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

    private void displayUserInfo(String message){
       // Snackbar.make(signUpBinding.myCoordinatorLayout, message,Snackbar.LENGTH_SHORT).show();
    }
}