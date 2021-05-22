package com.example.madguidesapp.android.recyclerView.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.android.recyclerView.viewHolder.BaseViewHolder;
import com.example.madguidesapp.android.recyclerView.viewHolder.BasicViewHolder;

import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    List<? extends RecyclerViewElement> recyclerViewElements;
    NavController navController;
    RequestManager glideRequestManager;
    Context context;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);

        context = parent.getContext();
        glideRequestManager = Glide.with(view);

        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position){
        RecyclerViewElement recyclerViewElement = recyclerViewElements.get(position);

        holder.elementNameTextView.setText(recyclerViewElement.getName());
        holder.elementCircleImageView.loadImage(recyclerViewElement.getImageUrl());
        holder.elementBtn.setOnClickListener(getOnItemClickedListener(position));
    }

    @Override
    public int getItemCount() {
        return recyclerViewElements.size();
    }

    protected abstract View.OnClickListener getOnItemClickedListener(int position);

    BaseViewHolder getViewHolder(View view){
        return new BasicViewHolder(view);
    }

    int getLayout(){
        return R.layout.recycler_view_element_layout;
    }

    public void setRecyclerViewElements(List<? extends RecyclerViewElement> recyclerViewElements) {
        this.recyclerViewElements = recyclerViewElements;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }
}
