package com.example.primaryproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {
    ArrayList<Category> categories;
    Context context;
    Listener listener;

    public CategoryAdapter(Context context, ArrayList<Category> categories, Listener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;

    }


    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        Category category = categories.get(position);

        holder.tvName.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnStoryClick(category);

            }
        });


    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


    class CategoryVH extends RecyclerView.ViewHolder {

        TextView tvName;

        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }


    }

  public interface Listener {
        void setOnStoryClick(Category category);
    }

}

