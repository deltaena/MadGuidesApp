package com.example.madguidesapp.ui.mainMenu.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.AppRepository;
import com.example.madguidesapp.pojos.MainMenuElement;

import java.util.List;

public class MainMenuViewModel extends ViewModel {

    private AppRepository appRepository;
    private MutableLiveData<List<MainMenuElement>> mainMenuElements;

    public MainMenuViewModel(){
        appRepository = new AppRepository();
        mainMenuElements = new MutableLiveData<>();

        appRepository.getMainMenuElements().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        mainMenuElements.setValue(task.getResult().toObjects(MainMenuElement.class));
                    }
                });
    }

    LiveData<List<MainMenuElement>> getMainMenuElements(){
        return mainMenuElements;
    }
}



















