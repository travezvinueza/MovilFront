package com.ricardo.front.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Boolean> userCreated = new MutableLiveData<>();

    public LiveData<Boolean> getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean created) {
        userCreated.setValue(created);
    }
}
