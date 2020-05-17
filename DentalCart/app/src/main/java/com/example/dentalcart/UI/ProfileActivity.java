package com.example.dentalcart.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;
import com.example.dentalcart.ViewModels.UserViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.updateToolbar_id)
    Toolbar updateToolbarId;
    @BindView(R.id.profilePhoto)
    CircleImageView profilePhoto;
    @BindView(R.id.camer_ID)
    ImageView camerID;
    @BindView(R.id.profileName_ID)
    EditText profileNameID;
    @BindView(R.id.profileEmail_ID)
    EditText profileEmailID;
    @BindView(R.id.editBtn)
    Button editBtn;
    @BindView(R.id.profileripple)
    MaterialRippleLayout profileripple;

    private static final int RN_PHOTO = 2;
    private Uri imageUri;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(updateToolbarId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        updateToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        userViewModel.init();
        userViewModel.getUserInfo().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                profileNameID.setText(userModel.getName());
                profileEmailID.setText(userModel.getEmail());
                Picasso.get()
                        .load(userModel.getPhoto())
                        .error(R.drawable.ic_person)
                        .into(profilePhoto);
            }
        });

        camerID.setOnClickListener(this::onClick);
        editBtn.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camer_ID :
                openGallary();
                break;
            case R.id.editBtn :
                UpdateProfile() ;
                break;
        }
    }

    private void UpdateProfile() {
        String name = profileNameID.getText().toString().trim() ;
        String email = profileEmailID.getText().toString().trim() ;
        if (imageUri.getPath().equals("null")){
            Toast.makeText(ProfileActivity.this, "Please Enter the photo", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        FirebaseOperations.updateUserInfo(name , email , imageUri , progressDialog ,ProfileActivity.this);
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
                    .into(profilePhoto);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =  new Intent(ProfileActivity.this , MainActivity.class) ;
        startActivity(intent);
        finishAffinity();
    }
}
