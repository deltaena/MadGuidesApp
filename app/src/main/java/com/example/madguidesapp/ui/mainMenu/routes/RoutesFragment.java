package com.example.madguidesapp.ui.mainMenu.routes;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.RecyclerViewBaseFragment;
import com.example.madguidesapp.android.recyclerView.adapter.BaseAdapter;
import com.example.madguidesapp.android.recyclerView.adapter.BasicNavToPagerAdapter;

public class RoutesFragment extends RecyclerViewBaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel.getRoutesLiveData().
                observe(this, routes -> setRecyclerViewElements(routes));
    }

    @Override
    public BaseAdapter getBaseAdapter() {
        return new BasicNavToPagerAdapter(R.id.nav_routes_pager);
    }
}
