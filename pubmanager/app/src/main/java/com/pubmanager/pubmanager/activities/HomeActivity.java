package com.pubmanager.pubmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.categoryExpenses.CategoryExpensesRecycleViewActivity;
import com.pubmanager.pubmanager.activities.expenseSource.UserExpenseSourceActivity;
import com.pubmanager.pubmanager.activities.expenses.ListExpensesActivity;
import com.pubmanager.pubmanager.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);


        SharedPreferences preferences =
                getSharedPreferences("com.pubmanager.pubmanager", Context.MODE_PRIVATE);
        String emailId = preferences.getString("email", "email");
        TextView emailIdTextView = (TextView) findViewById(R.id.user_name);
        emailIdTextView.setText("Welcome "+emailId);



        homeBinding.expensesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(HomeActivity.this, ListExpensesActivity.class));

                Intent intent = new Intent(HomeActivity.this, ListExpensesActivity.class);
                intent.putExtra("categoryId", "0");
                intent.putExtra("endpointType", "find-transactions-by-datetime");
                intent.putExtra("hideAddOptionFlag", "TRUE");
                startActivityForResult(intent, 0);

            }
        });

        homeBinding.expenseSources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(HomeActivity.this, ListExpensesActivity.class));

                Intent intent = new Intent(HomeActivity.this, UserExpenseSourceActivity.class);
                intent.putExtra("categoryId", "0");
                intent.putExtra("endpointType", "find-transactions-by-datetime");
                intent.putExtra("hideAddOptionFlag", "TRUE");
                startActivityForResult(intent, 0);

            }
        });




        homeBinding.categoriesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(HomeActivity.this, CategoriesListViewActivity.class));
            }
        });


        homeBinding.userAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UserExpensesAnalyticsActivity.class));
            }
        });


        homeBinding.categoryExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CategoryExpensesRecycleViewActivity.class));
            }
        });



    }
}