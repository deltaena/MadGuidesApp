package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.HotelCategoriesAdapter;

public class HotelCategoriesFragment extends RecyclerViewBaseFragment {

    private HotelsViewModel hotelsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotelsViewModel = new ViewModelProvider(this).get(HotelsViewModel.class);
        hotelsViewModel.getHotelCategoriesLiveData().
                observe(this, hotelCategories -> setRecyclerViewElements(hotelCategories));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new HotelCategoriesAdapter();
    }
}
