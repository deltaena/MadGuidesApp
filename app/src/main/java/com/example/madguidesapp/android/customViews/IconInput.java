package com.example.madguidesapp.android.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.example.madguidesapp.R;

import java.util.HashMap;

import static android.text.InputType.TYPE_NULL;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.example.madguidesapp.pojos.MyUtil.toDp;

public class IconInput extends ConstraintLayout {

    private static final String TAG = "IconInput";

    private HashMap<Integer, Integer> iconsMap = new HashMap<>();

    private EditText editText;
    private TypedArray ta;

    private int inputType;

    public IconInput(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initMap();
        initAttributes(attrs);
        initEditText();
        initIcon();
        initConstraints();
    }

    public void initMap(){
        iconsMap.put(33, R.drawable.email_icon);
        iconsMap.put(129, R.drawable.eye_icon_orange);
        iconsMap.put(97, R.drawable.no_user_orange_icon);
    }

    public void initAttributes(AttributeSet attributeSet){
        ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.IconInput);
    }

    public void initEditText(){
        editText = new EditText(getContext());

        editText.setId(R.id.customIIEditText);

        editText.setGravity(Gravity.CENTER);

        inputType = ta.getInteger(R.styleable.IconInput_android_inputType, TYPE_NULL);

        editText.setInputType(inputType);

        editText.setHint(ta.getString(R.styleable.IconInput_hint));
        //editText.setBackground((ContextCompat.getDrawable(getContext(), iconsMap.get(R.drawable.input_bg))));

        addView(editText);

        editText.getLayoutParams().height = MATCH_PARENT;
        editText.getLayoutParams().width = MATCH_PARENT;
    }

    public void initIcon(){
        ImageView imageView = new ImageView(getContext());

        imageView.setId(R.id.customIIImageView);

        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), iconsMap.get(inputType)));

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);

        addView(imageView);

        imageView.getLayoutParams().width = toDp(getContext(), 20);
        imageView.getLayoutParams().height = 0;
    }

    public void initConstraints(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(R.id.customIIEditText, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(R.id.customIIEditText, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(R.id.customIIEditText, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(R.id.customIIEditText, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

        constraintSet.connect(R.id.customIIImageView, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, toDp(getContext(), 10));

        constraintSet.connect(R.id.customIIImageView, ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, toDp(getContext(), 10));

        constraintSet.connect(R.id.customIIImageView, ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP, toDp(getContext(), 10));

        constraintSet.applyTo(this);
    }

    public String getText(){
        String text = editText.getText().toString().trim();
        return (inputType == TYPE_TEXT_VARIATION_EMAIL_ADDRESS) ? text.toLowerCase() : text;
    }

    public void setText(String str){
        editText.setText(str);
    }

    public void setError(String str){
        editText.setError(str);
    }
}














