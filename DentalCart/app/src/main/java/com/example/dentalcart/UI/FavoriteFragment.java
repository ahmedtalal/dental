package com.example.dentalcart.UI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Adapters.FavoriteAdapter;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.GetAllProducts;


public class FavoriteFragment extends Fragment {
    private View view ;
    private ProgressBar bar ;
    private FavoriteAdapter favoriteAdapter;
    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_favorite, container ,false);
        bar = view.findViewById(R.id.favvbar_id) ;
        bar.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).searchItemId.setVisibility(View.INVISIBLE);
        intializeFavotiteViews(view);
        return view;
    }
    private void intializeFavotiteViews(View view) {
        RecyclerView productfavRecycler = view.findViewById(R.id.productFavoRecyler_id) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false) ;
        linearLayoutManager.setStackFromEnd(false);
        productfavRecycler.setLayoutManager(linearLayoutManager);
                GetAllProducts getAllProducts = new GetAllProducts(getContext()) ;
                getAllProducts.get();
                favoriteAdapter = new FavoriteAdapter(getContext() ,getAllProducts.favoriteProduct) ;
                productfavRecycler.setAdapter(favoriteAdapter);
                favoriteAdapter.notifyDataSetChanged();
                if (favoriteAdapter.getItemCount() > 0){
                    bar.setVisibility(View.GONE);
                }
     }


    }

