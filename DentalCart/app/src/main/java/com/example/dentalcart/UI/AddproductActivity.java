package com.example.dentalcart.UI;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.GeneralOperations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddproductActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.addProductToolbar_id)
    Toolbar addProductToolbarId;
    @BindView(R.id.done_id)
    ImageView doneId;
    @BindView(R.id.productImgo_id)
    CircleImageView productImgoId;
    @BindView(R.id.camera_id)
    ImageView cameraId;
    @BindView(R.id.productName_id)
    EditText productNameId;
    @BindView(R.id.price_id)
    EditText priceId;
    @BindView(R.id.discount_id)
    EditText discountId;
    @BindView(R.id.description_id)
    EditText descriptionId;
    @BindView(R.id.favorite_id)
    RadioButton favoriteId;
    @BindView(R.id.unfavorite_id)
    RadioButton unfavoriteId;
    @BindView(R.id.rateo_id)
    AppCompatRatingBar rateoId;
    @BindView(R.id.spinner_id)
    Spinner spinnerId;

    private static final int RN_PHOTO = 2;
    private Uri imageUri;
    private DatabaseReference reference;
    private String favoriteo = null;
    private ProgressDialog progressDialog;
    private String category = null;

    private String[] categoriesType = {
           "orthodontics" ,
           "equipments" ,
           "periodontics" ,
           "prosthodonics"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        ButterKnife.bind(this);
        setSupportActionBar(addProductToolbarId);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addProductToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddproductActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference();
        cameraId.setOnClickListener(this::onClick);
        doneId.setOnClickListener(this::onClick);
        favoriteId.setOnClickListener(this::onClick);
        unfavoriteId.setOnClickListener(this::onClick);

        setSprinngItems();
    }

    // this method is used to set date in spinner
    private void setSprinngItems() {
        ArrayAdapter adapter = new ArrayAdapter(this , android.R.layout.simple_spinner_item , categoriesType) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerId.setAdapter(adapter);
        spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (categoriesType[position]){
                    case "orthodontics" :
                        category = "orthodontics" ;
                        break;
                    case "equipments" :
                        category = "equipments" ;
                        break;
                    case "periodontics" :
                        category = "periodontics" ;
                        break;
                    case "prosthodonics" :
                        category = "prosthodonics" ;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "orthodontics" ;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_id:
                openGallary();
                break;
            case R.id.done_id:
                checkFieldsBeforeAdding();
                break;
            case R.id.favorite_id:
                favoriteo = "true";
                break;
            case R.id.unfavorite_id:
                favoriteo = "false";
                break;
        }
    }

    private void checkFieldsBeforeAdding() {
        String name = productNameId.getText().toString().trim();
        String price = priceId.getText().toString().trim();
        String discount = discountId.getText().toString().trim();
        String descrption = descriptionId.getText().toString().trim();
        String rating = String.valueOf(rateoId.getRating());
        String id = reference.push().getKey();
        if (TextUtils.isEmpty(name)) {
            productNameId.setError("this field is required");
            productNameId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            priceId.setError("this field is required");
            priceId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(discount)) {
            discountId.setError("this field is required");
            discountId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(descrption)) {
            descriptionId.setError("this field is required");
            descriptionId.requestFocus();
            return;
        }
        if (imageUri.getPath().equals("null")) {
            Toast.makeText(AddproductActivity.this, "Please Enter the photo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (category == null){
            Toast.makeText(AddproductActivity.this, "Please select category", Toast.LENGTH_SHORT).show();
            return;
        }
        ItemModel itemModel = new ItemModel
                (
                        descrption,
                        discount,
                        favoriteo,
                        id,
                        name,
                        rating,
                        price,
                        category
                );
        progressDialog = new ProgressDialog(AddproductActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        GeneralOperations.AddProduct(itemModel, imageUri, progressDialog, AddproductActivity.this);

    }



    private void openGallary() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "completed action"), RN_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RN_PHOTO && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Picasso.get()
                    .load(imageUri)
                    .into(productImgoId);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddproductActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
