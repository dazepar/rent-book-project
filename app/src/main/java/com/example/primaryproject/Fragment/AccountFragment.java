package com.example.primaryproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.primaryproject.Activity.CartActivity;
import com.example.primaryproject.Activity.SignInActivity;
import com.example.primaryproject.Model.PaymentInfo;
import com.example.primaryproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button Btcart, btnSignOut;
    TextView tvNameUser,tvEmailUser;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private PaymentInfo mPaymentInfo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this.getContext(), gso);

        auth=FirebaseAuth.getInstance();

        tvNameUser=view.findViewById(R.id.tvNameUser);
        tvEmailUser=view.findViewById(R.id.tvEmailUser);
        btnSignOut=view.findViewById(R.id.btnSignOut);


        DocumentReference docRef = db.collection("paymentInfo").document("info");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mPaymentInfo = document.toObject(PaymentInfo.class);
                        updatePaymentInfo();
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy thông tin thanh toán", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lỗi khi truy vấn thông tin thanh toán", Toast.LENGTH_SHORT).show();
                }
            }
        });
        

        Btcart = view.findViewById(R.id.btCart);
        Btcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String personName;
        String personEmail;
        if(acct == null){
            personName = user.getDisplayName();
            personEmail = user.getEmail();
        }else{
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
        }
        tvNameUser.setText(personName);
        tvEmailUser.setText(personEmail);


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });



    }
    private void updatePaymentInfo() {
        TextView paymentMethodTextView = getView().findViewById(R.id.payment_method);
        TextView addressTextView = getView().findViewById(R.id.address);

        paymentMethodTextView.setText(mPaymentInfo.getPaymentMethod());
        addressTextView.setText(mPaymentInfo.getDeliveryAddress());
    }
    public void signOut() {

        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });
    }
}