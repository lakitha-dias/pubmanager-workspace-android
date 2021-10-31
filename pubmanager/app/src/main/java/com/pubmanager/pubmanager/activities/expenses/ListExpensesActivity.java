package com.pubmanager.pubmanager.activities.expenses;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.CategoriesListViewActivity;
import com.pubmanager.pubmanager.activities.HomeActivity;
import com.pubmanager.pubmanager.activities.UserExpensesAnalyticsActivity;
import com.pubmanager.pubmanager.activities.UserTransactionActivity;
import com.pubmanager.pubmanager.activities.categoryExpenses.CategoryExpensesRecycleViewActivity;
import com.pubmanager.pubmanager.activities.categoryExpenses.CategoryListItem;
import com.pubmanager.pubmanager.activities.categoryExpenses.RecyclerViewAdapter;
import com.pubmanager.pubmanager.databinding.ActivityListExpensesBinding;
import com.pubmanager.pubmanager.databinding.ActivityUserCategoryBinding;
import com.pubmanager.pubmanager.retrofitutil.APIRegistry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<Object> listdata = new ArrayList<Object>();


    private RecyclerViewAdapter recyclerViewAdapter;
    private List<CategoryListItem> categoryListItemList;

    // Drawer
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expenses);


        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("categoryId");
        String endpointType = intent.getStringExtra("endpointType");
        String hideAddOptionFlag = intent.getStringExtra("hideAddOptionFlag");


        listExpensesBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_expenses);
        listExpensesBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDateDialog(date_in,endpointType,categoryId);
            }
        });


        listExpensesBinding.textviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ListExpensesActivity.this, HomeActivity.class));
            }
        });


        TextView txtViewHide = (TextView)findViewById(R.id.addExpense);

        if(hideAddOptionFlag != null){
            if(hideAddOptionFlag.equals("TRUE")){
                txtViewHide.setVisibility(View.INVISIBLE);
            }
            else{
                txtViewHide.setVisibility(View.VISIBLE);
            }
        }
        else{
            txtViewHide.setVisibility(View.VISIBLE);
        }



        listExpensesBinding.addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ListExpensesActivity.this, UserTransactionActivity.class);
                intent.putExtra("categoryId", categoryId);
                startActivityForResult(intent, 0);

                // startActivity(new Intent(ListExpensesActivity.this, UserTransactionActivity.class));
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

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.from(now), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.from(now), LocalTime.MAX);

        String startDate = startOfDay.toString().replace("T", " ")+":00";
        String endDate = endOfDay.toString().replace("T", " ");



        getTransactionsByDateTime(startDate,endDate,endpointType,categoryId);


        Log.d("onResponse|listdata got :",listdata.toString());



        /// Drawer

     /*toolbar = findViewById(R.id.main_toolbar);
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
        navigationView.setNavigationItemSelectedListener(this);*/


    }


/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.categories:
                startActivity(new Intent(ListExpensesActivity.this, CategoriesListViewActivity.class));
                return true;
            case R.id.statistics:
                startActivity(new Intent(ListExpensesActivity.this, UserExpensesAnalyticsActivity.class));
                return true;

            case R.id.expenses:
                startActivity(new Intent(ListExpensesActivity.this, ListExpensesActivity.class));
                return true;

            case R.id.categoryExpenses:
                return true;

            case R.id.signout:
                startActivity(new Intent(ListExpensesActivity.this, HomeActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/


    @Override
    public void onBackPressed() {
        if (adapter.isMenuShown()) {
            adapter.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void showDateDialog(final EditText date_in,String endpointType, String categoryId) {
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

                getTransactionsByDateTime(startDate,endDate,endpointType, categoryId);

            }
        };

        new DatePickerDialog(ListExpensesActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    public void getTransactionsByDateTime(String startDate,  String endDate, String endpointType, String categoryId){


        String BASE_URL = null;

        if(endpointType.equals("find-transactions-by-datetime")){

           BASE_URL = "http://10.0.2.2:8082/api/";
           // http://10.0.2.2:8082/api/categories/0/transactions/find-transactions-by-datetime

        }

        if(endpointType.equals("find-transactions-by-category-datetime")){

           BASE_URL = "http://10.0.2.2:8082/api/categories/"+categoryId+"/";

           // http://10.0.2.2:8082/api/categories/7/transactions/find-transactions-by-datetime
        }


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        APIRegistry api = retrofit.create(APIRegistry.class);


        JsonObject bodyObj= new JsonObject();
        bodyObj.addProperty("startDateTime", startDate);
        bodyObj.addProperty("endDateTime", endDate);

        SharedPreferences preferences =
                getSharedPreferences("com.pubmanager.pubmanager", Context.MODE_PRIVATE);
        String authToken = preferences.getString("authToken", "token");



        Call<JsonArray> call = null;
        if(endpointType.equals("find-transactions-by-datetime")){

            Log.e("onFailure|authToken:", authToken);
            call = api.getTransactionsByDateTime(bodyObj,authToken);
        }

        if(endpointType.equals("find-transactions-by-category-datetime")){

            Log.e("onFailure|authToken:", authToken);
            call = api.getTransactionsByCategoryDateTime(bodyObj,authToken);
        }

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

                    list.add(new RecyclerEntity(note+" "+"| "+amount+" (LKR)"+" | "+transactionDate + " | categoryId : "+categoryId, R.drawable.categories_icon, false));

                }
           }

        }
        else{

            Context context = getApplicationContext();
            CharSequence text = "No records found within the date range";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Log.d("onResponse| No data found :", "Empty data list..");
        }

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
}
