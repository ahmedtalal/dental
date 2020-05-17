package com.example.dentalcart.UI;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dentalcart.Adapters.CartAdapter;
import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.CartItemsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.frameLaouts_id)
    FrameLayout frameLaoutsId;
    @BindView(R.id.searchItem_id)
    SearchView searchItemId;
    @BindView(R.id.bottomNavigation_id)
    BottomNavigationView bottomNavigationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigationId.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLaouts_id , new HomeFragment(MainActivity.this)).commit() ;


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null ;
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                selectedFragment = new HomeFragment(MainActivity.this) ;
                break;
            case R.id.nav_favorite:
                selectedFragment = new FavoriteFragment() ;
                break;
            case R.id.nav_offers:
                selectedFragment = new OfferFragment() ;
                break;
            case R.id.nav_settings:
                selectedFragment = new SettingsFragment(MainActivity.this) ;
                break;
            case R.id.nav_categories:
                selectedFragment = new CategoriesFragment(MainActivity.this) ;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLaouts_id , selectedFragment).commit() ;
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
