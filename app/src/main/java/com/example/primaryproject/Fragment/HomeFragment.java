package com.example.primaryproject.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.primaryproject.Activity.DetailActivity;
import com.example.primaryproject.Activity.StoryActivity;
import com.example.primaryproject.Adapter.AdvertiseAdapter;
import com.example.primaryproject.Adapter.HomeAdapter;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class HomeFragment extends Fragment implements HomeAdapter.Listener {
    private FirebaseFirestore db;

    ArrayList<Category> popularList;
    HomeAdapter popularAdapter;
    AdvertiseAdapter advertiseAdapter;
    private RecyclerView rvPopular, rvAdvertise;

    ArrayList<Category> advertiseList;
    String idPopular;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db=FirebaseFirestore.getInstance();

        rvPopular = view.findViewById(R.id.rvPopular);
        popularList = new ArrayList<>();
        popularAdapter = new HomeAdapter(getContext(),popularList, HomeFragment.this);
        rvPopular.setAdapter(popularAdapter);
        rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));



        rvAdvertise = view.findViewById(R.id.rvAdvertise);
        advertiseList = new ArrayList<>();
        advertiseAdapter = new AdvertiseAdapter(getContext(),advertiseList);
        rvAdvertise.setAdapter(advertiseAdapter);
        rvAdvertise.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));





        db.collection("Story")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            idPopular=document.getId();
                            String idcate = document.get("IdCate").toString();
                            String name = document.get("Name").toString();
                            String image = document.get("Image").toString();

                            popularList.add(new Category(idPopular,idcate, name, image));
                        }
                       popularAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        db.collection("Advertise").limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String image = document.get("Image").toString();
                            advertiseList.add(new Category(image));

                        }
                        advertiseAdapter.notifyDataSetChanged();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void setOnDetailClick(Category popular) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("idStory",popular.getId());
        Log.d("abc", "setOnDetailClick: " +popular.getId());
        startActivity(intent);
    }

}