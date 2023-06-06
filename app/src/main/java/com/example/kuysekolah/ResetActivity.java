package com.example.kuysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {
        private EditText emailedit;
        private Button resetbutton;
        FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        emailedit = (EditText) findViewById(R.id.forgotemail);
        resetbutton=(Button) findViewById(R.id.btnReset);

        mAuth = FirebaseAuth.getInstance();
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset();
            }
        });
    }

    private void Reset() {
        String email = emailedit.getText().toString().trim();

        if(email.isEmpty()){
            emailedit.setError("Field is Required!");
            emailedit.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedit.setError("Invalid Email!");
            emailedit.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetActivity.this,"check Your Email Addres!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ResetActivity.this,"Try Again",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}