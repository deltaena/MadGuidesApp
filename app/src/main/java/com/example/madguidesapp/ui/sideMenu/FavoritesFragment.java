package com.example.madguidesapp.ui.sideMenu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.DrawerActivityViewModel;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.FavoritesAdapter;

import java.util.List;

public class FavoritesFragment extends RecyclerViewBaseFragment {
    private DrawerActivityViewModel drawerActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerActivityViewModel = new ViewModelProvider(requireActivity()).
                get(DrawerActivityViewModel.class);

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
