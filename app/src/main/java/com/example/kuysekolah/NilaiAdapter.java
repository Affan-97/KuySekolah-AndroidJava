package com.example.kuysekolah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kuysekolah.Model.Nilai;

import java.util.ArrayList;
import java.util.List;

public class NilaiAdapter extends RecyclerView.Adapter<NilaiAdapter.ViewHolder> {
    Context context;
    ArrayList<Nilai> list;

    public NilaiAdapter(Context context, ArrayList<Nilai> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_listmapel,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nilai nilai = list.get(position);
        holder.mapel.setText(nilai.getMapel());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context,NilaiDetailActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout constraintLayout;
        TextView mapel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mapel = itemView.findViewById(R.id.info_text);
            constraintLayout=itemView.findViewById(R.id.list_mapel);
        }
    }

}