package com.farzin.notepad2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.farzin.notepad2.R;
import com.farzin.notepad2.Utils.RVAdapter;
import com.farzin.notepad2.database.NoteDBAdapter;
import com.farzin.notepad2.database.NoteDataBase;
import com.farzin.notepad2.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class NotePadActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbar;
    NavigationView nav;
    DrawerLayout drawerLayout;
    FloatingActionButton float_btn;
    NoteDataBase noteDataBase;
    NoteDBAdapter noteDBAdapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);

        float_btn = findViewById(R.id.floating);
        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= \"#FFFFFF\">" + getString(R.string.app_name) + "</font>"));

        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(NotePadActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerToggle.syncState();

        noteDataBase = new NoteDataBase(getApplicationContext());
        noteDBAdapter = new NoteDBAdapter(getApplicationContext());



        float_btn.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
            startActivity(intent);
        });



        nav = findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.exit:

                        //finishAffinity();

                        AlertDialog.Builder alert = new AlertDialog.Builder(NotePadActivity.this);
                        alert.setTitle(R.string.exit_app);
                        alert.setIcon(R.drawable.exit);
                        alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        });

                        alert.show();
                        break;


                    case R.id.add_note:
                        Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent);
                        break;

                    case R.id.show_all:
                        showAllNotes();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.share_app:
                        Intent intent_share = new Intent(Intent.ACTION_SEND);
                        intent_share.putExtra(Intent.EXTRA_SUBJECT,"https://play.google.com/store/apps/details?id=" + getPackageName());
                        intent_share.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_app_message));
                        startActivity(Intent.createChooser(intent_share,getString(R.string.share_link)));
                        break;

                }
                return false;

            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.query_hint));
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.about:

                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);

                break;

            case R.id.contact:

                Intent intent_contact = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:09203168496"));
                startActivity(intent_contact);
                break;

            case R.id.exit:

                finishAffinity();
                break;

            case R.id.search:
                break;

            case R.id.web:
                Intent intent_web = new Intent(Intent.ACTION_VIEW,Uri.parse("https://android-learn.ir"));
                startActivity(intent_web);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
//        Dialogs.showDialog(NotePadActivity.this);
        AlertDialog.Builder alert = new AlertDialog.Builder(NotePadActivity.this);
        alert.setTitle(R.string.exit_app);
        alert.setIcon(R.drawable.exit);
        alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        showAllNotes();
    }

    private void showAllNotes() {
        List<Note> noteList = noteDBAdapter.getAllNotes();

        List<String> titles = new ArrayList<>();
        List<String> desc = new ArrayList<>();

        for (Note note : noteList){
            titles.add(note.getTitle());
        }

        Activity thisActivity=(Activity)this;
        RVAdapter rvAdapter = new RVAdapter(NotePadActivity.this,titles,noteList,thisActivity);
        rv.setAdapter(rvAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(linearLayoutManager);






    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        intent.putExtra("search",query);
        startActivity(intent);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}