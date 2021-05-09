package com.example.madguidesapp.android.recyclerView.adapter;

import android.os.Bundle;
import android.view.View;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;

public class BasicNavToPagerAdapter extends BaseAdapter{
    @Override
    protected View.OnClickListener getOnItemClickedListener(int position) {
        return v ->{
            RecyclerViewElement element = recyclerViewElements.get(position);

            Bundle bundle = new Bundle();

            bundle.putInt("selectedElementIndex", position);

            if (element instanceof Resource) {
                navController.navigate(R.id.nav_resources_pager, bundle);
            } else if (element instanceof Route) {
                navController.navigate(R.id.nav_routes_pager, bundle);
            } else if (element instanceof Hotel) {
                navController.navigate(R.id.nav_hotels_pager, bundle);
            }
        };
    }
}
