package com.example.madguidesapp.recyclerViewClasses.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Guide;
import com.example.madguidesapp.abstractsAndInterfaces.BaseViewHolder;
import com.example.madguidesapp.recyclerViewClasses.adapter.viewHolder.GuidesViewHolder;
import com.example.madguidesapp.ui.dialogs.ContactWithGuideDialog;

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


















