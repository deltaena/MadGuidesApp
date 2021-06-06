package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.HotelCategoriesAdapter;

public class HotelCategoriesFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getHotelCategoriesLiveData().
                observe(this, hotelCategories -> setRecyclerViewElements(hotelCategories));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new HotelCategoriesAdapter();
    }
}
