package com.example.madguidesapp.viewPagerClasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.madguidesapp.R;
import com.example.madguidesapp.recyclerViewClasses.RecyclerViewElement;

import java.util.List;

public abstract class BaseViewPagerAdapter extends Fragment {

    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_layout, container, false);

        viewPager2 = view.findViewById(R.id.viewPager2);

        return view;
    }

    public void fillSliderAdapter(List<? extends RecyclerViewElement> recyclerViewElements){
        if(recyclerViewElements == null){
            return;
        }

        int selectedResourceIndex = getArguments().getInt("selectedElementIndex", 0);

        sliderAdapter = new BaseViewPagerAdapter.SliderAdapter(this, recyclerViewElements);
        viewPager2.setAdapter(sliderAdapter);

        viewPager2.post(() -> viewPager2.setCurrentItem(selectedResourceIndex));
    }

    class SliderAdapter extends FragmentStateAdapter {

        List<? extends RecyclerViewElement> recyclerViewElements;

        public SliderAdapter(@NonNull Fragment fragment, List<? extends RecyclerViewElement> recyclerViewElements) {

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
