package com.example.kuysekolah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuysekolah.Helper.Helper;
import com.example.kuysekolah.Model.Model;
import com.example.kuysekolah.Model.Nilai;
import com.example.kuysekolah.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NilaiActivity extends AppCompatActivity {

    private TextView txtnama,txtkelas,txtabsen;
    RecyclerView recyclerView;
    DatabaseReference reference,referenceuser;
    NilaiAdapter mAdapter;
    FirebaseUser firebaseUser;
    String userID;
    ArrayList<Nilai> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();

        txtnama = findViewById(R.id.nilaiNama);
        txtkelas = findViewById(R.id.nilaiKelas);
        txtabsen = findViewById(R.id.nilaiAbsen);

        recyclerView = findViewById(R.id.listMapel);

        referenceuser = FirebaseDatabase.getInstance().getReference("Users");
        reference = FirebaseDatabase.getInstance().getReference("Nilai");
        recyclerView.setHasFixedSize(true);

        int numberOfColumns = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        list = new ArrayList<>();

        mAdapter = new NilaiAdapter(this,list);

        recyclerView.setAdapter(mAdapter);



        referenceuser.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
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

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Nilai nilai = dataSnapshot.getValue(Nilai.class);
                    list.add(nilai);

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}