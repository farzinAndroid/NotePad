package com.farzin.notepad2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.farzin.notepad2.AppConfig.AppConfig;
import com.farzin.notepad2.R;

public class SplashScreenActivity extends AppCompatActivity {

    AppConfig appConfig;
    public final static int DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appConfig = new AppConfig(getApplicationContext());
        boolean currentflag = appConfig.getBoot();

        if (currentflag == false){

            appConfig.setBoot(true);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    navigateToHomeScreen();

                }
            },DELAY_MILLIS);
        }else {

            navigateToHomeScreen();
        }



    }

    private void navigateToHomeScreen() {
        finish();

        Intent intent = new Intent(getApplicationContext(), NotePadActivity.class);
        startActivity(intent);
    }
}