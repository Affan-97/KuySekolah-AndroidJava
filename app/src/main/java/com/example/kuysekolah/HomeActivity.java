package com.example.kuysekolah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kuysekolah.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    Button btnAbsen,btnJadwal,btnCat,btnNilai,btnLogout;
    private TextView txtnama,txtkelas;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    BroadcastReceiver _broadcastReceiver;
    private String userID;
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm");
    private TextView Jam;

    @Override
    public void onStart() {
        super.onStart();
        Jam = findViewById(R.id.idJam);
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    Jam.setText(_sdfWatchTime.format(new Date()));
            }
        };

        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (_broadcastReceiver != null)
            unregisterReceiver(_broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnCat      = findViewById(R.id.btnCat);
        btnAbsen    = findViewById(R.id.btnAbsen);
        btnJadwal   = findViewById(R.id.btnJadwal);
        btnNilai    =findViewById(R.id.btnNilai);
        btnLogout   = findViewById(R.id.buttonLogout);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID = firebaseUser.getUid();

        txtnama = findViewById(R.id.idName);
        txtkelas = findViewById(R.id.idKelas);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile != null){
                    String name = userprofile.name;
                    String kelas = userprofile.kelas;

                    txtnama.setText(name);
                    txtkelas.setText(kelas);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this,"Something Wrong!",Toast.LENGTH_LONG).show();
            }
        });




        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(HomeActivity.this,NotesActivity.class);
                startActivity(intent);
            }
        });
        btnAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(HomeActivity.this,AbsenActivity.class);
                startActivity(intent);
            }
        });
        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(HomeActivity.this,JadwalActivity.class);
                startActivity(intent);
            }
        });

        btnNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(HomeActivity.this,NilaiActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
            }
        });



    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}