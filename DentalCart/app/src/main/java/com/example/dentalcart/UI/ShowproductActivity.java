package com.example.dentalcart.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShowproductActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.showProductImage_id)
    ImageView showProductImageId;
    @BindView(R.id.showProductToolbar_id)
    Toolbar showProductToolbarId;
    @BindView(R.id.reviewsShow_id)
    ImageView reviewsShowId;
    @BindView(R.id.nameShow_id)
    TextView nameShowId;
    @BindView(R.id.priceShow_id)
    TextView priceShowId;
    @BindView(R.id.discountShow_id)
    TextView discountShowId;
    @BindView(R.id.descriptionShow_id)
    TextView descriptionShowId;
    @BindView(R.id.ratingShow_id)
    RatingBar ratingShowId;
    @BindView(R.id.favShowProduct_id)
    CircleImageView favShowProductId;
    @BindView(R.id.shareShowProduct_id)
    CircleImageView shareShowProductId;
    @BindView(R.id.addReviewShowProduct_id)
    TextView addReviewShowProductId;
    //these are variables to recieve data from activity
    String name ;
    String description ;
    String favorite ;
    String price ;
    String discount ;
    String id ;
    float rating ;
    String photo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproduct);
        ButterKnife.bind(this);
        setSupportActionBar(showProductToolbarId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        showProductToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowproductActivity.this ,  MainActivity.class) ;
                startActivity(intent);
                finish();
            }
        });
        IntializeData();
        favShowProductId.setOnClickListener(this::onClick);
        addReviewShowProductId.setOnClickListener(this::onClick);
        reviewsShowId.setOnClickListener(this::onClick);

    }

    @SuppressLint("SetTextI18n")
    private void IntializeData() {
        Intent intent = getIntent() ;
        name = intent.getStringExtra("name") ;
        description = intent.getStringExtra("description") ;
        favorite = intent.getStringExtra("favorite") ;
        price = intent.getStringExtra("price") ;
        discount = intent.getStringExtra("descount") ;
        id = intent.getStringExtra("id");
        rating = intent.getFloatExtra("rating" , 0) ;
        photo = intent.getStringExtra("photo") ;

        //intialize here
        Picasso.get()
                .load(photo)
                .placeholder(R.drawable.logo)
                .into(showProductImageId);
        nameShowId.setText("Name : " + name);
        priceShowId.setText("Price : " + price + " EGP");
        discountShowId.setText("Discount : " + discount + "% off");
        descriptionShowId.setText("Description : " + description);

        if (rating != 0){
            ratingShowId.setRating(rating);
        }
        if (favorite.equals("true")){
            favShowProductId.setImageResource(R.drawable.ic_favorito);
        }else {
            favShowProductId.setImageResource(R.drawable.ic_unfavorite);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.favShowProduct_id:
                favoriteFunc();
                break;
            case R.id.addReviewShowProduct_id:
                passingData(id) ;
                break;
            case R.id.reviewsShow_id:
                passingId(id) ;
        }
    }

    private void passingId(String id) {
        Intent intent = new Intent(ShowproductActivity.this , ShowreviewsActivity.class) ;
        intent.putExtra("id" , id) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
        startActivity(intent);
        finish();
    }

    private void passingData(String id) {
        Intent intent = new Intent(ShowproductActivity.this , FeedbackActivity.class) ;
        intent.putExtra("id" , id) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
        startActivity(intent);
        finish();
    }

    private void favoriteFunc() {
        // update favorite value
        HashMap<String , Object> hashMap = new HashMap<>() ;
        if (favorite.equals("true")){
            hashMap .put("description" , description) ;
            hashMap.put("discount" , discount) ;
            hashMap.put("favorite" , "false") ;
            hashMap.put("id" , id) ;
            hashMap.put("name" , name);
            hashMap.put("photo" , photo) ;
            hashMap.put("price" , price) ;
            hashMap.put("rating" , String.valueOf(rating)) ;
            FirebaseOperations.updateData(id, hashMap , ShowproductActivity.this);
            favShowProductId.setImageResource(R.drawable.ic_favorito);
            finish();
        }else {
            hashMap .put("description" , description) ;
            hashMap.put("discount" , discount) ;
            hashMap.put("favorite" , "true") ;
            hashMap.put("id" , id) ;
            hashMap.put("name" , name);
            hashMap.put("photo" , photo) ;
            hashMap.put("price" , price) ;
            hashMap.put("rating" , String.valueOf(rating)) ;
            FirebaseOperations.updateData(id, hashMap , ShowproductActivity.this);
            favShowProductId.setImageResource(R.drawable.ic_unfavorite);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowproductActivity.this , MainActivity.class) ;
        startActivity(intent);
        finish();

    }
}
