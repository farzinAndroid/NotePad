package com.farzin.notepad2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.farzin.notepad2.R;
import com.farzin.notepad2.database.NoteDBAdapter;
import com.farzin.notepad2.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateActivity extends AppCompatActivity {

    private Bundle bundle;
    private AppCompatImageView img_back;
    private AppCompatEditText edt_title , edt_desc;
    private AppCompatTextView date_txt , time_txt;
    private AppCompatButton date_btn, time_btn;
    private FloatingActionButton btn_save;
    private String date = "",time = "";
    private int year,month,dayOfMonth;
    private int hour,minute;
    private NoteDBAdapter noteDBAdapter;
    private Note note;
    String title,desc,txt_time,txt_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initViews();
        noteDBAdapter = new NoteDBAdapter(getApplicationContext());
        getExtras();



        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = year + "/" + month + "/" + dayOfMonth;
                        date_txt.setText(date);

                    }

                },year,month,dayOfMonth);

                datePickerDialog.show();
            }
        });

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time = hourOfDay+":"+minute;
                        time_txt.setText(time);

                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_title.getText().toString().trim().equals("") || edt_desc.getText().toString().trim().equals("") ||
                        time_txt.getText().toString().trim().equals("") || date_txt.getText().toString().trim().equals("")){

                    Snackbar.make(v, R.string.snack_note2,Snackbar.LENGTH_SHORT)
                            .setTextColor(Color.WHITE)
                            .setAction(R.string.ok, v1 -> {

                            })
                            .setActionTextColor(Color.WHITE)
                            .show();
                }else {


                    noteDBAdapter.updateNotes(title,edt_title.getText().toString(),edt_desc.getText().toString()
                            ,time_txt.getText().toString(),date_txt.getText().toString());

                    Snackbar.make(v, R.string.snack_update,Snackbar.LENGTH_SHORT)
                            .setTextColor(Color.WHITE)
                            .show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },500);

                }





            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }



    private void getExtras() {
        bundle = getIntent().getExtras();
        if (bundle != null){

            title = bundle.getString("title");
            desc = bundle.getString("desc");
            txt_time = bundle.getString("time");
            txt_date = bundle.getString("date");
        }

        edt_title.setText(title);
        edt_desc.setText(desc);
        time_txt.setText(txt_time);
        date_txt.setText(txt_date);
    }


    private void initViews() {
        edt_title = findViewById(R.id.edt_title);
        edt_desc = findViewById(R.id.edt_desc);
        date_txt = findViewById(R.id.date_txt);
        time_txt = findViewById(R.id.time_txt);
        date_btn = findViewById(R.id.date_btn);
        time_btn = findViewById(R.id.time_btn);
        btn_save = findViewById(R.id.btn_save);
        img_back = findViewById(R.id.img_back);
    }
}