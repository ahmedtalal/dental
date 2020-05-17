package com.example.dentalcart.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Adapters.HomeAdapter;
import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.CartItemsViewModel;
import com.example.dentalcart.ViewModels.ProductsViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private View view ;
    private HomeAdapter adapter  ;
    private  ProgressBar bar ;
    private Context context ;
    private CircleImageView circleImageView , addedo;
    private TextView textView ;
    public HomeFragment(Context context) {
        this.context = context;
    }

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_home, container ,false);
        bar = view.findViewById(R.id.progressBarHome_id) ;
        addedo  = view.findViewById(R.id.addItemo_id) ;
        circleImageView = view.findViewById(R.id.itemCart_id) ;
        textView = view.findViewById(R.id.cartCountTV_id);
        bar.setVisibility(View.VISIBLE);
        // call search icon from main activity to search special item
        ((MainActivity)getActivity()).searchItemId.setVisibility(View.VISIBLE);
        intializeHomeViews(view);
        ((MainActivity)getActivity()).searchItemId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        // when click on additem icon , call addproductactivity
        addedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , AddproductActivity.class) ;
                startActivity(intent);
                ((Activity)context).finish();
            }
        });

        // when click on cart icon , call cart activity
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartActivity.class) ;
                startActivity(intent);
                ((Activity)context).finish();
            }
        });
        return view;
    }
    // this method is used to set adapter on fragment and get data from firebase
    private void intializeHomeViews(View view) {
        RecyclerView productRecycler = view.findViewById(R.id.productRecyler_id) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false) ;
        linearLayoutManager.setStackFromEnd(false);
        productRecycler.setLayoutManager(linearLayoutManager);

        ProductsViewModel viewModel = new ViewModelProvider(this).get(ProductsViewModel.class) ;
        viewModel.init();

        CartItemsViewModel cartItemsViewModel = new ViewModelProvider(this).get(CartItemsViewModel.class) ;
        cartItemsViewModel.init();

        List<ItemModel> lienoo = new ArrayList<>() ;
        cartItemsViewModel.getCarts().observe(this, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                if (itemModels.size() > 0){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(String.valueOf(itemModels.size()));
                    for (ItemModel itemModel : itemModels){
                        lienoo.add(itemModel);
                    }
                    Log.i("soze" , lienoo.size()+"");
                }else {
                    textView.setVisibility(View.INVISIBLE);
                }

            }
        });

        viewModel.getProductInfo().observe(this, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                adapter = new HomeAdapter(context , itemModels , lienoo) ;
                productRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 0){
                    bar.setVisibility(View.GONE);
                }
            }
        });

    }

}
