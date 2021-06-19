package com.example.respirapp.ui.respiraciones;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RespiracionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RespiracionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}