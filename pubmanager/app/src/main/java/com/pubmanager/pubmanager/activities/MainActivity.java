package com.pubmanager.pubmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pubmanager.pubmanager.R;
import com.pubmanager.pubmanager.databinding.ActivityMainBinding;

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

        mainBinding.submitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserTransactionActivity.class));
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


}