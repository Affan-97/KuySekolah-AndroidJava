package com.example.kuysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    private TextView mViewsignup,mViewforgot;
    private EditText mViewEmail,mViewPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        mViewEmail = (EditText) findViewById(R.id.editLogin);
        mViewPass = (EditText) findViewById(R.id.editLoginPass);
        mViewsignup = (TextView) findViewById(R.id.txtSignin);
        mViewforgot = (TextView) findViewById(R.id.txtforgot) ;

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu!");
        progressDialog.setCancelable(false);

        mViewsignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        mViewforgot.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtSignin:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btnLogin:
                Login();
                break;
            case R.id.txtforgot:
                startActivity(new Intent(this,ResetActivity.class));
                break;
        }

    }

    private void Login() {
        String email    = mViewEmail.getText().toString().trim();
        String password = mViewPass.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){
                                startActivity(new Intent(getBaseContext(),HomeActivity.class));
                            }else{
                                user.sendEmailVerification();
                                Toast.makeText(LoginActivity.this,"Check Email For Verification",Toast.LENGTH_LONG).show();;

                            }


                        }else{
                            Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();;
                        }
                    }
                });
    }
}