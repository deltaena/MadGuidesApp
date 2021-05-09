package com.example.madguidesapp.android.recyclerView.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.madguidesapp.pojos.SocialNetwork;

public class SocialNetworksAdapter extends BaseAdapter {

    @Override
    public View.OnClickListener getOnItemClickedListener(int position) {
        SocialNetwork socialNetwork = (SocialNetwork) recyclerViewElements.get(position);

        return click -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialNetwork.getUrl()));
            context.startActivity(intent);
        };
    }
}
