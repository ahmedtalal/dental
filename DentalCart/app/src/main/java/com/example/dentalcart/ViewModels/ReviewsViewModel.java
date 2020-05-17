package com.example.dentalcart.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dentalcart.Pojo.ReviewsModel;
import com.example.dentalcart.Repositories.ReviewsRepository;

import java.util.List;

public class ReviewsViewModel extends ViewModel {
    MutableLiveData<List<ReviewsModel>> mutableLiveData ;
    ReviewsRepository reviewsRepository ;

    public void reviewInit(String id){
        if (mutableLiveData != null){
            return;
        }
        reviewsRepository = ReviewsRepository.getInstance() ;
        mutableLiveData = reviewsRepository.getReviews(id) ;
    }

    public LiveData<List<ReviewsModel>> getReviewsInfo(){
        return mutableLiveData;
    }
}
