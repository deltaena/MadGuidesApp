package com.example.madguidesapp.android.recyclerView.adapter;

import android.view.View;

import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.ReferenceElement;
import com.example.madguidesapp.pojos.Resource;
import com.example.madguidesapp.pojos.Route;
import com.google.android.material.snackbar.Snackbar;

public class ReferenceAdapter extends BaseAdapter{

    private View root;

    public ReferenceAdapter(View root){
        this.root = root;
    }

    @Override
    protected View.OnClickListener getOnItemClickedListener(int position) {
        return click -> {
            ReferenceElement element = (ReferenceElement) recyclerViewElements.get(position);

            if (!element.isAvailable()) {
                Snackbar.make(root, "Estamos trabajando para poner a su disposición todos los recursos lo antes posible", Snackbar.LENGTH_LONG).show();
                return;
            }
        };
    }
}


















