package com.example.kuysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuysekolah.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mViewname, mViewEmail,mViewKelas,mViewAbsen,mViewPass,mViewRepass;
    private ProgressDialog progressDialog;
    private TextView mtextLogin;
    private FirebaseAuth mAuth;
    private Button mRegisbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mViewname = findViewById(R.id.RegisFull);
        mViewEmail = findViewById(R.id.RegisEmail);
        mViewKelas = findViewById(R.id.RegisKelas);
        mViewAbsen = findViewById(R.id.Regisabsen);
        mViewPass = findViewById(R.id.RegisPass);
        mViewRepass = findViewById(R.id.RegisRepass);
        mRegisbtn = findViewById(R.id.buttonRegis);
        mtextLogin = findViewById(R.id.txtLogin);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu!");
        progressDialog.setCancelable(false);

        mRegisbtn.setOnClickListener(this);
        mtextLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
            case R.id.txtLogin:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        case R.id.buttonRegis:
            Register();
            break;
    }
    }

    private void Register() {
        String email    = mViewEmail.getText().toString().trim();
        String password = mViewPass.getText().toString().trim();
        String repass   = mViewRepass.getText().toString().trim();
        String absen    = mViewAbsen.getText().toString().trim();
        String name     = mViewname.getText().toString().trim();
        String kelas    = mViewKelas.getText().toString().trim();

        if(name.isEmpty()){
            mViewname.setError("Field is Required!");
            mViewname.requestFocus();
            return;
        }
        if(kelas.isEmpty()){
            mViewKelas.setError("Field is Required!");
            mViewKelas.requestFocus();
            return;
        }
        if(absen.isEmpty()){
            mViewAbsen.setError("Field is Required!");
            mViewAbsen.requestFocus();
            return;
        }
        if(email.isEmpty()){
            mViewEmail.setError("Field is Required!");
            mViewEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mViewEmail.setError("Invalid Email!");
            mViewEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mViewPass.setError("Field is Required!");
            mViewPass.requestFocus();
            return;
        }
        if(password.length()<8){
            mViewPass.setError("Password Length should be 8 characters!");
            mViewPass.requestFocus();
            return;
        }
        if(repass.isEmpty()){
            mViewRepass.setError("Field is Required!");
            mViewRepass.requestFocus();
            return;
        }
        if(!repass.equals(password)){
            mViewRepass.setError("password didn't match!");
            mViewRepass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,kelas,email,absen);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.sendEmailVerification();
                                            Toast.makeText(RegisterActivity.this,"Check Email For Verification",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getBaseContext(),LoginActivity.class));


                                    }else{
                                        Toast.makeText(RegisterActivity.this,"Failed to Register",Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Failed to Register",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}