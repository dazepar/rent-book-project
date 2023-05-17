package com.example.primaryproject.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Adapter.DetailAdapter;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements DetailAdapter.Listener {
    TextView tvName, tvPublisher, tvYear, tvDescription,tvPriceDetail;
    ImageView ivPoster,ivFavorite;
    ImageButton ibFavorite;
    Button btnBuy, btnAddtoCart;
    String id;
    FirebaseFirestore db;
    ArrayList<Category> categories;
    String idStory;
    Button addCart;

    Category category;
    int price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = FirebaseFirestore.getInstance();
        categories = new ArrayList<>();
        tvPriceDetail=findViewById(R.id.tvPriceDetail);
        tvName = findViewById((R.id.tvName));
        tvPublisher = findViewById((R.id.tvPublisher));
        tvYear = findViewById((R.id.tvYear));
        tvDescription = findViewById((R.id.tvDescription));
        ivPoster = findViewById(R.id.ivPoster);
        ibFavorite = findViewById(R.id.ibFavorite);
        addCart=findViewById(R.id.btnAddtoCart);
        Intent intent = getIntent();
        idStory = intent.getStringExtra("idStory");


            DocumentReference docRef = db.collection("Story").document(idStory);


            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    tvName.setText(documentSnapshot.get("Name").toString());
//                    id=documentSnapshot.get("IdProduct").toString();
                    tvPublisher.setText(documentSnapshot.get("Publisher").toString());
                    tvYear.setText(documentSnapshot.get("Publishing_year").toString());
                    tvDescription.setText(documentSnapshot.get("Description").toString());

//                    //load giá
                      double priceDou = documentSnapshot.getDouble("price");
                       price=(int)priceDou;
//                     price = Integer.parseInt(documentSnapshot.getString("price"));
                  //int price = (int) Math.round(documentSnapshot.getDouble("price"));

                    tvPriceDetail.setText(price+"$");
                    // load hình
                    Glide.with(DetailActivity.this)
                            .load(documentSnapshot.get("Image").toString())
                            .into(ivPoster);

                    category = new Category(documentSnapshot.get("Image").toString());


                }
            });



        // Tạo sự kiện thêm truyện vào DS yêu thích
        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        // tạo sự kiện thêm vào giỏ hàng
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });


    }

    public  void  addToCart(){
        String id=idStory;
        String name = tvName.getText().toString();
        String publisher=tvPublisher.getText().toString();
        String publishing_year=tvYear.getText().toString();
        String description=tvDescription.getText().toString();
        String imageUrl =category.getImage();

        Map<String, Object> itemsStory = new HashMap<>();


        itemsStory.put("IdProduct",id);
        itemsStory.put("Name", name );
        itemsStory.put("Image", imageUrl);
        itemsStory.put("Publisher", publisher);
        itemsStory.put("Publishing_year", publishing_year);
        itemsStory.put("Description",description);
       itemsStory.put("quantity", 1);
       itemsStory.put("days",3);
       itemsStory.put("price",price);


        db.collection("Cart")
                .whereEqualTo("IdProduct", id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size()==0) {
                            db.collection("Cart").add(itemsStory).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Truyện đã có ở giỏ hàng ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void insertData() {

        String idProduct=idStory;
        String name = tvName.getText().toString();
        String image= category.getImage();
        String publisher=tvPublisher.getText().toString();
        String publishing_year=tvYear.getText().toString();
        String description=tvDescription.getText().toString();



        Map<String, Object> itemsStory = new HashMap<>();
        itemsStory.put("IdProduct",idProduct);
        itemsStory.put("Image",image);
        itemsStory.put("Name", name );
        itemsStory.put("Publisher", publisher);
        itemsStory.put("Publishing_year", publishing_year);
        itemsStory.put("Description",description);
        itemsStory.put("price",price);



        db.collection("Favorite")
                .whereEqualTo("IdProduct", idProduct)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size()==0) {
                            db.collection("Favorite").add(itemsStory).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(getApplicationContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Truyện đã tồn tại", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}