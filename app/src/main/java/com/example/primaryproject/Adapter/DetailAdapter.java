package com.example.primaryproject.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Activity.DetailActivity;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.CategoryVH> {
    ArrayList<Category>categories;
    Listener listener;
    Context context;

    public DetailAdapter(ArrayList<Category> categories,Context context,Listener listener) {
        this.categories = categories;
        this.context=context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetailAdapter.CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail, parent, false);
        return new DetailAdapter.CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.CategoryVH holder, int position) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        Category category=categories.get(position);
        StorageReference pathReference = storageRef.child(category.getImage());



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    public class CategoryVH extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvName;
        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            ivPoster=itemView.findViewById(R.id.ivPoster);
            tvName=itemView.findViewById(R.id.tvName);

        }
    }
    public interface Listener {
    }
}
