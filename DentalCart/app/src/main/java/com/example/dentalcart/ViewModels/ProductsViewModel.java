package com.example.dentalcart.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.Repositories.ProductRepository;

import java.util.List;

public class ProductsViewModel extends ViewModel {
    MutableLiveData<List<ItemModel>> listMutableLiveData ;
    ProductRepository productRepository ;
    public void init(){
        if (listMutableLiveData != null){
            return;
        }
        productRepository = ProductRepository.getInstance();
        listMutableLiveData = productRepository.getProducts() ;
    }

    public LiveData<List<ItemModel>> getProductInfo(){
        return listMutableLiveData ;
    }
}
