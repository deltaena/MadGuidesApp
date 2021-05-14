package com.example.madguidesapp.android.viewPager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.ui.mainMenu.hotels.SingleHotelFragment;
import com.example.madguidesapp.ui.mainMenu.resources.SingleResourceFragment;
import com.example.madguidesapp.ui.mainMenu.routes.SingleRouteFragment;

public class FavoritesViewPagerAdapter extends BaseViewPagerAdapter{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getFavoritesLiveData().
                observe(this, recyclerViewElements -> fillSliderAdapter(recyclerViewElements));
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement) {
        if (recyclerViewElement instanceof Resource) {
            return new SingleResourceFragment(recyclerViewElement);
        } else if(recyclerViewElement instanceof Route){
            return new SingleRouteFragment(recyclerViewElement);
        } else if(recyclerViewElement instanceof Hotel){
            return new SingleHotelFragment(recyclerViewElement);
        }

        return null;
    }
}
