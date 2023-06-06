package com.example.kuysekolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kuysekolah.Model.Mapel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment1 extends Fragment {
    private TextView jadwal1,jadwal2,jadwal3,jadwal4,jadwal5,jadwal6,jadwal7,jadwal8,jadwal9,jadwal10;
    private DatabaseReference reference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_fragment1,container,false);
        jadwal1 =(TextView) rootview.findViewById(R.id.mapel1);
        jadwal2 =(TextView) rootview.findViewById(R.id.mapel2);
        jadwal3 =(TextView) rootview.findViewById(R.id.mapel3);
        jadwal4 =(TextView) rootview.findViewById(R.id.mapel4);
        jadwal5 =(TextView) rootview.findViewById(R.id.mapel5);
        jadwal6 =(TextView) rootview.findViewById(R.id.mapel6);
        jadwal7 =(TextView) rootview.findViewById(R.id.mapel7);
        jadwal8 =(TextView) rootview.findViewById(R.id.mapel8);
        jadwal9 =(TextView) rootview.findViewById(R.id.mapel9);
        jadwal10 =(TextView) rootview.findViewById(R.id.mapel10);


        reference= FirebaseDatabase.getInstance().getReference("Mapel");
        reference.child("Senin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Mapel pelajaran = snapshot.getValue(Mapel.class);
                if (pelajaran != null){
                    String pel1 = pelajaran.pel1;
                    String pel2 = pelajaran.pel2;
                    String pel3 = pelajaran.pel3;
                    String pel4 = pelajaran.pel4;

                    jadwal1.setText("Upacara");
                    jadwal2.setText(pel2);
                    jadwal3.setText(pel2);
                    jadwal4.setText(pel1);

                    jadwal5.setText(pel1);
                    jadwal6.setText(pel1);
                    jadwal7.setText(pel3);
                    jadwal8.setText(pel3);
                    jadwal9.setText(pel4);
                    jadwal10.setText(pel4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootview;


    }

}
