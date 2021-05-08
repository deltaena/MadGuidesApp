package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.madguidesapp.pojos.SocialNetwork;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;

import java.util.List;

public class SocialNetworksAdapter extends BaseAdapter {

    @Override
    View.OnClickListener getOnItemClickedListener(int position) {
        SocialNetwork socialNetwork = (SocialNetwork) recyclerViewElements.get(position);

        return click -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialNetwork.getUrl()));
            context.startActivity(intent);
        };
    }
}
