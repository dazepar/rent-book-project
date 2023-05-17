package com.example.primaryproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeVH> {

    Context context;
    ArrayList<Category> homeList;
    Listener listener;

    public HomeAdapter(Context context, ArrayList<Category> homeList,Listener listener) {
        this.context = context;
        this.homeList = homeList;
        this.listener= listener;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, parent, false);
        return new HomeAdapter.HomeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeVH holder, int position) {
       Category popular = homeList.get(position);

        holder.tvNamePopular.setText(popular.getName());
        Glide.with(this.context)
                .load(popular.getImage())
                .into(holder.ivPopular);
        holder.ivPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDetailClick(popular);
            }


        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public interface Listener {
        void setOnDetailClick(Category popular);
    }

    public class HomeVH extends RecyclerView.ViewHolder {
        TextView tvNamePopular;
        ImageView ivPopular;
        public HomeVH(@NonNull View itemView) {
            super(itemView);
            tvNamePopular=itemView.findViewById(R.id.tvNamePopular);
            ivPopular=itemView.findViewById(R.id.ivPopular);
        }
    }

}
