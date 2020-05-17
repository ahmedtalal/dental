package com.example.dentalcart.ViewModels;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.dentalcart.Pojo.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllProducts {

    private Context context ;
    public List<ItemModel> favoriteProduct ;
    public List<ItemModel> offerProduct ;
    public List<ItemModel> orthodonticsList ;
    public List<ItemModel> equipmentList ;
    public List<ItemModel> periodonticsList ;
    public List<ItemModel> prosthodonicsList ;
    public GetAllProducts(Context context) {
        this.context = context;
        favoriteProduct = new ArrayList<>();
        offerProduct = new ArrayList<>() ;
        orthodonticsList = new ArrayList<>();
        equipmentList = new ArrayList<>();
        periodonticsList = new ArrayList<>() ;
        prosthodonicsList = new ArrayList<>() ;
    }

    public void get(){
        ProductsViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductsViewModel.class) ;
        viewModel.init();
        viewModel.getProductInfo().observe((LifecycleOwner) context, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                for (ItemModel itemModel : itemModels){
                    if (itemModel.getFavorite().equals("true")){
                        favoriteProduct.add(itemModel) ;
                    }
                    if (!itemModel.getDiscount().equals("0")){
                        offerProduct.add(itemModel) ;
                    }
                    if (itemModel.getCategory().equals("orthodontics")){
                        orthodonticsList.add(itemModel) ;
                    }
                    if (itemModel.getCategory().equals("equipments")){
                        equipmentList.add(itemModel) ;
                    }
                    if (itemModel.getCategory().equals("periodontics")){
                        periodonticsList.add(itemModel) ;
                    }
                    if (itemModel.getCategory().equals("prosthodonics")){
                        prosthodonicsList.add(itemModel) ;
                    }
                }
            }
        });
    }

}
