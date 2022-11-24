package com.example.bismillah_air.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillah_air.API.History;
import com.example.bismillah_air.R;

import java.util.List;

public class GrafikAdapter extends RecyclerView.Adapter<GrafikAdapter.GrafikViewHolder>{

    List<History> postList;
    Context context;

    public GrafikAdapter(Context context, List<History> posts){
        this.context = context;
        postList = posts;
    }

    @NonNull
    @Override
    public GrafikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grafik_item , parent, false);
        return new GrafikAdapter.GrafikViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrafikViewHolder holder, int position) {
        History post = postList.get(position);

        holder.a = post.getDate().toString();
        holder.b = post.getDate_time().toString();
        holder.c.setText(holder.b);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



    public class GrafikViewHolder extends RecyclerView.ViewHolder {
        String a, b;
        TextView c;

        public GrafikViewHolder(@NonNull View itemView) {
            super(itemView);

            a = "a";
            b = "b";
            c = itemView.findViewById(R.id.text);
        }
    }




}
