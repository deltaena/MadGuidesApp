package com.example.madguidesapp.android.viewPager;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.ui.mainMenu.resources.SingleResourceFragment;

public class ResourcesViewPager extends BaseViewPagerAdapter{

    private static final String TAG = "ResourcesViewPager";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getResourcesLiveData().
                observe(this, resources -> {
                    fillSliderAdapter(resources);
                });
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement) {
        return new SingleResourceFragment(recyclerViewElement);
    }
}
