package com.example.dentalcart.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Adapters.ShowcategoriesAdapter;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.GetAllProducts;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowcategoriesActivity extends AppCompatActivity {

    @BindView(R.id.categoriesToolbar_id)
    Toolbar categoriesToolbarId;
    @BindView(R.id.showCategoriresRecycler_id)
    RecyclerView showCategoriresRecyclerId;

    private GetAllProducts getAllProducts ;
    private ShowcategoriesAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcategories);
        ButterKnife.bind(this);
        setSupportActionBar(categoriesToolbarId);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoriesToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowcategoriesActivity.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent() ;
        String type = intent.getStringExtra("type") ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowcategoriesActivity.this , LinearLayoutManager.VERTICAL , false) ;
        showCategoriresRecyclerId.setLayoutManager(linearLayoutManager);
        getAllProducts = new GetAllProducts(ShowcategoriesActivity.this) ;
        getAllProducts.get();
        showCategory(type) ;
    }

    private void showCategory(String type) {
        switch (type) {
            case "Orthodontics" :
                adapter = new ShowcategoriesAdapter(getAllProducts.orthodonticsList , ShowcategoriesActivity.this);
                showCategoriresRecyclerId.setAdapter(adapter);
                categoriesToolbarId.setTitle("Orthodontics");
                break;
            case "Equipments":
                adapter = new ShowcategoriesAdapter(getAllProducts.equipmentList , ShowcategoriesActivity.this);
                showCategoriresRecyclerId.setAdapter(adapter);
                categoriesToolbarId.setTitle("Equipments");
                break;
            case "Periodontics":
                adapter = new ShowcategoriesAdapter(getAllProducts.periodonticsList , ShowcategoriesActivity.this);
                showCategoriresRecyclerId.setAdapter(adapter);
                categoriesToolbarId.setTitle("Periodontics");
                break;
            case "Prosthodonics":
                adapter = new ShowcategoriesAdapter(getAllProducts.prosthodonicsList , ShowcategoriesActivity.this);
                showCategoriresRecyclerId.setAdapter(adapter);
                categoriesToolbarId.setTitle("Prosthodonics");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowcategoriesActivity.this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
