package com.example.madguidesapp.abstractsAndInterfaces;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public TextView elementNameTextView;
    public ImageView elementImageView;
    public Button elementBtn;
    public ProgressBar progressBar;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);

        elementNameTextView = itemView.findViewById(R.id.elementNameTextView);
        elementImageView = itemView.findViewById(R.id.elementImageView);
        elementBtn = itemView.findViewById(R.id.elementBtn);
        progressBar = itemView.findViewById(R.id.progressBar);
    }


}
