package com.example.dentalcart.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;
import com.example.dentalcart.ViewModels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frameLaouts_id)
    FrameLayout frameLaoutsId;
    @BindView(R.id.searchItem_id)
    SearchView searchItemId;
    @BindView(R.id.bottomNavigation_id)
    BottomNavigationView bottomNavigationId;
    @BindView(R.id.navigation_id)
    NavigationView navigationId;
    @BindView(R.id.drawer_id)
    DrawerLayout drawerId;
    @BindView(R.id.toolbar_id)
    Toolbar toolbarId;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigationId.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this , drawerId , toolbarId , R.string.close , R.string.open) ;
        drawerId.addDrawerListener(toggle);
        toggle.syncState();
        bottomNavigationId.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        navigationId.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        loadFragment(new HomeFragment(MainActivity.this));
        loadInfoProfile();
    }

    // this method is used to get some information about your account like name and photo
    private void loadInfoProfile() {
        CircleImageView image = navigationId.getHeaderView(0).findViewById(R.id.photo_profile_id) ;
        TextView name = navigationId.getHeaderView(0).findViewById(R.id.name_profile_id) ;
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        viewModel.init();
        viewModel.getUserInfo().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                name.setText(userModel.getName());
                Picasso.get()
                        .load(userModel.getPhoto())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(image);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        Intent intentt = new Intent(MainActivity.this , ShowcategoriesActivity.class) ;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                toolbarId.setTitle("Home");
                selectedFragment = new HomeFragment(MainActivity.this) ;
                loadFragment(selectedFragment);
                break;
            case R.id.nav_favorite:
                toolbarId.setTitle("Favorite ");
                selectedFragment = new FavoriteFragment();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_offers:
                toolbarId.setTitle("Offers");
                selectedFragment = new OfferFragment();
                loadFragment(selectedFragment);
                break;
            case R.id.nav_logout :
                FirebaseOperations.singOut(MainActivity.this);
                break;
            case R.id.nav_profile :
                Intent intent =  new Intent(MainActivity.this, ProfileActivity.class) ;
                startActivity(intent);
                finish();
                break;
            case R.id.nav_share :
                Intent intent1 =  new Intent(MainActivity.this , ShareappActivity.class) ;
                 startActivity(intent1);
                 finish();
                 break;
            case R.id.nav_orthodontics :
                intentt.putExtra("type" , "Orthodontics") ;
                startActivity(intentt);
                finish();
                break;
            case R.id.nav_equipments :
                intentt.putExtra("type" , "Equipments") ;
                startActivity(intentt);
                finish();
                break;
            case R.id.nav_periodontics :
                intentt.putExtra("type" , "Periodontics") ;
                startActivity(intentt);
                finish();
                break;
            case R.id.nav_prosthodonics :
                intentt.putExtra("type" , "Prosthodonics") ;
                startActivity(intentt);
                finish();
                break;
        }
        return true;
    }

    // this method is used to load frament after is created
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLaouts_id , fragment) ;
        transaction.commit() ;
    }
    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation_id) ;
        if (bottomNavigationView.getSelectedItemId() != R.id.nav_home){
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }else {
            super.onBackPressed();
            finish();
        }

    }
}
