package com.example.madguidesapp.android.recyclerView.adapter;

import android.os.Bundle;
import android.view.View;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.RestaurantCategory;

public class RestaurantCategoriesAdapter extends BaseAdapter{

    private static final String TAG = "RestaurantCategoriesAda";
    
    @Override
    protected View.OnClickListener getOnItemClickedListener(int position) {
        RestaurantCategory restaurantCategory = (RestaurantCategory) recyclerViewElements.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("title", restaurantCategory.getName());
        
        bundle.putString("selectedElementCategory", restaurantCategory.getCategory());

        return click -> navController.navigate(R.id.nav_restaurants, bundle);
    }
}
