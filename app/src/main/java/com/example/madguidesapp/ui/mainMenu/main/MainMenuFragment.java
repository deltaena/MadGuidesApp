package com.example.madguidesapp.ui.mainMenu.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.MainMenuAdapter;

public class MainMenuFragment extends RecyclerViewBaseFragment {
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getMainMenuElements().
                observe(this, mainMenuElements ->
                    setRecyclerViewElements(mainMenuElements));
    }

    @Override
    public void onStart() {
        super.onStart();

        drawerActivityViewModel.setRoutesFilter(false);
        drawerActivityViewModel.setResourcesFilter(false);
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new MainMenuAdapter();
    }
}










