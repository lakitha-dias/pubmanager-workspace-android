package com.pubmanager.pubmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.activities.expenses.ListExpensesActivity;
import com.pubmanager.pubmanager.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        homeBinding.expensesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(HomeActivity.this, ListExpensesActivity.class));

                Intent intent = new Intent(HomeActivity.this, ListExpensesActivity.class);
                intent.putExtra("categoryId", "0");
                intent.putExtra("endpointType", "find-transactions-by-datetime");
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


    }
}