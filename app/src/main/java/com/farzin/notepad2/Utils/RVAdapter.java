package com.farzin.notepad2.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.farzin.notepad2.Activities.NotePadActivity;
import com.farzin.notepad2.Activities.UpdateActivity;
import com.farzin.notepad2.R;
import com.farzin.notepad2.database.NoteDBAdapter;
import com.farzin.notepad2.models.Note;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private Context c;
    private List<String> title = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private Activity mActivity;

    public RVAdapter(Context c, List<String> title, List<Note> notes, Activity mActivity) {
        this.c = c;
        this.title = title;
        this.notes = notes;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.rv_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.title.setText(notes.get(position).getTitle());
        holder.deac.setText(notes.get(position).getDescription());
        holder.details.setText(notes.get(position).getDate());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, UpdateActivity.class);
                //pass all value
                intent.putExtra("title",notes.get(position).getTitle());
                intent.putExtra("desc",notes.get(position).getDescription());
                intent.putExtra("time",notes.get(position).getTime());
                intent.putExtra("date",notes.get(position).getDate());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(c,holder.more);
                popupMenu.inflate(R.menu.more_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.delete:

                                NoteDBAdapter noteDBAdapter = new NoteDBAdapter(c);
                                int result = noteDBAdapter.deleteNote(notes.get(position).getId());
                                if (result > 0){

                                    Snackbar.make(v,R.string.deleted, Snackbar.LENGTH_SHORT)
                                            .setTextColor(Color.WHITE)
                                            .show();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            notes.remove(notes.get(position));
                                            notifyItemRemoved(position);
                                            Intent intent = new Intent(c,NotePadActivity.class);
                                            mActivity.overridePendingTransition(0, 0);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            ((NotePadActivity)c).finish();
                                            mActivity.overridePendingTransition(0, 0);
                                            c.startActivity(intent);
                                        }
                                    },500);





                                }

                            default:
                                return false;
                        }

                    }
                });

                popupMenu.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView title, deac, details;
        private RelativeLayout parent;
        private AppCompatImageView more;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            parent = itemView.findViewById(R.id.parent);
            deac = itemView.findViewById(R.id.desc);
            more = itemView.findViewById(R.id.more_menu);
            details = itemView.findViewById(R.id.details);
        }
    }
}
