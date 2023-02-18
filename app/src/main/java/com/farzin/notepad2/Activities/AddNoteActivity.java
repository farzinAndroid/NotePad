package com.farzin.notepad2.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.farzin.notepad2.R;
import com.farzin.notepad2.database.NoteDBAdapter;
import com.farzin.notepad2.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;


public class AddNoteActivity extends AppCompatActivity {

    AppCompatEditText edt_title, edt_desc;
    AppCompatButton date_btn, time_btn;
    AppCompatImageView arrow_forward;
    FloatingActionButton btn_save;
    NoteDBAdapter noteDBAdapter;
    AppCompatTextView date_txt, time_txt;
    String date = "",time = "";
    int year,month,dayOfMonth;
    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edt_desc = findViewById(R.id.edt_desc);
        edt_title = findViewById(R.id.edt_title);
        date_btn = findViewById(R.id.date_btn);
        time_btn = findViewById(R.id.time_btn);
        date_txt = findViewById(R.id.date_txt);
        time_txt = findViewById(R.id.time_txt);


        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_title.getText().toString().trim().equals("") || edt_desc.getText().toString().trim().equals("") ||
                        date_txt.getText().toString().trim().equals("") || time_txt.getText().toString().trim().equals("")){

                    Snackbar.make(v,R.string.snack_note2, Snackbar.LENGTH_LONG)
                            .setTextColor(Color.WHITE)
                            .setAction(R.string.ok, v1 -> {

                            })
                            .setActionTextColor(Color.WHITE)
                            .show();

                }else {

                    Note note  = new Note();
                    note.setTitle(edt_title.getText().toString());
                    note.setDescription(edt_desc.getText().toString());
                    note.setDate(date);
                    note.setTime(time);

                    noteDBAdapter = new NoteDBAdapter(getApplicationContext());
                    noteDBAdapter.insertNote(note);
                    Snackbar.make(v,R.string.snack_note, Snackbar.LENGTH_SHORT)
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




        arrow_forward = findViewById(R.id.img_back);
        arrow_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time = hourOfDay+":"+minute;
                        time_txt.setText(time);

                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });
    }
}