package com.example.kuysekolah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuysekolah.Helper.Helper;

public class AddNotesActivity extends AppCompatActivity {
    EditText mTitle,mDesc;
    Button addNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        mTitle  = findViewById(R.id.title);
        mDesc   = findViewById(R.id.description);
        addNote = findViewById(R.id.addNote);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(mTitle.getText().toString())
                        && !TextUtils.isEmpty(mDesc.getText().toString())){
                    Helper mhelper = new Helper(AddNotesActivity.this);
                    mhelper.addNotes(mTitle.getText().toString(),mDesc.getText().toString());

                    Intent intent = new Intent(AddNotesActivity.this,NotesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else  {
                    Toast.makeText(AddNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}