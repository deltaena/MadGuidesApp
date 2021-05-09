package com.example.madguidesapp.android.recyclerView.adapter;

import android.os.Bundle;
import android.view.View;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.HotelCategory;

public class HotelCategoriesAdapter extends BaseAdapter {

    @Override
    public View.OnClickListener getOnItemClickedListener(int position) {
        HotelCategory hotelCategory = (HotelCategory) recyclerViewElements.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("title", hotelCategory.getName());
        bundle.putInt("selectedElementCategory", hotelCategory.getCategory());

        return click -> navController.navigate(R.id.nav_hotels, bundle);
    }

}

















