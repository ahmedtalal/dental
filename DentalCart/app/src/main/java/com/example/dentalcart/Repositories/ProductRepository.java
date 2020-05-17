package com.example.dentalcart.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.dentalcart.Pojo.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static ProductRepository repository ;
    private MutableLiveData<List<ItemModel>> mutableLiveData = new MutableLiveData<>() ;
    private List<ItemModel>  lists = null ;
    //is used to create one instance from repository class
    public static ProductRepository getInstance(){
        if (repository == null){
            repository = new ProductRepository() ;
        }
        return repository;
    }

    // this method is used to get data from firebase and set these data in mutabilelivedata
    public MutableLiveData<List<ItemModel>> getProducts(){
        lists = new ArrayList<>() ;
        FirebaseDatabase fDB = FirebaseDatabase.getInstance() ;;
        DatabaseReference reference = fDB.getReference().child("Products") ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    lists.add(snapshot.getValue(ItemModel.class)) ;
                }
                mutableLiveData.setValue(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
        return mutableLiveData ;
    }

//    private void loadData() {
//        FirebaseDatabase fDB = FirebaseDatabase.getInstance() ;;
//        DatabaseReference reference = fDB.getReference().child("Products") ;
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
//                    lists.add(snapshot.getValue(ItemModel.class)) ;
//                }
//                mutableLiveData.postValue(lists);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        }) ;
//    }
}
