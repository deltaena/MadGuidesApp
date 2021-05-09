package com.example.madguidesapp.android.recyclerView.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        glideRequestManager.
                load(recyclerViewElement.getImageUrl()).
                placeholder(R.mipmap.guide_icon_unavailable_man_round).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.elementImageView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.elementImageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).
                into(holder.elementImageView);

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
