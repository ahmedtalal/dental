package com.example.dentalcart.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dentalcart.Pojo.ReviewsModel;
import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;
import com.example.dentalcart.Repositories.GeneralOperations;
import com.example.dentalcart.ViewModels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.reviewToolbar_id)
    Toolbar reviewToolbarId;
    @BindView(R.id.rateFeedback_id)
    RatingBar rateFeedbackId;
    @BindView(R.id.feedback_id)
    EditText feedbackId;
    @BindView(R.id.feebackButton)
    Button feebackButton;

    private String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        setSupportActionBar(reviewToolbarId);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this is toolbar action
        reviewToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this , MainActivity.class) ;
                startActivity(intent);
                finish();
            }
        });

        feebackButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.feebackButton){
            String feedback = feedbackId.getText().toString() ;
            int rating = (int) rateFeedbackId.getRating();
            checkFields(feedback , rating) ;
        }
    }

    private void checkFields(String feedback, int rating) {
        if (feedback.equals("")) {
            feedbackId.setError("username  is required");
            feedbackId.requestFocus();
            return;
        }
        if (rating == 0) {
            Toast.makeText(FeedbackActivity.this , "Please enter your rating" , Toast.LENGTH_LONG).show();
            return;
        }
        prepareFeedbackData(feedback , rating , id) ;
    }

    private void prepareFeedbackData(String feedback, int rating, String id) {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        userViewModel.init();
        userViewModel.getUserInfo().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                String name = userModel.getName() ;
                String photo = userModel.getPhoto();
                Log.i("users" , userModel.getName() + photo);
                ReviewsModel reviewsModel = new ReviewsModel(name , feedback , photo , rating) ;
                GeneralOperations.addFeedBack(reviewsModel , id , FeedbackActivity.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FeedbackActivity.this , MainActivity.class) ;
        startActivity(intent);
        finish();
    }
}
