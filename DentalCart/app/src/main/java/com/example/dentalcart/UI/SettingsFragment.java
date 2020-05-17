package com.example.dentalcart.UI;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Adapters.SettingAdapter;
import com.example.dentalcart.Pojo.SettingModel;
import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.ViewModels.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsFragment extends Fragment {
    private View view ;
    private Context context ;
    private CircleImageView circleImageView ;
    private TextView nameTV ;
    private RecyclerView recyclerView ;
    public SettingsFragment(Context context ) {
        this.context = context ;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_settings, container ,false);
        intializeSettingsViews(view);
        ((MainActivity)getActivity()).searchItemId.setVisibility(View.INVISIBLE);

        return view;
    }
    private void intializeSettingsViews(View view) {
        circleImageView = view.findViewById(R.id.personImgo_id) ;
        nameTV = view.findViewById(R.id.namePerson_id) ;
        recyclerView = view.findViewById(R.id.settingRecycler_id) ;
        UserViewModel userViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(UserViewModel.class) ;
        userViewModel.init();
        userViewModel.getUserInfo().observe((LifecycleOwner) context, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                nameTV.setText(userModel.getName());
                Picasso.get()
                        .load(userModel.getPhoto())
                        .error(R.drawable.ic_person)
                        .into(circleImageView);
            }
        });
        List<SettingModel> list = new ArrayList<>();
        list.add(new SettingModel("Profile" , R.drawable.ic_person)) ;
        list.add(new SettingModel("Tell a friend" , R.drawable.ic_sharego)) ;
        list.add(new SettingModel("Logout" , R.drawable.ic_logout)) ;
        SettingAdapter adapter =  new SettingAdapter(list , context) ;
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false) ;
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}
