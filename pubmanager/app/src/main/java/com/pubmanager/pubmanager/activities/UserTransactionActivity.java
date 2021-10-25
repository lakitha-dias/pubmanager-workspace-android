package com.pubmanager.pubmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.databinding.ActivitySignUpBinding;
import com.pubmanager.pubmanager.databinding.ActivityUserTransactionBinding;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserTransactionActivity extends AppCompatActivity {

    EditText date_in;
    EditText time_in;
    //ImageView transactionDocument;
    private ActivityUserTransactionBinding userTransactionActivityBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_transaction);

        date_in=findViewById(R.id.date_input);
        time_in=findViewById(R.id.time_input);

        date_in.setInputType(InputType.TYPE_NULL);
        time_in.setInputType(InputType.TYPE_NULL);

        userTransactionActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_transaction);

       // transactionDocument = findViewById(R.id.transactionDocument);


       /* transactionDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        userTransactionActivityBinding.btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTransaction();
            }
        });




        userTransactionActivityBinding.dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date_in);
            }
        });
;
        userTransactionActivityBinding.timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time_in);
            }
        });

    }


    private void showTimeDialog(final EditText time_in) {
        final Calendar calendar=Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                userTransactionActivityBinding.timeInput.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(UserTransactionActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                userTransactionActivityBinding.dateInput.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(UserTransactionActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void createTransaction() {
        Double amount = Double.valueOf(userTransactionActivityBinding.amount.getText().toString());
        String note = userTransactionActivityBinding.note.getText().toString();
        String dateInput = userTransactionActivityBinding.dateInput.getText().toString();
        String timeInput = userTransactionActivityBinding.timeInput.getText().toString();
        Long transactionDate = Long.valueOf("1234566667555555");


        String BASE_URL = "http://10.0.2.2:8082/api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("amount", amount);
        bodyObj.addProperty("note", note);
        bodyObj.addProperty("transactionDate", transactionDate);

        String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzUxNjA3MjIsImV4cCI6MTYzNTE2NzkyMiwidXNlcklkIjo3LCJlbWFpbCI6Im5hcmVzaDIyZGQuZGRkQGdtYWlsLmNvbSIsImZpcnN0TmFtZSI6Im5hcmVzaDIzZGQiLCJsYXN0TmFtZSI6ImdnZzIyZGQifQ.ipWv2tWt25afRqzuh2fMYORh2mzUleAbGWiE0oRfE5k";
        Call<JsonObject> call = api.createTransaction(bodyObj,authToken);
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