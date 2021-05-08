package com.example.madguidesapp.ui.mainMenu.resources;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.ResourcesAdapter;

public class ResourcesFragment extends RecyclerViewBaseFragment {
    private static final String TAG = "ResourcesFragment";
    private DrawerActivityViewModel drawerActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getResourcesLiveData().
                observe(this, resources -> setRecyclerViewElements(resources));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new ResourcesAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        drawerActivityViewModel.clearFilters();
    }
}
