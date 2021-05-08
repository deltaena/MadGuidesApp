package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.os.Bundle;
import android.view.View;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.pojos.Hotel;
import com.example.madguidesapp.pojos.HotelCategory;

public class HotelCategoriesAdapter extends BaseAdapter{

    @Override
    View.OnClickListener getOnItemClickedListener(int position) {
        HotelCategory hotel = (HotelCategory) recyclerViewElements.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("selectedElementCategory", hotel.getCategory());

        return click -> navController.navigate(R.id.nav_hotels, bundle);
    }

}

















