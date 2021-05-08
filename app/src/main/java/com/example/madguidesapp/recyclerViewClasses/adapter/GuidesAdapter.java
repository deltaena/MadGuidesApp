package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;
import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.recyclerViewClasses.adapter.viewHolder.BaseViewHolder;
import com.example.madguidesapp.recyclerViewClasses.adapter.viewHolder.GuidesViewHolder;
import com.example.madguidesapp.ui.dialogs.ContactWithGuideDialog;

import java.util.List;

public class GuidesAdapter extends BaseAdapter {

    @Override
    View.OnClickListener getOnItemClickedListener(int position) {
        return null;
    }

    @Override
    BaseViewHolder getViewHolder(View view) {
        return new GuidesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Guide guide = (Guide) recyclerViewElements.get(position);

        GuidesViewHolder guidesViewHolder = (GuidesViewHolder) holder;

        guidesViewHolder.guideDescriptionTextView.setText(guide.getDescription());

        guidesViewHolder.contactsBtn.setOnClickListener(click -> {
            ContactWithGuideDialog contactWithGuideDialog = new ContactWithGuideDialog(context, guide);
            contactWithGuideDialog.show();
        });
    }

    @Override
    int getLayout() {
        return R.layout.recycler_view_guide_layout;
    }
}


















