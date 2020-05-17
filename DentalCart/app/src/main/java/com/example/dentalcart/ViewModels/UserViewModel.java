package com.example.dentalcart.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.Repositories.GetUserRepository;

public class UserViewModel extends ViewModel {
    MutableLiveData<UserModel> mutableLiveData ;
    GetUserRepository repository ;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        repository = GetUserRepository.getInstance() ;
        mutableLiveData = repository.getUser() ;
    }

    public LiveData<UserModel> getUserInfo(){
        return mutableLiveData ;
    }
}
