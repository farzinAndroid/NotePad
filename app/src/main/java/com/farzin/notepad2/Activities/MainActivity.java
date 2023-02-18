package com.farzin.notepad2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.farzin.notepad2.AppConfig.AppConfig;
import com.farzin.notepad2.R;

public class MainActivity extends AppCompatActivity {

     AppCompatTextView forgot_password;
     AppCompatButton signin_btn;
     AppCompatEditText username, password;

     AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rounded_login);

        forgot_password = findViewById(R.id.forgot_password);
        signin_btn = findViewById(R.id.signin_btn);
        username = findViewById(R.id.user);
        password = findViewById(R.id.password);

        appConfig = new AppConfig(getApplicationContext());

        username.setText(appConfig.saveUser() + "");
        password.setText(appConfig.savePassword() + "");



        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("farzin") && pass.equals("1234")){

                    appConfig.saveUserPass(user,pass);
                    Intent intent = new Intent(getApplicationContext(), NotePadActivity.class);
                    startActivity(intent);
                }else {

                    Toast.makeText(MainActivity.this, "user or password is wrong", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }


}