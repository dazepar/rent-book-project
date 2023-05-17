package com.example.primaryproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchVH>{
    ArrayList<Category> searchBug;

    ArrayList<Category> search;

    //    giao tiếp giữa adapter và activity
    Context context;
    Listener listener;

    public SearchAdapter(ArrayList<Category> search, Context context,Listener listener) {
        this.search = search;
        this.context = context;
        this.searchBug = search;
        this.listener=listener;
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        gọi màn hình lên
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_row, parent, false);
        SearchVH searchVH = new SearchVH(view);
        return searchVH;
    }

    //lấy dữ liệu từ database lên
    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("Story");

        Category category = search.get(position);

//        StorageReference pathReference = storageRef.child(category.getImage());

        Glide.with(this.context)
                .load(category.getImage())
                .into(holder.Img);

        holder.Text.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDetailClick(category);
            }
        });
    }

    //    xét kích thước mảng
    @Override
    public int getItemCount() {
        if (search != null) {
            return search.size();
        }
        return 0;
    }

    public interface Listener {

        void setOnDetailClick(Category category);
    }

    //    Tham chiếu màn hình search_row
    class SearchVH extends RecyclerView.ViewHolder {

        ImageView Img;
        TextView Text;


        public SearchVH(@NonNull View itemView) {
            super(itemView);
            Img = itemView.findViewById(R.id.imageSearch);
            Text = itemView.findViewById(R.id.txSearch);

        }
    }


//       class SearchFilter extends Filter {
//
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String charString = constraint.toString();
//                if (charString.isEmpty())
//                {
//                    searchfilter = searches;
//                }
//                else
//                {
//                    List<Category> filteredList = new ArrayList<>();
//                    for (Category row : searches) {
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()))
//                        {
//                            filteredList.add(row);
//                        }
//                    }
//                    searchfilter = (ArrayList<Category>) filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = searchfilter;
//                return filterResults;
//
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                        searchfilter = (ArrayList<Category>) results.values;
//                        notifyDataSetChanged();
//            }
}


