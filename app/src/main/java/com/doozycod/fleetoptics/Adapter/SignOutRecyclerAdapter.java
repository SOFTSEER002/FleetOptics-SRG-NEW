package com.doozycod.fleetoptics.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.R;

public class SignOutRecyclerAdapter extends RecyclerView.Adapter<SignOutRecyclerAdapter.SignHolder> {
    Context context;

    public SignOutRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SignOutRecyclerAdapter.SignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expenditors_recycler_v, parent, false);
        return new SignHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignOutRecyclerAdapter.SignHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class SignHolder extends RecyclerView.ViewHolder {
        public SignHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
