package com.example.shridhar.distributorapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                SharedPreferences preferences = SplashActivity.this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String token = preferences.getString(SplashActivity.this.getResources().getString(R.string.token_identifier), "");
                if (TextUtils.isEmpty(token)) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
