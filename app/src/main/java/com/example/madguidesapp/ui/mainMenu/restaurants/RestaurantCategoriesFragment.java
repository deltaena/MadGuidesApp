package com.example.madguidesapp.ui.mainMenu.restaurants;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.RestaurantCategoriesAdapter;

public class RestaurantCategoriesFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getRestaurantsCategoryLiveData().
                observe(this, restaurantCategories -> setRecyclerViewElements(restaurantCategories));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new RestaurantCategoriesAdapter();
    }
}
