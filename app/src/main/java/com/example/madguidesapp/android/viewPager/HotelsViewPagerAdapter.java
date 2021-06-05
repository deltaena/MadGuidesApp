package com.example.madguidesapp.android.viewPager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.ui.mainMenu.hotels.SingleHotelFragment;

public class HotelsViewPagerAdapter extends BaseViewPagerAdapter{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getHotelsLiveData().
                observe(this, hotels ->
                    fillSliderAdapter(hotels));
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement, int index) {
        return new SingleHotelFragment(recyclerViewElement);
    }
}
