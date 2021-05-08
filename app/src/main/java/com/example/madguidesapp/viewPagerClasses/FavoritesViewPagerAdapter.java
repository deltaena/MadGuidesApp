package com.example.madguidesapp.viewPagerClasses;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.ui.mainMenu.resources.SingleResourceFragment;
import com.example.madguidesapp.ui.mainMenu.routes.SingleRouteFragment;

import java.util.List;

public class FavoritesViewPagerAdapter extends BaseViewPagerAdapter{
    DrawerActivityViewModel drawerActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(this).
                get(DrawerActivityViewModel.class);

        drawerActivityViewModel.getFavoritesLiveData().
                observe(requireActivity(), recyclerViewElements -> {
                    fillSliderAdapter(recyclerViewElements);
                });
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement) {
        if (recyclerViewElement instanceof Resource) {
            return new SingleResourceFragment(recyclerViewElement);
        } else {
            return new SingleRouteFragment(recyclerViewElement);
        }
    }
}
