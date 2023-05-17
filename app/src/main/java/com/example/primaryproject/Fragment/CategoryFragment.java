package com.example.primaryproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primaryproject.Activity.StoryActivity;
import com.example.primaryproject.Adapter.CategoryAdapter;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements CategoryAdapter.Listener {

    FirebaseFirestore db;

    RecyclerView rvCategory;
    TextView tvName;

    CategoryAdapter categoryAdapter;

    ArrayList<Category> category;

    ActivityResultLauncher<Intent> laucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );


    public CategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();

        rvCategory = view.findViewById(R.id.rvCategory);
        tvName = view.findViewById(R.id.tvName);


        category = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(getContext(), category, CategoryFragment.this);

        rvCategory.setAdapter(categoryAdapter);

        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override

                    public void onComplete(Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.get("Name").toString();
                            category.add(new Category(name, id));
                        }

                        categoryAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void setOnStoryClick(Category category) {
        Intent intent = new Intent(getActivity(), StoryActivity.class);
        intent.putExtra("IdCate", category.getIdcate());
        Log.d("============", "setOnStoryClick: " + category.getIdcate());
        startActivity(intent);
    }
}