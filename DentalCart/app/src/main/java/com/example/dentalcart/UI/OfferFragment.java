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

import com.example.dentalcart.Adapters.OffersAdapter;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.GetAllProducts;


public class OfferFragment extends Fragment {
    private View view ;
    private ProgressBar bar ;
    private OffersAdapter offersAdapter;
    public OfferFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_offer, container ,false);
        bar = view.findViewById(R.id.offersbar_id) ;
        bar.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).searchItemId.setVisibility(View.INVISIBLE);
        intializeOffersViews(view);
        return view;
    }
    private void intializeOffersViews(View view) {
        RecyclerView offerRecycler = view.findViewById(R.id.offersRecyler_id) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false) ;
        linearLayoutManager.setStackFromEnd(false);
        offerRecycler.setLayoutManager(linearLayoutManager);
        GetAllProducts getAllProducts =new GetAllProducts(getContext()) ;
        getAllProducts.get();
        offersAdapter = new OffersAdapter(getContext() ,getAllProducts.offerProduct) ;
        offerRecycler.setAdapter(offersAdapter);
        offersAdapter.notifyDataSetChanged();
        if (offersAdapter.getItemCount() > 0){
            bar.setVisibility(View.GONE);
        }
    }
}
