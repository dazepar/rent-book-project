package com.example.primaryproject.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primaryproject.R;

public class ThankActivity extends AppCompatActivity {
    Button btBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);
        btBack=findViewById(R.id.btBackHome);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankActivity.this, MainActivity.class);
                intent.putExtra("fragment", "account");
                startActivity(intent);
            }
        });
    }
}