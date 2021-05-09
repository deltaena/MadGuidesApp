package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.view.View;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.MainMenuElement;
import com.example.madguidesapp.abstractsAndInterfaces.RecyclerViewElement;

public class MainMenuAdapter extends BaseAdapter {

    @Override
    View.OnClickListener getOnItemClickedListener(int position) {
        MainMenuElement mainMenuElement = (MainMenuElement) recyclerViewElements.get(position);

        return click -> {
            navController.navigate(findNextFragmentId(mainMenuElement.getType()));
        };
    }

    int findNextFragmentId(RecyclerViewElement.Type type){
        switch(type){
            case RESOURCE:
                return R.id.nav_resources;
            case ROUTE:
                return R.id.nav_routes;
            case GUIDE:
                return R.id.nav_guides;
            case HOTEL_CATEGORY:
                return R.id.nav_hotel_categories;
            case HOTEL:
                return R.id.nav_hotels;
            default:
                return 0;
        }
    }
}














