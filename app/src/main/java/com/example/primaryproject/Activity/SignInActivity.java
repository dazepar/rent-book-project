package com.example.primaryproject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.primaryproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity{


    private Button signIn, signUp, forgotPassword;
    private TextInputEditText userName, pass;
    private ProgressDialog progressDialog;
    private AppCompatImageButton googleBtn;
    private AppCompatImageButton facebookBtn;
    private final static int RC_SIGN_IN = 123;

    FirebaseAuth firebaseAuth;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.etUser);
        pass = findViewById(R.id.etPass);
        signIn = findViewById(R.id.buttonLogin);
        signUp = findViewById(R.id.buttonSignup);
        forgotPassword = findViewById(R.id.buttonForgot);
        googleBtn = findViewById(R.id.btn_gg);
        facebookBtn = findViewById(R.id.btn_fb);

        progressDialog = new ProgressDialog(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            navigateToSecondActivity();
        }

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, FacebookActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinProcess();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,ForgotPasswordActivity.class));
            }
        });
    }
    private void signinProcess(){
        try {
            String email = userName.getText().toString().trim();
            String password = pass.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                userName.setError("email cannot be empty");
            }
            if (TextUtils.isEmpty(password)){
                pass.setError("password cannot be empty");
            }

            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        finish();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }

                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this,"Invalid username/password",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityIfNeeded(signInIntent,1000);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch(ApiException e){
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}