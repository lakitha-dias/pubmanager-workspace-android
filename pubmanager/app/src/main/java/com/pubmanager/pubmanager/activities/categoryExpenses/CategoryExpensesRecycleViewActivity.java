package com.pubmanager.pubmanager.activities.categoryExpenses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.CategoriesListViewActivity;
import com.pubmanager.pubmanager.activities.HomeActivity;
import com.pubmanager.pubmanager.activities.MainActivity;
import com.pubmanager.pubmanager.activities.UserCategoryActivity;
import com.pubmanager.pubmanager.activities.UserExpensesAnalyticsActivity;
import com.pubmanager.pubmanager.activities.categories.CardModel;
import com.pubmanager.pubmanager.activities.categories.CardsAdapter;
import com.pubmanager.pubmanager.activities.expenses.ListExpensesActivity;
import com.pubmanager.pubmanager.models.UserCategory;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryExpensesRecycleViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<CategoryListItem> categoryListItemList;

    // Drawer
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_expenses_recycle_view);

        categoryListItemList = new ArrayList<>();


        //prepareMovie();
        getAllCategories();


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(categoryListItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        recyclerViewAdapter.setOnItemClickListener(new ClickListener<CategoryListItem>(){
            @Override
            public void onItemClick(CategoryListItem data) {
                //Toast.makeText(CategoryExpensesRecycleViewActivity.this, data.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CategoryExpensesRecycleViewActivity.this, ListExpensesActivity.class);
                intent.putExtra("categoryId", data.getCategoryId());
                intent.putExtra("endpointType", "find-transactions-by-category-datetime");
                startActivityForResult(intent, 0);

            }
        });


        recyclerView.setAdapter(recyclerViewAdapter);



        /// Drawer

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




    }





   /* public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.categories:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, CategoriesListViewActivity.class));
                return true;
            case R.id.statistics:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, UserExpensesAnalyticsActivity.class));
                return true;

            case R.id.expenses:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, ListExpensesActivity.class));
                return true;

            case R.id.categoryExpenses:
                return true;

            case R.id.signout:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, HomeActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, HomeActivity.class));
                return true;

            case R.id.categories:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, CategoriesListViewActivity.class));
                return true;
            case R.id.statistics:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, UserExpensesAnalyticsActivity.class));
                return true;

            case R.id.expenses:
                Intent intent = new Intent(CategoryExpensesRecycleViewActivity.this, ListExpensesActivity.class);
                intent.putExtra("categoryId", "0");
                intent.putExtra("endpointType", "find-transactions-by-datetime");
                intent.putExtra("hideAddOptionFlag", "TRUE");
                startActivityForResult(intent, 0);
                return true;

            case R.id.categoryExpenses:
                startActivity(new Intent(CategoryExpensesRecycleViewActivity.this, CategoryExpensesRecycleViewActivity.class));
                return true;

            case R.id.signout:

                SharedPreferences preferences =
                        getSharedPreferences("com.pubmanager.pubmanager", Context.MODE_PRIVATE);

                preferences.edit().putString("authToken", "").commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
            }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void getAllCategories(){


        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);



        SharedPreferences preferences =
                getSharedPreferences("com.pubmanager.pubmanager", Context.MODE_PRIVATE);
        String authToken = preferences.getString("authToken", "token");

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


                                    double percentage = 0.0D;

                                    UserCategory userCategory =  new UserCategory();
                                    calculateTransactionTotalPerCategory(userCategory,categoryId);

                                    Log.e("onResponse|totalAmount:", "totalAmount:"+ userCategory.getTotalTransactionAmount()+ " | categoryId : "+userCategory.getCategoryId());

                                    percentage = (userCategory.getTotalTransactionAmount()/10000)*100;
                                    Log.e("onResponse|percentage:", "percentage:" + percentage);



                                    CategoryListItem categoryListItem = new CategoryListItem(title, description,categoryId);
                                    categoryListItemList.add(categoryListItem);

                                    if(recyclerViewAdapter != null) {
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }

                                }
                            }

                        }
                        else{
                            Log.d("onResponse| No data found :", "Empty data list..");
                        }


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



    public void calculateTransactionTotalPerCategory( UserCategory userCategory,String categoryId) {



        String BASE_URL = "http://10.0.2.2:8082/api/categories/" + categoryId + "/";


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        ZoneId zoneId = ZoneId.of("America/Montreal");  // Or 'ZoneOffset.UTC'.
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        Month month = now.getMonth();
        int year = now.getYear();
        int monthNumber = month.getValue(); // Answer to the Question.


        YearMonth yearMonth = YearMonth.of(year, monthNumber);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate last = yearMonth.atEndOfMonth();


        String startDate = firstOfMonth + " " + "00:00:00";
        String endDate = last + " " + "11:59:59";


        JsonObject bodyObj = new JsonObject();
        bodyObj.addProperty("startDateTime", startDate);
        bodyObj.addProperty("endDateTime", endDate);


        SharedPreferences preferences =
                getSharedPreferences("com.pubmanager.pubmanager", Context.MODE_PRIVATE);
        String authToken = preferences.getString("authToken", "token");

        Call<JsonArray> call = null;
        call = api.getTransactionsByCategoryDateTime(bodyObj, authToken);


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
                            for (int i=0;i<jsonArray.size();i++){

                                //Adding each element of JSON array into ArrayList
                                listdata.add(jsonArray.get(i));
                            }
                        }



                        double totalAmount = 0.0D;
                        String categoryId = null;

                        if(!listdata.isEmpty()){
                            for(int i=0; i<listdata.size(); i++) {
                                //Printing each element of ArrayList
                                Log.d("onResponse|listdata :", listdata.get(i).toString());

                                JsonObject transactionItem = (JsonObject) listdata.get(i);

                                if(transactionItem != null){


                                    JsonElement categoryIdElement = transactionItem.get("categoryId");

                                    if(categoryIdElement != null){
                                        categoryId = categoryIdElement.getAsString();
                                        Log.d("onResponse|categoryId :", categoryId);
                                    }

                                    JsonElement amountElement = transactionItem.get("amount");
                                    double amount = 0.0D;
                                    if(amountElement != null){
                                        amount = amountElement.getAsDouble();
                                    }


                                    totalAmount = totalAmount + amount;

                                }
                            }

                            userCategory.setCategoryId(categoryId);
                            userCategory.setTotalTransactionAmount(totalAmount);
                            Log.e("onResponse|totalAmount calculated:", "totalAmount:" + userCategory.getTotalTransactionAmount()+ " | categoryId : "+userCategory.getCategoryId());

                        }
                        else{
                            Log.d("onResponse| No data found :", "Empty data list..");
                        }


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



    private void setTotalAmountVal( UserCategory userCategory,ArrayList<Object> listdata){

        double totalAmount = 0.0D;
        String categoryId = null;

        if(!listdata.isEmpty()){
            for(int i=0; i<listdata.size(); i++) {
                //Printing each element of ArrayList
                Log.d("onResponse|listdata :", listdata.get(i).toString());

                JsonObject transactionItem = (JsonObject) listdata.get(i);

                if(transactionItem != null){


                    JsonElement categoryIdElement = transactionItem.get("categoryId");

                    if(categoryIdElement != null){
                        categoryId = categoryIdElement.getAsString();
                        Log.d("onResponse|categoryId :", categoryId);
                    }

                    JsonElement amountElement = transactionItem.get("amount");
                    double amount = 0.0D;
                    if(amountElement != null){
                        amount = amountElement.getAsDouble();
                    }


                    totalAmount = totalAmount + amount;

                }
            }

            userCategory.setCategoryId(categoryId);
            userCategory.setTotalTransactionAmount(totalAmount);
            Log.e("onResponse|totalAmount calculated:", "totalAmount:" + userCategory.getTotalTransactionAmount()+ " | categoryId : "+userCategory.getCategoryId());

        }
        else{


            TextView messageView = (TextView)findViewById(R.id.textView2);
            messageView.setText("No records found");
            Log.d("onResponse| No data found :", "Empty data list..");
        }
    }


}