package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.os.Bundle;
import android.view.View;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;

public class FavoritesAdapter extends BaseAdapter{
    @Override
    View.OnClickListener getOnItemClickedListener(int position) {
        return click -> {
            Bundle bundle = new Bundle();
            bundle.putInt("selectedElementIndex", position);

            navController.navigate(R.id.nav_favorites_pager, bundle);
        };
    }
}
















