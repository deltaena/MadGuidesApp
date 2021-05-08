package com.example.madguidesapp.viewPagerClasses;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.ui.mainMenu.routes.SingleRouteFragment;

public class RoutesViewPager extends BaseViewPagerAdapter {

    private DrawerActivityViewModel drawerActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
        drawerActivityViewModel.getRoutesLiveData().
                observe(this, routes -> fillSliderAdapter(routes));
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement) {
        return new SingleRouteFragment(recyclerViewElement);
    }
}