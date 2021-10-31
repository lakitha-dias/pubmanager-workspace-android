package com.pubmanager.pubmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.categoryExpenses.CategoryExpensesRecycleViewActivity;
import com.pubmanager.pubmanager.activities.expenses.ListExpensesActivity;
import com.pubmanager.pubmanager.databinding.ActivityMainBinding;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setSupportActionBar(mainBinding.myToolbar);

        mainBinding.signupReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });



        mainBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignIn();
            }
        });



        // Initially category-threshold-exceeded will be unsubscribed, after that if threshold exceeded after specific  threshold this will be subscribed else unsubscribed
        FirebaseMessaging.getInstance().subscribeToTopic("topic1")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()) {
                            Log.d("subscribeToTopic", "success"+task.getResult());
                        }
                        else {
                            Log.d("subscribeToTopic", "failed"+ task.getException());
                        }
                    }
                });



    }


    private void performSignIn(){


        String email = mainBinding.emailLogin.getText().toString();
        String password = mainBinding.passwordLogin.getText().toString();



        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("email", email);
        bodyObj.addProperty("password", password);


        Call<JsonObject> call = api.performSignIn(bodyObj);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("onResponse:", response.toString());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("onResponse|success:", response.body().toString());

                    JsonObject jsonObject = response.body();
                    JsonElement authTokenElement  = jsonObject.get("token");

                    String token = null;
                    if(authTokenElement != null){
                        token =  authTokenElement.getAsString();
                    }

                    token = token.replaceAll("^[\"']+|[\"']+$", "");

                    SharedPreferences preferences =
                            getSharedPreferences("com.pubmanager.pubmanager", Context.MODE_PRIVATE);

                    preferences.edit().putString("authToken", "Bearer "+token).putString("email", email).commit();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));


                } else {

                    Context context = getApplicationContext();
                    CharSequence text = "Error occured";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Log.e("onResponse|failure:", "Error in getGenericJson:" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Context context = getApplicationContext();
                CharSequence text = "Error occured";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                Log.e("onFailure|error:", t.getMessage());
                Log.e("onFailure|request:", call.request().toString());
            }
        });

    }


}