package com.doozycod.fleetoptics.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.fleetoptics.Interface.CallbackListener;
import com.doozycod.fleetoptics.R;

public class SignOutRecyclerAdapter extends RecyclerView.Adapter<SignOutRecyclerAdapter.SignHolder> {
    Context context;
    CallbackListener callbackListener;

    public SignOutRecyclerAdapter(Context context, CallbackListener callbackListener) {
        this.context = context;
        this.callbackListener = callbackListener;
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

    void setCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class SignHolder extends RecyclerView.ViewHolder {
        TextView emp_nameSignout;

        public SignHolder(@NonNull View itemView) {
            super(itemView);
            emp_nameSignout = itemView.findViewById(R.id.emp_nameSignout);
        }
    }
}
