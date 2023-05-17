package com.example.primaryproject.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primaryproject.Activity.CartActivity;
import com.example.primaryproject.Model.Orders;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    ArrayList<Orders> mOrderList;
    Context context;
    Listener listener;


    public OrderAdapter(Context context, ArrayList<Orders> mOrderList, Listener listener) {
        this.mOrderList = mOrderList;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("Cart");
        Orders order = mOrderList.get(position);


        Glide.with(this.context)
                .load(order.getImage())
                .into(holder.imageProduct);
        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnEditClick(order);
            }
        });
        holder.nameProductTextView.setText(order.getName());
        holder.tvPrice.setText(order.getPrice().toString());
        holder.tvQuantity.setText(order.getQuantity());
        holder.tvDays.setText(order.getDays());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDetailClick(order);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDeleteClick(order);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView nameProductTextView, tvPrice, tvQuantity, tvDays,sumPrice;
        ImageView imageProduct;
        ImageView ivUpdate;
        ImageView ivDelete;


        public OrderViewHolder(View itemView) {
            super(itemView);
            nameProductTextView = itemView.findViewById(R.id.tvNameitem);
            imageProduct = itemView.findViewById(R.id.ivAvatar);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivUpdate = itemView.findViewById(R.id.ivEdit);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.quantityNum);
            tvDays = itemView.findViewById(R.id.daysNum);


        }

    }

    //  @SuppressLint("NotifyDataSetChanged")
    public void deleteOrder(Orders order) {
        mOrderList.remove(order);
        notifyDataSetChanged();
    }

    public interface Listener {
        void setOnDeleteClick(Orders order);

        void setOnDetailClick(Orders order);

        void setOnEditClick(Orders orders);

    }

    public void editOrder(Orders order) {
        // Tạo dialog mới
        Dialog dialog = new Dialog(context);
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
                    Toast.makeText(context, "Please enter quantity and days", Toast.LENGTH_SHORT).show();
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
                                notifyDataSetChanged();

                                Toast.makeText(context, "Order updated successfully", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to update order", Toast.LENGTH_SHORT).show();
                                // Log.e(TAG, "Failed to update order", e);
                            }
                        });
            }
        });

        // Hiển thị dialog
        dialog.show();


    }

}

