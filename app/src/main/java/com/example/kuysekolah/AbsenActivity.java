package com.example.kuysekolah;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kuysekolah.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AbsenActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mViewname;
    private TextView mtextLogin;
    private TextView txtnama,txtkelas,txtabsen;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private String userID;
    private Button Databtn,Absenbtn,Inputbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID = firebaseUser.getUid();

        txtnama     = findViewById(R.id.AbsenName);
        txtkelas    = findViewById(R.id.AbsenKelas);
        txtabsen    = findViewById(R.id.AbsenNumb);

        Databtn = findViewById(R.id.dataAbsen);
        Absenbtn=findViewById(R.id.inputAbsen);


        mAuth = FirebaseAuth.getInstance();

        Databtn.setOnClickListener(this);
        Absenbtn.setOnClickListener(this);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile != null){
                    String name = userprofile.name;
                    String kelas = userprofile.kelas;
                    String absen = userprofile.absen;

                    txtnama.setText(name);
                    txtkelas.setText(kelas);
                    txtabsen.setText(absen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AbsenActivity.this,"Something Wrong!",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.dataAbsen:
                startActivity(new Intent(this,DataActivity.class));
                break;
            case R.id.inputAbsen:
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AbsenActivity.this
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet,
                                (LinearLayout) findViewById(R.id.bottomSheetContainer)

                        );
                Button Input = (Button) bottomSheetView.findViewById(R.id.DialogInput);
                EditText Textinput = (EditText) bottomSheetView.findViewById(R.id.absendialoginput) ;

                Input.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nama = Textinput.getText().toString().trim();
                        if(nama.isEmpty()){
                            Textinput.setError("Field is Required!");
                            Textinput.requestFocus();
                            return;
                        }
                        FirebaseDatabase.getInstance().getReference("Absen").setValue(nama).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(AbsenActivity.this,"Berhasil Absen",Toast.LENGTH_LONG).show();
                                    BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(AbsenActivity.this);
                                    View bottomsheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_done, (LinearLayout) findViewById(R.id.bottomSheetContainer2)
                                    );
                                    bottomSheetDialog1.setContentView(bottomsheet);
                                    bottomSheetDialog1.show();

                                    bottomSheetDialog.dismiss();
                                }else{
                                    Toast.makeText(AbsenActivity.this,"Berhasil Absen",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                break;
        }

    }

}