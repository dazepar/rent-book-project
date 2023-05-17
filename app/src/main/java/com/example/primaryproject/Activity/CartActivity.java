package com.example.primaryproject.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.primaryproject.Adapter.FavoriteAdapter;
import com.example.primaryproject.Adapter.OrderAdapter;
import com.example.primaryproject.Model.Category;
import com.example.primaryproject.Model.Favorite;
import com.example.primaryproject.Model.Orders;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartActivity extends AppCompatActivity implements OrderAdapter.Listener {
    Button BtnContinue;
    TextView tvSumPrice;
    RecyclerView mRecyclerView;
    ArrayList<Orders> mOrderList;
    //  private OrderAdapter mAdapter;
    FirebaseFirestore db;
    OrderAdapter orderAdapter;

    String id;

    String idProduct;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        db = FirebaseFirestore.getInstance();
        mRecyclerView=findViewById(R.id.rcvCart);
        BtnContinue=findViewById(R.id.btContinue);
        tvSumPrice=findViewById(R.id.sumPrice);
        mOrderList= new ArrayList<>();

        orderAdapter=new OrderAdapter(this,mOrderList,CartActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(orderAdapter);
        // lay dl
        db.collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            id= document.getId();
                           idProduct =document.get("IdProduct").toString();
                            String name = document.get("Name").toString();
                            String image = document.get("Image").toString();
                            String publisher=document.get("Publisher").toString();
                            String publishing_year=document.get("Publishing_year").toString();
                            String days=document.get("days").toString();
                            String quantity=document.get("quantity").toString();
                          //  int priceLong = document.get("price").hashCode();// lỗi Field 'price' is not a java.lang.Number
                            Long price =  document.getLong("price");
                            mOrderList.add(new Orders (id,name,image,publisher,publishing_year,idProduct,quantity,days,price));
                        }
                        int sumPrice = 0;

                        for (Orders order : mOrderList) {
                            int price = Integer.parseInt(String.valueOf((order.getPrice())));
                            int quantity = Integer.parseInt(order.getQuantity());
                            int days = Integer.parseInt(order.getDays());

                            int totalPrice = price * quantity * days;
                            sumPrice += totalPrice;
                        }

                        tvSumPrice.setText(String.valueOf(sumPrice)+" $");
                        orderAdapter.notifyDataSetChanged();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        BtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });



    }



    @Override
    public void setOnDetailClick(Orders order) {
        Intent intent = new Intent(CartActivity.this, DetailActivity.class);
        //  intent.putExtra("category",order);
        intent.putExtra("idStory",order.getIdProduct() );
        startActivity(intent);

    }

@Override
    public void setOnEditClick(Orders order) {
    // Tạo dialog mới
    Dialog dialog = new Dialog(this);
    dialog.setContentView(R.layout.dialog);

    // Lấy view trong dialog
    EditText etQuantity = dialog.findViewById(R.id.etQuantity);
    EditText etDays = dialog.findViewById(R.id.etDays);
    Button btnSave = dialog.findViewById(R.id.btnSave);

    // Thiết lập số lượng và số ngày mặc định là giá trị hiện tại của đơn hàng
    etQuantity.setText(order.getQuantity());
    etDays.setText(order.getDays());


    // Thiết lập sự kiện khi nhấn nút Save
    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Lấy số lượng và số ngày mới từ các trường EditText trong dialog
            String newQuantity = etQuantity.getText().toString().trim();
            String newDays = etDays.getText().toString().trim();

            // Kiểm tra giá trị nhập vào có hợp lệ hay không
            if (TextUtils.isEmpty(newQuantity) || TextUtils.isEmpty(newDays)) {
                Toast.makeText(CartActivity.this, "Please enter quantity and days", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật trường "quantity" và "days" trong Firebase Firestore
            Map<String, Object> data = new HashMap<>();
            data.put("quantity", newQuantity);
            data.put("days", newDays);

            FirebaseFirestore.getInstance().collection("Cart").document(order.getId())
                    .update(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void  onComplete (@NonNull Task<Void> task) {
                            // Cập nhật lại đơn hàng trong list
                            mOrderList.set(mOrderList.indexOf(order), order);
//                                orderAdapter.notifyDataSetChanged();
                           orderAdapter.notifyDataSetChanged();

                            Toast.makeText(CartActivity.this, "Order updated successfully", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                             recreate();

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CartActivity.this, "Failed to update order", Toast.LENGTH_SHORT).show();
                            // Log.e(TAG, "Failed to update order", e);
                        }
                    });
        }
    });

    // Hiển thị dialog
    dialog.show();


    ;
    }
    public void setOnDeleteClick(Orders order) {

        db.collection("Cart").document(order.getId()) //
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CartActivity.this,"Đã xóa truyện khỏi giỏ hàng của !!",Toast.LENGTH_SHORT).show();
                        orderAdapter .deleteOrder(order);
                    }
                });
        recreate();
    }
}