package com.farzin.notepad2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.farzin.notepad2.R;

public class AboutActivity extends AppCompatActivity {

    AppCompatTextView code, name;
    Toolbar toolbar;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        code = findViewById(R.id.code);
        name = findViewById(R.id.name);

        bundle = getIntent().getExtras();



        if (bundle != null){
            code.setText(bundle.getInt("code") + "");
            name.setText(bundle.getString("name"));
        }


    }
}