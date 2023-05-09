package com.example.co566app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Uses the layout defined in the activity_splash
                                                // layout file
        getSupportActionBar().hide();

        final Intent i = new Intent(SplashActivity.this, MainActivity.class);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(i); // Starts the SplashActivity
                finish(); // Finishes the SplashActivity after a set time
            }
        }, 2000); // Length of time SplashActivity is displayed
    }
}