package com.example.madguidesapp.ui.sideMenu;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;

public class FavoritesFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getFavoritesLiveData().
                observe(this, recyclerViewElements -> {
                    if(drawerActivityViewModel.areUserRegistered()) {
                        setRecyclerViewElements(recyclerViewElements);
                    }
                    else{
                        navController.popBackStack();
                    }
        });
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new BasicNavToPagerAdapter(R.id.nav_favorites_pager);
    }
}
