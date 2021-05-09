package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.HotelsAdapter;

public class HotelsFragment extends RecyclerViewBaseFragment {

    private HotelsViewModel hotelsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int categoryFilter = getArguments().getInt("selectedElementCategory");

        hotelsViewModel = new ViewModelProvider(this).get(HotelsViewModel.class);

        hotelsViewModel.filteredHotels(categoryFilter);
        hotelsViewModel.getHotelsLiveData().
                observe(this, hotels -> setRecyclerViewElements(hotels));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new HotelsAdapter();
    }
}















