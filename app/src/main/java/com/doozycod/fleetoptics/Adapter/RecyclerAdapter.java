package com.doozycod.fleetoptics.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    Context context;

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view, parent, false);

        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView employeeName;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.emp_name);
        }
    }
}
