package com.example.madguidesapp.ui.mainMenu.hotels;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;

public class HotelsFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int categoryFilter = getArguments().getInt("selectedElementCategory");

        drawerActivityViewModel.filterHotels(categoryFilter);
        drawerActivityViewModel.getHotelsLiveData().
                observe(this, this::setRecyclerViewElements);
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new BasicNavToPagerAdapter(R.id.nav_hotels_pager);
    }
}















