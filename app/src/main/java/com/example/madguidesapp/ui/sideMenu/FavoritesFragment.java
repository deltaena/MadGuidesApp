package com.example.madguidesapp.ui.sideMenu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.FavoritesAdapter;

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
        return new FavoritesAdapter();
    }
}
