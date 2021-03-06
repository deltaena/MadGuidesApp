package com.example.madguidesapp.android.recyclerView.adapter;

import android.os.Bundle;
import android.view.View;

public class BasicNavToPagerAdapter extends BaseAdapter{
    public int navPagerId;

    public BasicNavToPagerAdapter(int navPagerId){
        this.navPagerId = navPagerId;
    }

    @Override
    protected View.OnClickListener getOnItemClickedListener(int position) {
        return v ->{
            Bundle bundle = new Bundle();

            bundle.putInt("selectedElementIndex", position);

            navController.navigate(navPagerId, bundle);
        };
    }
}
