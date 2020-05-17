package com.example.dentalcart.Repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.dentalcart.Pojo.ItemModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetCartItemRepository {
    private static GetCartItemRepository repository ;
    private MutableLiveData<List<ItemModel>> modelMutableLiveData = new MutableLiveData<>() ;
    private List<ItemModel> list = null ;
    public static GetCartItemRepository getInstance(){
        if (repository == null){
            repository = new GetCartItemRepository() ;
        }
        return repository;
    }

    public MutableLiveData<List<ItemModel>> getItemsFromCart(){
        list = new ArrayList<>() ;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = firebaseDatabase.getReference().child("Cart").child(user.getUid()) ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    list.add(snapshot.getValue(ItemModel.class));
                }
                modelMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return modelMutableLiveData;
    }
}
