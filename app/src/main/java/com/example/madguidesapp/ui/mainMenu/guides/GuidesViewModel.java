package com.example.madguidesapp.ui.mainMenu.guides;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.AppRepository;
import com.example.madguidesapp.pojos.Guide;

import java.util.List;

public class GuidesViewModel extends ViewModel {
    private AppRepository appRepository;
    private MutableLiveData<List<Guide>> guidesMutableLiveData;

    public GuidesViewModel(){
        appRepository = new AppRepository();
        guidesMutableLiveData = new MutableLiveData<>();

        appRepository.getGuides().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        guidesMutableLiveData.setValue(task.getResult().toObjects(Guide.class));
                    }
                });
    }

    LiveData<List<Guide>> getGuidesLiveData(){
        return guidesMutableLiveData;
    }
}




















