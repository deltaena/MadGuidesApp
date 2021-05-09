package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.os.Bundle;
import android.view.View;

import com.example.madguidesapp.R;

public class FavoritesAdapter extends BaseAdapter {
    @Override
    public View.OnClickListener getOnItemClickedListener(int position) {
        return click -> {
            Bundle bundle = new Bundle();
            bundle.putInt("selectedElementIndex", position);

            navController.navigate(R.id.nav_favorites_pager, bundle);
        };
    }
}
















