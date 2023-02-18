package com.farzin.notepad2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteDataBase extends SQLiteOpenHelper {

    public final static String DB_NAME = "note.db";
    final static int VERSION = 1;
    public final static String TABLE_NAME = "tbl_note";

    public NoteDataBase(@Nullable Context context) {
        super(context, DB_NAME, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "create table " + TABLE_NAME + " (id Integer PRIMARY KEY AUTOINCREMENT , title Text, description Text, time Text, date Text)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }



}
