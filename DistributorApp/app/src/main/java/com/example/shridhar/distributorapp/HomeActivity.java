package com.example.shridhar.distributorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClickNewClient(View v) {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickAccountDetailsClient(View v) {

    }

    public void onClickLogoutClient(View v) {

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getResources().getString(R.string.token_identifier), "");
        editor.apply();

        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);

        finish();
    }
}
