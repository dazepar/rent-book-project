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
import com.example.primaryproject.Activity.StoryActivity;
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

public class StoryAdapter  extends RecyclerView.Adapter<StoryAdapter.CategoryVH> {
    ArrayList<Category> categories;
    Listener listener;
     Context context;
    public StoryAdapter(Context context,ArrayList<Category> categories, Listener listener) {
        this.categories = categories;
        this.listener = listener;
        this.context=context;
    }

    @NonNull
    @Override
    public StoryAdapter.CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list, parent, false);
        return new StoryAdapter.CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("Story");

        Category category = categories.get(position);



        Glide.with(this.context)
                .load(category.getImage())
                .into(holder.ivStory);

            holder.tvName.setText(category.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setOnDetailClick(category);
                }
            });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }




    public class CategoryVH extends RecyclerView.ViewHolder {
        ImageView ivStory;
        TextView tvName;

        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.ivStory);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
    public interface Listener {

        void setOnDetailClick(Category category);
    }
}
