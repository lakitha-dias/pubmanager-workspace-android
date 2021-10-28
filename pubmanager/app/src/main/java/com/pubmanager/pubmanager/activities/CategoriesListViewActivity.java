package com.pubmanager.pubmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.categories.CardModel;
import com.pubmanager.pubmanager.activities.categories.CardsAdapter;
import com.pubmanager.pubmanager.activities.expenses.RecyclerEntity;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriesListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list_view);

        getAllCategories();
    }


    public void getAllCategories(){


        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzUzODgyNzQsImV4cCI6MTYzNTM5NTQ3NCwidXNlcklkIjo3LCJlbWFpbCI6Im5hcmVzaDIyZGQuZGRkQGdtYWlsLmNvbSIsImZpcnN0TmFtZSI6Im5hcmVzaDIzZGQiLCJsYXN0TmFtZSI6ImdnZzIyZGQifQ.UPKO1jiNBo4Db1B9XX7-BKK-g-BM95MCnTHTv1dUGsI";
        Call<JsonArray> call = api.getAllCategories(authToken);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("onResponse:", response.toString());

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("onResponse|success:", response.body().toString());

                    String responseArray = response.body().toString();

                    try {

                        JsonArray jsonArray = response.body();

                        ArrayList<Object> listdata = new ArrayList<Object>();

                        if (jsonArray != null) {

                            //Iterating JSON array
                            for (int i = 0; i < jsonArray.size(); i++) {

                                //Adding each element of JSON array into ArrayList
                                listdata.add(jsonArray.get(i));
                            }
                        }

                        renderList(listdata);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e("onResponse|failure:", "Error in getGenericJson:" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("onFailure|error:", t.getMessage());
                Log.e("onFailure|request:", call.request().toString());
            }

        });
    }

    public void renderList(ArrayList<Object> listdata){
        ListView lvCards = (ListView) findViewById(R.id.list_cards);
        CardsAdapter adapter = new CardsAdapter(this);


        if(!listdata.isEmpty()){
            for(int i=0; i<listdata.size(); i++) {
                //Printing each element of ArrayList
                Log.d("onResponse|listdata :", listdata.get(i).toString());

                JsonObject transactionItem = (JsonObject) listdata.get(i);

                if(transactionItem != null){

                    JsonElement userIdElement = transactionItem.get("userId");
                    String userId = null;
                    if(userIdElement != null){
                        userId = userIdElement.getAsString();
                        Log.d("onResponse|userId :", userId);
                    }

                    JsonElement categoryIdElement = transactionItem.get("categoryId");
                    String categoryId = null;
                    if(categoryIdElement != null){
                        categoryId = categoryIdElement.getAsString();
                        Log.d("onResponse|categoryId :", categoryId);
                    }


                    JsonElement titleElement = transactionItem.get("title");
                    String title = null;
                    if(titleElement != null){
                        title = titleElement.getAsString();
                        Log.d("onResponse|title :", title);
                    }

                    JsonElement descriptionElement = transactionItem.get("description");
                    String description = null;
                    if(descriptionElement != null){
                        description = descriptionElement.getAsString();
                        Log.d("onResponse|description :", description);

                    }



                    lvCards.setAdapter(adapter);
                    adapter.add(new CardModel(R.drawable.nonrecurring_list_icon, title, description));
                   /* lvCards.setAdapter(adapter);
                    adapter.add(new CardModel(R.drawable.nonrecurring_list_icon, R.string.mercury, R.string.mercury_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.venus, R.string.venus_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.earth, R.string.earth_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.mars, R.string.mars_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.jupiter, R.string.jupiter_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.saturn, R.string.saturn_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.uranus, R.string.uranus_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.neptune, R.string.neptune_sub),
                            new CardModel(R.drawable.nonrecurring_list_icon, R.string.pluto, R.string.pluto_sub));*/

                }
            }

        }
        else{
            Log.d("onResponse| No data found :", "Empty data list..");
        }



    }
}