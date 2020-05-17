package com.example.dentalcart.Repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.Pojo.ReviewsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRepository {
    private static ReviewsRepository reviewsRepository ;
    private MutableLiveData<List<ReviewsModel>> reviewsModelMutableLiveData = new MutableLiveData<>() ;
    private List<ReviewsModel> list = null ;

    public static ReviewsRepository getInstance(){
        if (reviewsRepository == null){
            reviewsRepository = new ReviewsRepository() ;
        }
        return reviewsRepository ;
    }

    public MutableLiveData<List<ReviewsModel>> getReviews(String id ){
        list = new ArrayList<>() ;
        FirebaseDatabase fDB = FirebaseDatabase.getInstance() ;;
        DatabaseReference reference = fDB.getReference().child("Reviews").child(id) ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(ReviewsModel.class)) ;
                }
                reviewsModelMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
        return reviewsModelMutableLiveData ;
    }
}
