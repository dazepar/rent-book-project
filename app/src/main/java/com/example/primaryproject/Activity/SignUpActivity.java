package com.example.primaryproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.primaryproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity{

    private Button btnSignup;
    private EditText fullname,email,password,cpassword,mob;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        fullname=findViewById(R.id.etFullName);
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPass);
        cpassword=findViewById(R.id.etConfirmPass);
        mob=findViewById(R.id.etMobileNumber);
        btnSignup=findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser(){
        final String uName = fullname.getText().toString().trim();
        final String uEmail = email.getText().toString().trim();
        final String uPassword = password.getText().toString().trim();
        final String uConfirmPassword= cpassword.getText().toString().trim();
        final String uMobile = mob.getText().toString().trim();

        if (uName.isEmpty()) {
            fullname.setError("Name cannot be empty");
        }
        if (uEmail.isEmpty()) {
            email.setError("Enter a valid email address");
        }
        if (TextUtils.isEmpty(uPassword) ){
            password.setError("combination of alphabet and numbers");
        } else if (uPassword.length()>12) {
            password.setError("Password can not be more than 12 characters ");
        }

        if (TextUtils.isEmpty(uConfirmPassword)){
            cpassword.setError("combination of alphabet and numbers");
        }

        if ((uMobile.length()<10) || (uMobile.length()>10)){
            mob.setError("enter a valid 10 digit number");
        }

        else {
            mAuth.createUserWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    }
                    else {
                        Toast.makeText(SignUpActivity.this,"User Authentication Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}