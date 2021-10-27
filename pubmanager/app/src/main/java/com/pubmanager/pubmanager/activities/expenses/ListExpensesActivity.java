package com.pubmanager.pubmanager.activities.expenses;

import android.app.DatePickerDialog;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Date;

import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.UserTransactionActivity;
import com.pubmanager.pubmanager.databinding.ActivityListExpensesBinding;
import com.pubmanager.pubmanager.databinding.ActivityUserCategoryBinding;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListExpensesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<RecyclerEntity> list;
    RecyclerAdapter adapter;
    EditText transactionsByDate;
    private ActivityListExpensesBinding listExpensesBinding;
    EditText date_in;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expenses);

        listExpensesBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_expenses);
        listExpensesBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(date_in);
            }
        });


        recyclerView = findViewById(listExpensesBinding.recyclerview.getId());
        list = new ArrayList<>();

        list.add(new RecyclerEntity("This is the best title", R.drawable.one, false));
        list.add(new RecyclerEntity("This is the second-best title", R.drawable.one, false));
        list.add(new RecyclerEntity("The Great Gatsby", R.drawable.one, false));
        list.add(new RecyclerEntity("Catch-22", R.drawable.one, false));
        list.add(new RecyclerEntity("I really don't know", R.drawable.one, false));
        list.add(new RecyclerEntity("What to name this title", R.drawable.one, false));
        list.add(new RecyclerEntity("This is the best title", R.drawable.one, false));
        list.add(new RecyclerEntity("This is the second-best title", R.drawable.one, false));
        list.add(new RecyclerEntity("The Great Gatsby", R.drawable.one, false));
        list.add(new RecyclerEntity("Catch-22", R.drawable.one, false));
        list.add(new RecyclerEntity("I really don't know", R.drawable.one, false));
        list.add(new RecyclerEntity("What to name this title", R.drawable.one, false));

        adapter = new RecyclerAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.background));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                recyclerView.post(new Runnable() {
                    public void run() {
                        adapter.showMenu(viewHolder.getAdapterPosition());
                    }
                });
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                recyclerView.post(new Runnable() {
                    public void run() {
                        adapter.closeMenu();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (adapter.isMenuShown()) {
            adapter.closeMenu();
        } else {
            super.onBackPressed();
        }
    }


    /*private void createCategory() {

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
    }*/

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");


               String dateSelected = simpleDateFormat.format(calendar.getTime());
               Log.d("dateSelected:", dateSelected);

               try{
                   String strDate = dateSelected;
                   SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
                   Date date = formatter.parse(strDate);
                   Log.d("dateSelected converted :", String.valueOf(date.toString()));
               }catch(Exception e){
                   Log.d("Error occured :", e.getMessage());
               }

            }
        };

        new DatePickerDialog(ListExpensesActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


    }
}
