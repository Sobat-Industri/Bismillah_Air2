package com.example.bismillah_air.Adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bismillah_air.API.History;

import java.util.List;

public class GrafikAdapter extends RecyclerView.Adapter<GrafikAdapter>{

    List<History> postList;
    Context context;

    public GrafikAdapter(Context context, List<History> posts){
        this.context = context;
        postList = posts;
    }




}
