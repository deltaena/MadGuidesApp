package com.example.madguidesapp.ui.mainMenu.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewBaseFragment;
import com.example.madguidesapp.recyclerViewClasses.adapter.BaseAdapter;
import com.example.madguidesapp.recyclerViewClasses.adapter.MainMenuAdapter;

public class MainMenuFragment extends RecyclerViewBaseFragment {

    private MainMenuViewModel mainMenuViewModel;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainMenuViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        mainMenuViewModel.getMainMenuElements().
                observe(this, mainMenuElements -> {
                    setRecyclerViewElements(mainMenuElements);
                });
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new MainMenuAdapter();
    }
}










