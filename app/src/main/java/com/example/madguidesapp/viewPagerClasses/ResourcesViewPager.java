package com.example.madguidesapp.viewPagerClasses;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewElement;
import com.example.madguidesapp.ui.mainMenu.resources.SingleResourceFragment;

public class ResourcesViewPager extends BaseViewPagerAdapter{

    private DrawerActivityViewModel drawerActivityViewModel;

    @Override
    public void onStart() {
        super.onStart();

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
        drawerActivityViewModel.getResourcesLiveData().
                observe(this, resources -> fillSliderAdapter(resources));
    }

    @Override
    Fragment getDetailFragment(RecyclerViewElement recyclerViewElement) {
        return new SingleResourceFragment(recyclerViewElement);
    }
}
