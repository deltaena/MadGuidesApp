package com.example.madguidesapp.pojos;

import android.content.Context;

public class MyUtil {
    public static int toDp(Context context, float dim){
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dim * scale + 0.5f);

        return pixels;
    }
}
