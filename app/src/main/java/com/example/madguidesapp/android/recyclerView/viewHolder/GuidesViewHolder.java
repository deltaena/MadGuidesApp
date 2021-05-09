package com.example.madguidesapp.android.recyclerView.viewHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.madguidesapp.R;

public class GuidesViewHolder extends BaseViewHolder {
    public ImageButton contactsBtn;
    public TextView guideDescriptionTextView;

    public GuidesViewHolder(@NonNull View itemView) {
        super(itemView);

        contactsBtn = itemView.findViewById(R.id.contactsBtn);
        guideDescriptionTextView = itemView.findViewById(R.id.guideDescriptionTextView);
    }


}
