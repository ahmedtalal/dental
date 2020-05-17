package com.example.dentalcart.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Adapters.AllreviewsAdapter;
import com.example.dentalcart.Pojo.ReviewsModel;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.ReviewsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowreviewsActivity extends AppCompatActivity {

    @BindView(R.id.toolbarReviews_id)
    Toolbar toolbarReviewsId;
    @BindView(R.id.recyclerReviews_id)
    RecyclerView recyclerReviewsId;
    @BindView(R.id.progressBarReviews_id)
    ProgressBar progressBarReviewsId;
    @BindView(R.id.no_review_id)
    LinearLayout noReviewId;
    private String id;
    private AllreviewsAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showreviews);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        setSupportActionBar(toolbarReviewsId);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarReviewsId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowreviewsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                adapter.clearData();
            }
        });
        progressBarReviewsId.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowreviewsActivity.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        recyclerReviewsId.setLayoutManager(linearLayoutManager);
        ReviewsViewModel reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        reviewsViewModel.reviewInit(id);
        reviewsViewModel.getReviewsInfo().observe(this, new Observer<List<ReviewsModel>>() {
            @Override
            public void onChanged(List<ReviewsModel> reviewsModels) {
                adapter = new AllreviewsAdapter(reviewsModels, ShowreviewsActivity.this);
                recyclerReviewsId.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() != 0) {
                    progressBarReviewsId.setVisibility(View.GONE);
                    noReviewId.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowreviewsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        adapter.clearData();
    }
}
