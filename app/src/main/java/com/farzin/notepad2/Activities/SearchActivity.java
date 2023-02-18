package com.farzin.notepad2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.farzin.notepad2.R;
import com.farzin.notepad2.database.NoteDBAdapter;
import com.farzin.notepad2.models.Note;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView list_search;
    Toolbar toolbar;
    AppCompatImageView img_back;
    AppCompatTextView txt_title;
    Bundle bundle;
    NoteDBAdapter noteDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list_search = findViewById(R.id.list_search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_title);

        bundle = getIntent().getExtras();
        String search = bundle.getString("search");
        txt_title.setText(search);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        noteDBAdapter = new NoteDBAdapter(getApplicationContext());
        List<Note> noteList = new ArrayList<>();
        noteList = noteDBAdapter.searchAllNotes(search);

        List<String> title = new ArrayList<>();

        for (Note note : noteList){
            title.add(note.getId()+"       " + note.getTitle());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,title);
        list_search.setAdapter(arrayAdapter);
    }
}