package com.example.madguidesapp.ui.mainMenu.restaurants;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;

public class RestaurantsFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String categoryFilter = getArguments().getString("selectedElementCategory");

        drawerActivityViewModel.filterRestaurants(categoryFilter);
        drawerActivityViewModel.getRestaurantsLiveData().
                observe(this, restaurants -> setRecyclerViewElements(restaurants));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new BasicNavToPagerAdapter(R.id.nav_restaurants_pager);
    }
}
