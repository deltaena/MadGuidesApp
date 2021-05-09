package com.example.madguidesapp.ui.mainMenu.routes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.RoutesAdapter;

public class RoutesFragment extends RecyclerViewBaseFragment {

    private DrawerActivityViewModel drawerActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
        drawerActivityViewModel.getRoutesLiveData().
                observe(this, routes -> setRecyclerViewElements(routes));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new RoutesAdapter();
    }
}
