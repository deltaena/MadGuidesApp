package com.example.madguidesapp.android.recyclerView.adapter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.recyclerView.viewHolder.BaseViewHolder;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.pojos.SocialNetwork;

public class SocialNetworksAdapter extends BaseAdapter {
    private static final String TAG = "SocialNetworksAdapter";
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position){
        RecyclerViewElement recyclerViewElement = recyclerViewElements.get(position);

        holder.elementNameTextView.setText(recyclerViewElement.getName());

        switch (recyclerViewElement.getName()) {
            case "instagram":
                holder.elementCircleImageView.loadImage(ContextCompat.getDrawable(context, R.drawable.instagram_icon));
                break;
            case "linkedin":
                holder.elementCircleImageView.loadImage(ContextCompat.getDrawable(context, R.drawable.linkedin_icon));
                break;

                case "twitter":
                    holder.elementCircleImageView.loadImage(ContextCompat.getDrawable(context, R.drawable.ic_twitter));
                    break;

                default: holder.elementCircleImageView.loadImage(ContextCompat.getDrawable(context, R.drawable.link_icon));
        }

        holder.elementBtn.setOnClickListener(getOnItemClickedListener(position));
    }

    @Override
    public View.OnClickListener getOnItemClickedListener(int position) {
        SocialNetwork socialNetwork = (SocialNetwork) recyclerViewElements.get(position);

        return click -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialNetwork.getUrl()));

            context.startActivity(intent);
        };
    }

    @Override
    int getLayout() {
        return R.layout.social_network_layout;
    }
}
