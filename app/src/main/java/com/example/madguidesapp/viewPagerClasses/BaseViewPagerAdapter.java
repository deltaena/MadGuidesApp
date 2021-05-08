package com.example.madguidesapp.viewPagerClasses;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.madguidesapp.DraweActivity;
import com.example.madguidesapp.R;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;

import java.util.List;

public abstract class BaseViewPagerAdapter extends DraweActivity {

    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager);

        viewPager2 = findViewById(R.id.viewPager2);
    }

    public void fillSliderAdapter(List<? extends RecyclerViewElement> recyclerViewElements){
        if(recyclerViewElements == null){
            return;
        }

        int selectedResourceIndex = getIntent().getIntExtra("selectedElementIndex", 0);

        sliderAdapter = new BaseViewPagerAdapter.SliderAdapter(this, recyclerViewElements);
        viewPager2.setAdapter(sliderAdapter);

        viewPager2.post(() -> viewPager2.setCurrentItem(selectedResourceIndex));
    }

    class SliderAdapter extends FragmentStateAdapter {

        List<? extends RecyclerViewElement> recyclerViewElements;

        public SliderAdapter(@NonNull FragmentActivity fragment,
                List<? extends RecyclerViewElement> recyclerViewElements) {

            super(fragment);
            this.recyclerViewElements = recyclerViewElements;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            RecyclerViewElement recyclerViewElement = recyclerViewElements.get(position);

            return getDetailFragment(recyclerViewElement);
        }

        @Override
        public int getItemCount() {
            return recyclerViewElements.size();
        }
    }

    abstract Fragment getDetailFragment(RecyclerViewElement recyclerViewElement);
}
