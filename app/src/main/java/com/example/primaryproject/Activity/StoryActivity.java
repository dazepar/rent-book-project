package com.example.primaryproject.Activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primaryproject.Adapter.StoryAdapter;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StoryActivity extends AppCompatActivity implements StoryAdapter.Listener {

    RecyclerView rvmStory;
    String id;
    ImageView btnSort;
    String idStory;
    FirebaseFirestore db;
    ArrayList<Category> categories;
    StoryAdapter storyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        db = FirebaseFirestore.getInstance();
        btnSort = findViewById(R.id.btnSort);

        rvmStory = findViewById(R.id.rvmStory);
        categories = new ArrayList<>();
        storyAdapter = new StoryAdapter(this, categories, StoryActivity.this);
        rvmStory.setAdapter(storyAdapter);
        rvmStory.setLayoutManager(new GridLayoutManager(StoryActivity.this, 2));


        Intent intent = getIntent();
        id = intent.getStringExtra("IdCate");
        Log.d(TAG, "onCreate: " + id);

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categories.clear();
                SortData();
            }
        });


        db.collection("Story")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String idcate = document.get("IdCate").toString();
                            idStory = document.getId();
                            String name = document.get("Name").toString();
                            String image = document.get("Image").toString();
                            String publisher = document.get("Publisher").toString();
                            String publishing_year = document.get("Publishing_year").toString();
                            String description = document.get("Description").toString();

                            if(id.equals(idcate))
                                categories.add(new Category(id,idcate,idStory, name, image, publisher, publishing_year, description));
                        }

                        storyAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error getting documents: " + e.getMessage());
                    }
                });
    }


    @Override
    public void setOnDetailClick(Category category) {
        Intent intent = new Intent(StoryActivity.this, DetailActivity.class);
        intent.putExtra("idStory", category.getIdproduct());
        startActivity(intent);

    }

    private void SortData() {
        CollectionReference StoriesRef = db.collection("Story");
        Query query = StoriesRef.orderBy("Name");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Category> sortList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idcate = document.get("IdCate").toString();
                        idStory = document.getId();
                        String name = document.get("Name").toString();
                        String image = document.get("Image").toString();
                        String publisher = document.get("Publisher").toString();
                        String publishing_year = document.get("Publishing_year").toString();
                        String description = document.get("Description").toString();

                        if(id.equals(idcate))
                            categories.add(new Category(id,idcate,idStory, name, image, publisher, publishing_year, description));


                    }
                    // Hiển thị danh sách user đã được sắp xếp theo tên
                    // ...
                   storyAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}