package com.example.madguidesapp.ui.mainMenu.resources;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.RestaurantCategoriesAdapter;

public class ResourcesFragment extends RecyclerViewBaseFragment {
    private static final String TAG = "ResourcesFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getResourcesLiveData().
                observe(this, resources -> setRecyclerViewElements(resources));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new BasicNavToPagerAdapter(R.id.nav_resources_pager);
    }
}
