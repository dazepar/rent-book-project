package com.example.primaryproject.Activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primaryproject.Adapter.SearchAdapter;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.Listener {
    private FirebaseFirestore db;

    SearchAdapter searchAdapter;

    SearchView searchView;
    //    biến list dùng để huwnsag dữ liệu từ database
    ArrayList<Category> list;

    String idSearch;
    String id;
    RecyclerView rcv;
    TextView Tx;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        db = FirebaseFirestore.getInstance();
        btn = findViewById(R.id.button);

//        rcv là khai báo RecyclerView
        rcv = findViewById(R.id.rvEmpty);
//        lấy dữ liệu truyền vào adapter
        list = new ArrayList<>();
//        adapter hiệm dữ liệu lên RecyclerView
        searchAdapter = new SearchAdapter(list, this,SearchActivity.this);
        rcv.setAdapter(searchAdapter);
//        Loại RecyclerView mún hiện
        rcv.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
//        lắng nghe sự kiện bấm vào nút sắp xếp
        btn.setOnClickListener(v -> {
            list.clear();
            SortData();
//            sau khi khai báo và so sánh thông qua thư viện comparable, sử dụng thuộc tính Collections.sort để sắp xếp dự liệu
//            Collections.sort(list);
//            hiện dữ liệu sau khi sắp xếp
//            searchAdapter = new SearchAdapter(list, this);
//            rcv.setAdapter(searchAdapter);

        });

        db.collection("Story")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            idSearch=document.getId();
                            String idcate = document.get("IdCate").toString();
                            String name = document.get("Name").toString();
                            String image = document.get("Image").toString();
                            list.add(new Category(idSearch,idcate, name, image));
                        }

                        searchAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SearchActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        khai báo hiện manu_search
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        menu.findItem(R.id.btnSearch).expandActionView();

//điều chỉnh kích thước của SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.btnSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            Tìm kiếm chính xác
            @Override
            public boolean onQueryTextSubmit(String s) {
                Searchdata(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d("================", s);
                Searchdata(s);

                return false;
            }
        });
        return true;
    }
    private void Searchdata(String s) {
        list.clear();
        db.collection("Story").orderBy("Name").startAt(s).endAt(s + "\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("+++++++++++++++++", document.getString("Name"));
                                idSearch=document.getId();
                                String idcate = document.get("IdCate").toString();
                                String name = document.get("Name").toString();
                                String image = document.get("Image").toString();
                                list.add(new Category(idSearch,idcate, name, image));
                            }
                            searchAdapter.notifyDataSetChanged();
                        }
                        else if (list != null) {
                            Toast.makeText(getApplicationContext(), "Truyện đã tồn tại", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                        String name = document.get("Name").toString();
                        String image = document.get("Image").toString();
                        list.add(new Category(idcate,name, image));
                    }
                    // Hiển thị danh sách user đã được sắp xếp theo tên
                    // ...
                    searchAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void setOnDetailClick(Category category) {
        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
        intent.putExtra("idStory", category.getId());
        Log.d("abc", "setOnDetailClick: " + category.getId());
        startActivity(intent);
    }
}










