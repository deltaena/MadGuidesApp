package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;

public class HotelsFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int categoryFilter = getArguments().getInt("selectedElementCategory");

        drawerActivityViewModel.filterHotels(categoryFilter);
        drawerActivityViewModel.getHotelsLiveData().
                observe(this, hotels -> setRecyclerViewElements(hotels));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new BasicNavToPagerAdapter();
    }
}















