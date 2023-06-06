package com.example.kuysekolah;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuysekolah.Helper.Helper;
import com.example.kuysekolah.Model.Model;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button mTambah;
    Adapter adapter;
    List<Model> notesList;
    Helper mHelper;
   ConstraintLayout mConstraintLayout;
   ImageView mDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        recyclerView = findViewById(R.id.notes);
        mTambah = findViewById(R.id.tambah);
        mDelete = findViewById(R.id.delete);
        mConstraintLayout= findViewById(R.id.layout_note);

        mTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this,AddNotesActivity.class);
                startActivity(intent);
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAllNote();
            }
        });


        notesList = new ArrayList<>();
        mHelper = new Helper(this);
        AmbilNotes();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,NotesActivity.this,notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(NotesActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
    void AmbilNotes(){
        Cursor cursor = mHelper.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                notesList.add(new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }

    }
    private void DeleteAllNote(){
        Helper mhelper = new Helper(NotesActivity.this);
        mhelper.deleteAllNotes();
        recreate();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            Model item = adapter.getList().get(position);

            adapter.removeItem(viewHolder.getAdapterPosition());
            Snackbar snackbar = Snackbar.make(mConstraintLayout,"item Deleted",Snackbar.LENGTH_LONG)
                     .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            if (!(event == DISMISS_EVENT_ACTION)){
                                Helper mhelper = new Helper(NotesActivity.this);
                                mhelper.deleteSingleItem(item.getId());
                            }
                        }
                    });

            snackbar.show();

        }
    };
}
