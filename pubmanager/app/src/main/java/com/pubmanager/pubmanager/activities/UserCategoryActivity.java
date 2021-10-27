package com.pubmanager.pubmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.databinding.ActivityUserCategoryBinding;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCategoryActivity extends AppCompatActivity {


    private ActivityUserCategoryBinding userCategoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        userCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_category);



        userCategoryBinding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCategory();
            }
        });

    }

    private void createCategory() {

        String title = userCategoryBinding.title.getText().toString();


        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);



        RadioGroup radioGroup = (RadioGroup) userCategoryBinding.description;
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
        String categoryDescription = (String) radioButton.getText();


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("title", title);
        bodyObj.addProperty("description", categoryDescription);

        String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzUyMzI3OTksImV4cCI6MTYzNTIzOTk5OSwidXNlcklkIjo3LCJlbWFpbCI6Im5hcmVzaDIyZGQuZGRkQGdtYWlsLmNvbSIsImZpcnN0TmFtZSI6Im5hcmVzaDIzZGQiLCJsYXN0TmFtZSI6ImdnZzIyZGQifQ.XYZ0TVAMKpL5Jls4h96oIliNlgEqPHfLJ1lerAqmTI4";
        Call<JsonObject> call = api.createCategory(bodyObj,authToken);
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

}