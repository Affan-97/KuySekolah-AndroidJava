package com.example.kuysekolah;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuysekolah.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {
    private ArrayList<com.example.kuysekolah.RecyclerItem> mRecyclerList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView txtnama,txtkelas,txtabsen;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private String userID;
    private Button Databtn,Absenbtn,Inputbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID = firebaseUser.getUid();

        txtnama     = findViewById(R.id.AbsenName);
        txtkelas    = findViewById(R.id.AbsenKelas);
        txtabsen    = findViewById(R.id.AbsenNumb);
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
                Toast.makeText(DataActivity.this,"Something Wrong!",Toast.LENGTH_LONG).show();
            }
        });
        createRecyclerList();
        buildRecyclerView();
    }
    private void createRecyclerList(){
        mRecyclerList = new ArrayList<>();
        mRecyclerList.add(new com.example.kuysekolah.RecyclerItem("Januari","1","2","3"));
        mRecyclerList.add(new com.example.kuysekolah.RecyclerItem("Febuari","1","2","3"));
        mRecyclerList.add(new com.example.kuysekolah.RecyclerItem("Maret","1","2","3"));
        mRecyclerList.add(new com.example.kuysekolah.RecyclerItem("April","1","2","3"));

    }

    private void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(mRecyclerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}