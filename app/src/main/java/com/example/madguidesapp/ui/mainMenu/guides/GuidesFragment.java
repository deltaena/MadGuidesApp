package com.example.madguidesapp.ui.mainMenu.guides;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.GuidesAdapter;

public class GuidesFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getGuidesLiveData().
                observe(this, this::setRecyclerViewElements);
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new GuidesAdapter();
    }
}
