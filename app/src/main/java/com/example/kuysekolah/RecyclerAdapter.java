package com.example.kuysekolah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<com.example.kuysekolah.RecyclerItem> mRecyclerList;

    public RecyclerAdapter(ArrayList<com.example.kuysekolah.RecyclerItem> RecyclerList) {
        mRecyclerList = RecyclerList;
    }

    public static  class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView mText1,mText2,mText3,mText4;

        public RecyclerViewHolder(View itemView){
            super(itemView);
            mText1 = itemView.findViewById(R.id.text1);
            mText2 = itemView.findViewById(R.id.text2);
            mText3 = itemView.findViewById(R.id.text3);
            mText4 = itemView.findViewById(R.id.text4);
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
      RecyclerViewHolder rvh = new RecyclerViewHolder(v);
      return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        com.example.kuysekolah.RecyclerItem currentitem = mRecyclerList.get(position);

        holder.mText1.setText(currentitem.getmText1());
        holder.mText2.setText(currentitem.getmText2());
        holder.mText3.setText(currentitem.getmtext3());
        holder.mText4.setText(currentitem.getmText4());


    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }
}
