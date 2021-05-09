package com.example.madguidesapp.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.android.recyclerView.adapter.SocialNetworksAdapter;

import java.util.List;

public class ContactWithGuideDialog extends AlertDialog {

    private static final String TAG = "ContactWithGuideDialog";
    
    private Guide guide;

    public ContactWithGuideDialog(Context context, Guide guide){
        super(context);
        this.guide = guide;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_with_guide_layout, null);

        List<? extends RecyclerViewElement> socialNetworks = guide.getSocialNetworks();

        RecyclerView recyclerView = view.findViewById(R.id.socialNetworkRecyclerView);

        SocialNetworksAdapter socialNetworksAdapter = new SocialNetworksAdapter();
        socialNetworksAdapter.setRecyclerViewElements(socialNetworks);

        recyclerView.setAdapter(socialNetworksAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        setView(view);
        setTitle("title");

        super.onCreate(savedInstanceState);
    }

}













