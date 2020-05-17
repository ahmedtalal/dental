package com.example.dentalcart.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.Pojo.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetUserRepository {
    private static GetUserRepository repository ;
    private MutableLiveData<UserModel> mutableLiveData = new MutableLiveData<>() ;

    public static GetUserRepository getInstance(){
        if (repository == null){
            repository = new GetUserRepository() ;
         }
        return repository ;
    }

    public MutableLiveData<UserModel> getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase fDB = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = fDB.getReference().child("Users").child(user.getUid()) ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class) ;
                mutableLiveData.setValue(userModel);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
        return mutableLiveData ;
    }
}
