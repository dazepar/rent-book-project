package com.example.primaryproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.firebase.firestore.util.Listener;

import java.util.ArrayList;

public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.AdvertiseVH> {

    Context context;
    ArrayList<Category> advertiseList;


    public AdvertiseAdapter(Context context, ArrayList<Category> advertiseList) {
        this.context = context;
        this.advertiseList = advertiseList;

    }

    @NonNull
    @Override
    public AdvertiseAdapter.AdvertiseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advertise_list, parent, false);
        return new AdvertiseAdapter.AdvertiseVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertiseAdapter.AdvertiseVH holder, int position) {
        Category popular = advertiseList.get(position);
        Glide.with(this.context)
                .load(popular.getImage())
                .into(holder.ivAdvertise);

    }

    @Override
    public int getItemCount() {
        return advertiseList.size();
    }

    public class AdvertiseVH extends RecyclerView.ViewHolder {
        ImageView ivAdvertise;
        public AdvertiseVH(@NonNull View itemView) {
            super(itemView);

            ivAdvertise=itemView.findViewById(R.id.ivAdvertise);
        }
    }
}
