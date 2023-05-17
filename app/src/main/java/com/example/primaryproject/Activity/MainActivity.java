package com.example.primaryproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.primaryproject.Fragment.AccountFragment;
import com.example.primaryproject.Fragment.CategoryFragment;
import com.example.primaryproject.Fragment.HomeFragment;
import com.example.primaryproject.Fragment.FavoriteFragment;
import com.example.primaryproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{

    SearchView searchView;
    FrameLayout mainView;
    BottomNavigationView mBottomNavigationView;
    private Fragment mainViewC;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.searchView);
        mainView=findViewById(R.id.mainView);
        mBottomNavigationView = findViewById(R.id.bottomMenu);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                display(item.getItemId());
                return true;
            }
        });
        display(R.id.MnuHome);
    }


    void display(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.MnuHome:
                fragment = new HomeFragment();
                break;
            case R.id.MnuCategory:
                fragment = new CategoryFragment();
                break;
            case R.id.MnuFavorite:
                fragment = new FavoriteFragment();
                break;
            case R.id.MnuAccount:
                fragment = new AccountFragment();
                break;
        }
        // ft sẽ thế "fragment" layout vào giao diện chính bằng hàm replace
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainView, fragment);
        ft.commit();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            return  true;

        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_nav,menu);
        return super.onCreateOptionsMenu(menu);
    }

}