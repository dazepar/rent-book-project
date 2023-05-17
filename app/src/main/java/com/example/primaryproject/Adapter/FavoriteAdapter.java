package com.example.primaryproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Model.Category;

import com.example.primaryproject.Model.Favorite;
import com.example.primaryproject.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteVH> {

    ArrayList<Favorite> favorites;
    Context context;
    Listener listener;

    public FavoriteAdapter(ArrayList<Favorite> favorites, Context context, Listener listener) {
        this.favorites = favorites;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list, parent, false);
        return new FavoriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVH holder, int position) {

        Favorite favorite = favorites.get(position);



        Glide.with(this.context)
                .load(favorite.getImage())
                .into(holder.ivFavorite);

        holder.tvFavorite.setText(favorite.getName());
        holder.tvPublisher.setText(favorite.getPublisher());
        holder.tvPublishing_year.setText(favorite.getPublishing_year());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDetailClick(favorite);
            }
        });
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDeleteClick(favorite);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }



    public class FavoriteVH extends RecyclerView.ViewHolder {
        ImageView ivFavorite;
        ImageButton ibDelete;
        TextView tvFavorite,tvPublisher,tvPublishing_year;
        public FavoriteVH(@NonNull View itemView) {
            super(itemView);
            ibDelete=itemView.findViewById((R.id.ibDelete));
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            tvFavorite = itemView.findViewById(R.id.tvFavorite);
            tvPublisher = itemView.findViewById(R.id.tvPublisher);
            tvPublishing_year = itemView.findViewById(R.id.tvPublishing_year);
        }
    }

    //Xóa phim trong DS yêu thích
    public void deleteFavorite(Favorite  favorite)
    {
        favorites.remove(favorite);
        notifyDataSetChanged();
    }
    public interface Listener {
        void setOnDetailClick(Favorite favorite);

        void setOnDeleteClick(Favorite  favorite);
    }
}