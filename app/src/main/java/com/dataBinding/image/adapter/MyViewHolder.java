package com.dataBinding.image.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dataBinding.image.databinding.HomeDataRecyclerviewItemBinding;

public class MyViewHolder extends RecyclerView.ViewHolder {
    HomeDataRecyclerviewItemBinding homeDataRecyclerviewItemBinding;
    public MyViewHolder(HomeDataRecyclerviewItemBinding itemView) {
        super(itemView.getRoot());
        homeDataRecyclerviewItemBinding = itemView;
    }
}
