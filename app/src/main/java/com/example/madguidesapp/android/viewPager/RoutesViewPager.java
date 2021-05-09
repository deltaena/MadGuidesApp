package com.example.madguidesapp.android.viewPager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.ui.mainMenu.routes.SingleRouteFragment;

public class RoutesViewPager extends BaseViewPagerAdapter {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getRoutesLiveData().
                observe(this, routes -> fillSliderAdapter(routes));
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement) {
        return new SingleRouteFragment(recyclerViewElement);
    }
}