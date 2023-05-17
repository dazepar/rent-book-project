package com.example.primaryproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primaryproject.Model.PaymentInfo;
import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends AppCompatActivity {
    private Button btPay;
    private EditText etDeliveryAddress;
    private RadioGroup rgPaymentMethod;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btPay = findViewById(R.id.btPay);
        etDeliveryAddress = findViewById(R.id.etDeliveryAddress);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);

        // Khởi tạo đối tượng FirebaseFirestore để kết nối với Firestore
        db = FirebaseFirestore.getInstance();

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin thanh toán từ người dùng
                String deliveryAddress = etDeliveryAddress.getText().toString();
                String paymentMethod = "";
                switch (rgPaymentMethod.getCheckedRadioButtonId()) {
                    case R.id.momo:
                        paymentMethod = "Momo";
                        break;
                    case R.id.cash:
                        paymentMethod = "Cash";
                        break;
                    case R.id.visa:
                        paymentMethod = "Visa Card";
                        break;
                }

                // Tạo đối tượng PaymentInfo để lưu thông tin thanh toán
                PaymentInfo paymentInfo = new PaymentInfo(paymentMethod, deliveryAddress);

                // Xóa dữ liệu cũ trên Firestore
                db.collection("paymentInfo").document("info").delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Lưu dữ liệu mới vào Firestore
                            db.collection("paymentInfo").document("info").set(paymentInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Chuyển đến màn hình cảm ơn
                                        Intent intent = new Intent(PaymentActivity.this, ThankActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(PaymentActivity.this, "Lỗi khi lưu thông tin thanh toán", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(PaymentActivity.this, "Lỗi khi xóa dữ liệu cũ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}