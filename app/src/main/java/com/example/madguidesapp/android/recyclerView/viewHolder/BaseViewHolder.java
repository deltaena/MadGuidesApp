package com.example.madguidesapp.android.recyclerView.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.customViews.DecorativeImage;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public TextView elementNameTextView;
    public DecorativeImage elementCircleImageView;
    public Button elementBtn;
    public ConstraintLayout elementBackgroundLayout;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);

        elementNameTextView = itemView.findViewById(R.id.elementNameTextView);
        elementCircleImageView = itemView.findViewById(R.id.elementCircleImageView);
        elementBtn = itemView.findViewById(R.id.elementBtn);
        elementBackgroundLayout = itemView.findViewById(R.id.elementBackgroundLayout);
    }


}
