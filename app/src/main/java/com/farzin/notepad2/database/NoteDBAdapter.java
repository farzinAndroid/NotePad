package com.farzin.notepad2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.farzin.notepad2.models.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteDBAdapter extends NoteDataBase{


    public final static String KEY_ID = "id";
    public final static String KEY_TITLE = "title";
    public final static String KEY_DESCRIPTION = "description";
    public final static String KEY_TIME = "time";
    public final static String KEY_DATE = "date";


    public NoteDBAdapter(@Nullable Context context) {
        super(context);
    }

    public boolean insertNote(Note note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,note.getTitle());
        contentValues.put(KEY_DESCRIPTION,note.getDescription());
        contentValues.put(KEY_TIME,note.getTime());
        contentValues.put(KEY_DATE,note.getDate());

        long insert = db.insert(NoteDataBase.TABLE_NAME, null, contentValues);
        if (insert == -1){
            return false;
        }else {
            return true;
        }

    }

    public List<Note> getAllNotes(){


        List<Note> noteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + NoteDataBase.TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){

            Note note = new Note();

            int id = cursor.getInt(0);
            note.setId(id);

            String title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
            note.setTitle(title);
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));
            note.setDescription(desc);
            String time = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TIME));
            note.setTime(time);
            String date = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE));
            note.setDate(date);

            noteList.add(note);

        }

        cursor.close();
        db.close();
        return noteList;
    }

    public List<Note> searchAllNotes(String title){

        List<Note> noteList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        //NOT A GOOD QUERY BECAUSE OF INJECTIONS
        String query = "select * from "+ NoteDataBase.TABLE_NAME + " where title='" + title+"'";

        //BEST QUERY
        String search_query = "select * from "+ NoteDataBase.TABLE_NAME + " where title= ?";
        String[] args = {title};

        Cursor cursor = db.rawQuery(search_query,args);


        while (cursor.moveToNext()){

            Note note = new Note();

            int id = cursor.getInt(0);
            note.setId(id);
            String title_db = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
            note.setTitle(title_db);
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));
            note.setDescription(desc);
            String time = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TIME));
            note.setTime(time);
            /**///SHORTER
            note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE)));

            noteList.add(note);

        }

        db.close();
        cursor.close();
        return noteList;
    }


    public int deleteNote(int id) {

        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,"id=?",new String[] {String.valueOf(id)});

    }

    public void updateNotes(String originalNoteTitle,
                            String noteTitle, String description, String time, String date){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,noteTitle);
        contentValues.put(KEY_DESCRIPTION,description);
        contentValues.put(KEY_TIME,time);
        contentValues.put(KEY_DATE,date);

        db.update(TABLE_NAME,contentValues,"title = ?" , new String[] {originalNoteTitle});
        db.close();

    }

}
