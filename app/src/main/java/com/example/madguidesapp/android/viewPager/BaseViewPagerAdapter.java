package com.example.madguidesapp.android.viewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.connectivity.ConnectivityFragment;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;

import java.util.List;

public abstract class BaseViewPagerAdapter extends ConnectivityFragment {

    private static final String TAG = "BaseViewPagerAdapter";

    DrawerActivityViewModel drawerActivityViewModel;

    private ImageButton nextElementImgBtn;
    private ImageButton previousElementImgBtn;

    public NavController navController;
    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_layout, container, false);

        viewPager2 = view.findViewById(R.id.viewPager2);
        nextElementImgBtn = view.findViewById(R.id.nextElementImgBtn);
        previousElementImgBtn = view.findViewById(R.id.previousElementImgBtn);

        previousElementImgBtn.setOnClickListener(click -> changeCurrentItem(-1));
        nextElementImgBtn.setOnClickListener(click -> changeCurrentItem(1));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(position < sliderAdapter.getItemCount()-1){
                    nextElementImgBtn.setVisibility(View.VISIBLE);
                }
                else{
                    nextElementImgBtn.setVisibility(View.INVISIBLE);
                }

                if(position > 0){
                    previousElementImgBtn.setVisibility(View.VISIBLE);
                }
                else{
                    previousElementImgBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getView());
    }

    public void fillSliderAdapter(List<? extends RecyclerViewElement> recyclerViewElements){
        if(recyclerViewElements.isEmpty()){
            navController.popBackStack();
        }

        int selectedResourceIndex = getArguments().getInt("selectedElementIndex", 0);

        sliderAdapter = new BaseViewPagerAdapter.SliderAdapter(this, recyclerViewElements);

        viewPager2.setAdapter(sliderAdapter);

        viewPager2.post(() -> viewPager2.setCurrentItem(selectedResourceIndex));
    }

    public void changeCurrentItem(int direction){ viewPager2.setCurrentItem(viewPager2.getCurrentItem()+direction); }

    class SliderAdapter extends FragmentStateAdapter {

        List<? extends RecyclerViewElement> recyclerViewElements;

        public SliderAdapter(
                @NonNull Fragment fragment,
                List<? extends RecyclerViewElement> recyclerViewElements) {

            super(fragment);
            this.recyclerViewElements = recyclerViewElements;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return getDetailFragment(recyclerViewElements.get(position));
        }

        @Override
        public int getItemCount() {
            return recyclerViewElements.size();
        }
    }

    abstract Fragment getDetailFragment(RecyclerViewElement recyclerViewElement);
}
