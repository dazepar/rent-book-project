package com.example.primaryproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primaryproject.Activity.DetailActivity;
import com.example.primaryproject.Adapter.FavoriteAdapter;
import com.example.primaryproject.Model.Category;

import com.example.primaryproject.Model.Favorite;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements FavoriteAdapter.Listener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView RvFavoriteList;
    ArrayList<Favorite> favorites;
    FirebaseFirestore db;
    FavoriteAdapter favoriteAdapter;


    String id;

    String idFavorite;


    public FavoriteFragment(){
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2){
        FavoriteFragment fragment=new FavoriteFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        RvFavoriteList = view.findViewById(R.id.rvFavoriteList);

        favorites = new ArrayList<>();

        favoriteAdapter = new FavoriteAdapter(favorites, getContext(), FavoriteFragment.this);

        RvFavoriteList.setAdapter(favoriteAdapter);

        RvFavoriteList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));




        db.collection("Favorite")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            id= document.getId();
                            idFavorite=document.get("IdProduct").toString();
                            String name = document.get("Name").toString();
                            String image = document.get("Image").toString();
                            String publisher=document.get("Publisher").toString();
                            String publishing_year=document.get("Publishing_year").toString();
                            favorites.add(new Favorite (id,idFavorite,name,image,publisher,publishing_year));
                        }

                        favoriteAdapter.notifyDataSetChanged();
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
    public void setOnDetailClick(Favorite  favorite) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("idStory", favorite.getIdproduct());
        startActivity(intent);

    }

    // Xóa truyện trong ds yêu thích
    @Override
    public void setOnDeleteClick(Favorite  favorite) {
        db.collection("Favorite").document(favorite.getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Đã xóa truyện khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                        favoriteAdapter.deleteFavorite(favorite);
                    }
                });

    }
}