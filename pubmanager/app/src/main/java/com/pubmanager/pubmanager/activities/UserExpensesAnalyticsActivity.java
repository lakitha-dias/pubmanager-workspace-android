package com.pubmanager.pubmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.expenses.ListExpensesActivity;
import com.pubmanager.pubmanager.activities.expenses.RecyclerAdapter;
import com.pubmanager.pubmanager.activities.expenses.RecyclerEntity;
import com.pubmanager.pubmanager.databinding.ActivityListExpensesBinding;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserExpensesAnalyticsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<RecyclerEntity> list;
    RecyclerAdapter adapter;
    EditText transactionsByDate;
    private ActivityListExpensesBinding listExpensesBinding;
    EditText date_in;
    ArrayList<Object> listdata = new ArrayList<Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_expenses_analytics);


        listExpensesBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_expenses);
        listExpensesBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String categoryId = intent.getStringExtra("categoryId");
                String endpointType = intent.getStringExtra("endpointType");

                showDateDialog(date_in,endpointType,categoryId);
            }
        });

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add an expense entry..", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Get transactions for current date at first load

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


        getTransactionsByDateTime(startDate,endDate);


        Log.d("onResponse|listdata got :",listdata.toString());

    }



    @Override
    public void onBackPressed() {
        if (adapter.isMenuShown()) {
            adapter.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void showDateDialog(final EditText date_in, String endpointType, String categoryId) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                LocalDateTime localDateTime = Instant
                        .ofEpochMilli(calendar.getTime().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                LocalDateTime startOfDay = LocalDateTime.of(LocalDate.from(localDateTime), LocalTime.MIN);
                LocalDateTime endOfDay = LocalDateTime.of(LocalDate.from(localDateTime), LocalTime.MAX);

                String startDate = startOfDay.toString().replace("T", " ")+":00";
                String endDate = endOfDay.toString().replace("T", " ");

                getTransactionsByDateTime(startDate,endDate);

            }
        };

        new DatePickerDialog(UserExpensesAnalyticsActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    public void getTransactionsByDateTime(String startDate,  String endDate){


        String BASE_URL = null;

        BASE_URL = "http://10.0.2.2:8082/api/";



        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("startDateTime", startDate);
        bodyObj.addProperty("endDateTime", endDate);

        String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzU1MjE0MTIsImV4cCI6MTYzNTUyODYxMiwidXNlcklkIjo3LCJlbWFpbCI6Im5hcmVzaDIyZGQuZGRkQGdtYWlsLmNvbSIsImZpcnN0TmFtZSI6Im5hcmVzaDIzZGQiLCJsYXN0TmFtZSI6ImdnZzIyZGQifQ.po1nbcmSVQiPKlxjmy_j79SF-EJMZhb--MCxWeUG1uM";


        Call<JsonArray> call = null;
        call = api.getTransactionsByDateTime(bodyObj,authToken);


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



                        renderCart(listdata);


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


    public void renderCart(ArrayList<Object> listdata){
        recyclerView = findViewById(listExpensesBinding.recyclerview.getId());
        list = new ArrayList<>();


        if(!listdata.isEmpty()){
            for(int i=0; i<listdata.size(); i++) {
                //Printing each element of ArrayList
                Log.d("onResponse|listdata :", listdata.get(i).toString());

                JsonObject transactionItem = (JsonObject) listdata.get(i);

                if(transactionItem != null){
                    JsonElement transactionIdElement = transactionItem.get("transactionId");
                    String transactionId = null;
                    if(transactionIdElement != null){
                        transactionId = transactionIdElement.getAsString();
                        Log.d("onResponse|transactionId :", transactionId);
                    }

                    JsonElement categoryIdElement = transactionItem.get("categoryId");
                    String categoryId = null;
                    if(categoryIdElement != null){
                        categoryId = categoryIdElement.getAsString();
                        Log.d("onResponse|categoryId :", categoryId);
                    }


                    JsonElement amountElement = transactionItem.get("amount");
                    String amount = null;
                    if(amountElement != null){
                        amount = amountElement.getAsString();
                        Log.d("onResponse|amount :", amount);
                    }

                    JsonElement noteElement = transactionItem.get("note");
                    String note = null;
                    if(noteElement != null){
                        note = noteElement.getAsString();
                        Log.d("onResponse|note :", note);

                    }

                    JsonElement transactionDateElement = transactionItem.get("transactionDate");
                    String transactionDate = null;
                    if(transactionDateElement != null){
                        transactionDate = transactionDateElement.toString();
                        SimpleDateFormat changeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date transactionDateTime = new Date(Long.valueOf(transactionDate));
                        transactionDate = changeFormat.format(transactionDateTime);
                        Log.d("onResponse|transactionDate :", transactionDate);

                    }



                }
            }

        }
        else{
            Log.d("onResponse| No data found :", "Empty data list..");
        }

    }



}