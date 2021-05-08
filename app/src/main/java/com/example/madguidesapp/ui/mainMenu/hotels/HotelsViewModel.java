package com.example.madguidesapp.ui.mainMenu.hotels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.madguidesapp.AppRepository;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.HotelCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HotelsViewModel extends ViewModel {
    private static final String TAG = "HotelsViewModel";

    private AppRepository appRepository;

    private MutableLiveData<List<HotelCategory>> hotelCategoriesMutableLiveData;
    private MutableLiveData<List<Hotel>> hotelsMutableLiveData;

    public HotelsViewModel(){
        appRepository = new AppRepository();

        hotelCategoriesMutableLiveData = new MutableLiveData<>();
        hotelsMutableLiveData = new MutableLiveData<>();

        appRepository.getHotelCategories().
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        hotelCategoriesMutableLiveData.setValue(
                                task.getResult().toObjects(HotelCategory.class));
                    }
                });
    }

    public void filteredHotels(int categoryFilter){
        appRepository.getHotels(categoryFilter).
                addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        hotelsMutableLiveData.setValue(task.getResult().toObjects(Hotel.class));
                    }
                    else{
                        Log.d(TAG, "filteredHotels: "+task.getException().getMessage());
                    }
                });
    }

    public LiveData<List<HotelCategory>> getHotelCategoriesLiveData(){
        return hotelCategoriesMutableLiveData;
    }

    public LiveData<List<Hotel>> getHotelsLiveData(){
        return hotelsMutableLiveData;
    }
}

















