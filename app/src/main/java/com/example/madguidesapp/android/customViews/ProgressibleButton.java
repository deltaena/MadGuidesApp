package com.example.madguidesapp.android.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.madguidesapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ProgressibleButton extends ConstraintLayout {

    private List<OnClickListener> onClickListeners = new ArrayList<>();

    private Button button;
    private ProgressBar progressBar;

    private TypedArray ta;

    private boolean isLoading = false;

    public ProgressibleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initTypedArray(attrs);
        initButton();
        initProgressBar();
        initConstraints();

        if(ta.getBoolean(R.styleable.ProgressibleButton_performsAsync, false)){
            onClickListeners.add(v -> {
                startLoading();
            });
        }
    }

    public void addOnClickListener(OnClickListener onClickListener){
        if(!ta.getBoolean(R.styleable.ProgressibleButton_performsAsync, false)){
            if(!onClickListeners.isEmpty()){
                onClickListeners.remove(0);
            }
        }
        else {
            if (onClickListeners.size() == 2) {
                onClickListeners.remove(onClickListeners.size() - 1);
            }
        }

        onClickListeners.add(onClickListener);
    }

    public void startLoading(){
        button.setClickable(false);
        button.setVisibility(INVISIBLE);
        progressBar.setVisibility(VISIBLE);

        isLoading = true;
    }

    public void endLoading(){
        progressBar.setVisibility(INVISIBLE);
        button.setVisibility(VISIBLE);
        button.setClickable(true);

        isLoading = false;
    }

    public boolean isLoading(){
        return isLoading;
    }

    private void initButton(){
        button = new Button(getContext());

        button.setId(R.id.customPBBButton);
        button.setText(ta.getString(0));

        button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.progress_button_bg));
        button.setTextColor(Color.WHITE);

        if(ta.getBoolean(R.styleable.ProgressibleButton_crusoeFont, false)){
            button.setTypeface(ResourcesCompat.getFont(getContext(), R.font.crusoe));
        }

        button.setClickable(true);
        button.setFocusable(true);

        button.setOnClickListener(click ->
                onClickListeners.forEach(onClickListener ->
                        onClickListener.onClick(click)));

        addView(button);

        button.getLayoutParams().height = MATCH_PARENT;
        button.getLayoutParams().width = MATCH_PARENT;
    }

    private void initProgressBar(){
        progressBar = new ProgressBar(getContext());

        progressBar.setId(R.id.customPBBProgressBar);
        progressBar.setVisibility(INVISIBLE);

        addView(progressBar);
    }

    private void initTypedArray(AttributeSet attrs){
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressibleButton);
    }

    private void initConstraints(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(R.id.customPBBButton, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.customPBBButton, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(R.id.customPBBButton, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.customPBBButton, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

        constraintSet.connect(R.id.customPBBProgressBar, ConstraintSet.RIGHT, R.id.customPBBButton, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.customPBBProgressBar, ConstraintSet.LEFT, R.id.customPBBButton, ConstraintSet.LEFT);
        constraintSet.connect(R.id.customPBBProgressBar, ConstraintSet.BOTTOM, R.id.customPBBButton, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.customPBBProgressBar, ConstraintSet.TOP, R.id.customPBBButton, ConstraintSet.TOP);

        constraintSet.applyTo(this);
    }

    public void setText(String text){
        button.setText(text);
    }
}
















