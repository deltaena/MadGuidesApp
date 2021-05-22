package com.example.madguidesapp.android.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.madguidesapp.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.example.madguidesapp.pojos.MyUtil.toDp;

public class DecorativeImage extends ConstraintLayout {

    private static final String TAG = "DecorativeImage";

    private final int
            profile = 0,
            recyclerViewElement = 1,
            elementDetail = 2;

    private Map<Integer, Integer> placeHoldersMap = new HashMap<>();

    private TypedArray ta;
    private float imageDims;
    private ImageView image, decoration;
    private ProgressBar progressBar;

    private int decorationType;

    public DecorativeImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPlaceHolders();
        initAttributes(attrs);
        initImage();
        initDecoration();
        initProgressBar();
        initConstraints();
    }

    private void initPlaceHolders(){
        placeHoldersMap.put(-1, R.drawable.no_user_round_icon);
        placeHoldersMap.put(profile, R.drawable.no_user_round_icon);
        placeHoldersMap.put(recyclerViewElement, R.drawable.no_user_round_icon);
        placeHoldersMap.put(elementDetail, R.drawable.no_user_round_icon);
    }

    private void initImage(){
        if(ta.getBoolean(R.styleable.DecorativeImage_makeCircular, false)){
            image = new CircleImageView(getContext());
        }
        else{
            image = new ImageView(getContext());
        }

        image.setId(R.id.customDIImage);

        addView(image);

        image.getLayoutParams().width = imageDims == -1 ? MATCH_PARENT : toDp(getContext(), imageDims);
        image.getLayoutParams().height = imageDims == -1 ? MATCH_PARENT : toDp(getContext(), imageDims);
    }

    private void initDecoration(){
        decoration = new ImageView(getContext());

        decoration.setId(R.id.customDIDecoration);

        decoration.setTranslationZ(-1);

        decoration.setScaleType(ImageView.ScaleType.FIT_CENTER);
        decoration.setAdjustViewBounds(true);

        if(decorationType == 0){
            decoration.setImageDrawable(getContext().getDrawable(R.drawable.user_image_drawable_background));

            decoration.setTranslationX(toDp(getContext(), 11));
            decoration.setTranslationY(toDp(getContext(), -11));

            decoration.setRotation(135);
        }

        addView(decoration);

        decoration.getLayoutParams().width = imageDims == -1 ? MATCH_PARENT : toDp(getContext(), imageDims);
        decoration.getLayoutParams().height = imageDims == -1 ? MATCH_PARENT : toDp(getContext(), imageDims);
    }

    private void initProgressBar(){
        progressBar = new ProgressBar(getContext());
        progressBar.setId(R.id.customDIProgressBar);

        progressBar.setVisibility(INVISIBLE);

        addView(progressBar);
    }

    private void initAttributes(AttributeSet attrs){
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.DecorativeImage);

        imageDims = ta.getFloat(R.styleable.DecorativeImage_imageDims, -1);
        decorationType = ta.getInt(R.styleable.DecorativeImage_decorationType, -1);
    }

    private void initConstraints(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(R.id.customDIImage, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.customDIImage, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(R.id.customDIImage, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.customDIImage, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

        constraintSet.connect(R.id.customDIProgressBar, ConstraintSet.RIGHT, R.id.customDIImage, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.customDIProgressBar, ConstraintSet.LEFT, R.id.customDIImage, ConstraintSet.LEFT);
        constraintSet.connect(R.id.customDIProgressBar, ConstraintSet.BOTTOM, R.id.customDIImage, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.customDIProgressBar, ConstraintSet.TOP, R.id.customDIImage, ConstraintSet.TOP);

        constraintSet.connect(R.id.customDIDecoration, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.customDIDecoration, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(R.id.customDIDecoration, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.customDIDecoration, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

        constraintSet.applyTo(this);
    }

    public void loadImage(String url){
        progressBar.setVisibility(VISIBLE);

        Glide.with(getContext()).
                load(url).
                placeholder(getContext().getDrawable(placeHoldersMap.get(decorationType))).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(INVISIBLE);
                        return false;
                    }
                }).
                into(image);
    }
}



























