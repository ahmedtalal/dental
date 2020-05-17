package com.example.dentalcart.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Adapters.CategoriesAdapter;
import com.example.dentalcart.Pojo.SettingModel;
import com.example.dentalcart.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private Context context ;
    private View view ;
    public CategoriesFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories, container ,false);
        intializeCategoriesViews(view);
        ((MainActivity)getActivity()).searchItemId.setVisibility(View.INVISIBLE);
        return view ;
    }

    private void intializeCategoriesViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.categoriesRecycler_id) ;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context , 2) ;
        recyclerView.setLayoutManager(gridLayoutManager);
        List<SettingModel> list = new ArrayList<>() ;
        list.add(new SettingModel("Orthodontics" , R.drawable.ic_brace));
        list.add(new SettingModel("Equipments" , R.drawable.ic_equipments));
        list.add(new SettingModel("Periodontics" , R.drawable.ic_toolsutensils));
        list.add(new SettingModel("Prosthodonics" , R.drawable.ic_prosthodonics));
        CategoriesAdapter categoriesAdapter =  new CategoriesAdapter(list , context);
        recyclerView.setAdapter(categoriesAdapter);
    }
}
