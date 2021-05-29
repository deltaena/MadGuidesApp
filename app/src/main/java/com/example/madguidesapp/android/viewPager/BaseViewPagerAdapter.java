package com.example.madguidesapp.android.viewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.connectivity.ConnectivityFragment;
import com.example.madguidesapp.android.customViews.IconButton;
import com.example.madguidesapp.android.recyclerView.adapter.CommentsAdapter;
import com.example.madguidesapp.pojos.RecyclerViewElement;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.pojos.User;
import com.example.madguidesapp.ui.dialogs.AccountRequiredDialog;
import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.w3c.dom.Text;

import java.util.List;

public abstract class BaseViewPagerAdapter extends ConnectivityFragment {

    private static final String TAG = "BaseViewPagerAdapter";

    DrawerActivityViewModel drawerActivityViewModel;

    private SlidingUpPanelLayout slidingUpPanelLayout;
    private ImageButton nextElementImgBtn;
    private ImageButton previousElementImgBtn;

    private IconButton sendImageView;
    private EditText commentEditText;

    private RecyclerView commentsRecyclerView;

    private ProgressBar progressBar;

    private IconButton toggleVisitedIcon, showOnMapsIcon;

    private IconButton markAsFavorite;

    public NavController navController;
    private ViewPager2 viewPager2;
    private SliderAdapter sliderAdapter;

    private List<? extends RecyclerViewElement> recyclerViewElements;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_layout, container, false);

        slidingUpPanelLayout = view.findViewById(R.id.slidingUpPanel);

        viewPager2 = view.findViewById(R.id.viewPager2);
        nextElementImgBtn = view.findViewById(R.id.nextElementImgBtn);
        previousElementImgBtn = view.findViewById(R.id.previousElementImgBtn);

        previousElementImgBtn.setOnClickListener(click -> changeCurrentItem(-1));
        nextElementImgBtn.setOnClickListener(click -> changeCurrentItem(1));

        sendImageView = view.findViewById(R.id.sendIcon);
        commentEditText = view.findViewById(R.id.commentEditText);
        commentsRecyclerView = view.findViewById(R.id.commentsRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        toggleVisitedIcon = view.findViewById(R.id.toggleResourceVisitedImgBtn);
        showOnMapsIcon = view.findViewById(R.id.showOnMapsImgBtn);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                setToolBar();

                progressBar.setVisibility(View.VISIBLE);
                commentsRecyclerView.setVisibility(View.INVISIBLE);

                drawerActivityViewModel.getComments(recyclerViewElements.get(viewPager2.getCurrentItem())).
                        observe(getViewLifecycleOwner(), comments -> {
                            sendImageView.enable();
                            if(comments != null){
                                CommentsAdapter commentsAdapter = new CommentsAdapter(comments);

                                commentsRecyclerView.setHasFixedSize(true);
                                commentsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                                commentsRecyclerView.setAdapter(commentsAdapter);

                                progressBar.setVisibility(View.INVISIBLE);
                                commentsRecyclerView.setVisibility(View.VISIBLE);
                            }
                        });

                drawerActivityViewModel.getUserLiveData().
                        observe(getViewLifecycleOwner(), user -> {
                            toggleVisitedIcon.enable();
                            markAsFavorite.enable();
                            if(user == null) return;

                            RecyclerViewElement element = recyclerViewElements.get(viewPager2.getCurrentItem());

                            if(drawerActivityViewModel.isVisited(element)){
                                toggleVisitedIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unvisit_resource_icon));
                            }
                            else {
                                toggleVisitedIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.eye_icon));
                            }

                            if(drawerActivityViewModel.isFavorite(element)){
                                markAsFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.filled_heart));
                            }
                            else{
                                markAsFavorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.empty_heart));
                            }
                        });

                toggleVisitedIcon.addListener(v -> {
                    if(!drawerActivityViewModel.areUserRegistered()){
                        requireAccount();
                        return;
                    }

                    drawerActivityViewModel.toggleVisited(recyclerViewElements.get(viewPager2.getCurrentItem()));
                });

                showOnMapsIcon.setOnClickListener(click -> {
                    Intent showOnMapsIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(recyclerViewElements.get(viewPager2.getCurrentItem()).getMapsUrl()));

                    startActivity(showOnMapsIntent);
                    showOnMapsIcon.enable();
                });

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

        sendImageView.setOnClickListener(click -> {
            if(!drawerActivityViewModel.areUserRegistered()){
                requireAccount();
                return;
            }

            String commentText = commentEditText.getText().toString().trim();

            commentEditText.setText("");
            if(commentText.isEmpty()) return;

                drawerActivityViewModel.postComment(commentText,
                    recyclerViewElements.get(viewPager2.getCurrentItem())).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Snackbar.make(requireView(), "Comentario añadido!", Snackbar.LENGTH_LONG).show();
                        }
                    });
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

        this.recyclerViewElements = recyclerViewElements;

        int selectedResourceIndex = getArguments().getInt("selectedElementIndex", 0);

        sliderAdapter = new BaseViewPagerAdapter.SliderAdapter(this, recyclerViewElements);

        viewPager2.setAdapter(sliderAdapter);

        viewPager2.post(() -> viewPager2.setCurrentItem(selectedResourceIndex));
    }

    public void changeCurrentItem(int direction){ viewPager2.setCurrentItem(viewPager2.getCurrentItem()+direction); }

    public void setToolBar(){
        Toolbar toolbar = ((AppCompatActivity)requireActivity()).findViewById(R.id.toolbar);

        TextView toolbarTitleTextView = toolbar.findViewById(R.id.toolbarTitleTextView);

        markAsFavorite = toolbar.findViewById(R.id.toolbarRightButton);
        markAsFavorite.setVisibility(View.VISIBLE);

        RecyclerViewElement element = recyclerViewElements.get(viewPager2.getCurrentItem());

        toolbarTitleTextView.setText(element.getName());

        markAsFavorite.setOnClickListener(click -> {
            if(!drawerActivityViewModel.areUserRegistered()){
                requireAccount();
                return;
            }

            drawerActivityViewModel.toggleFavorite(element);
        });
    }

    public void requireAccount(){
        AccountRequiredDialog accountRequiredDialog = new AccountRequiredDialog();
        accountRequiredDialog.show(getParentFragmentManager(), "Account Required");
    }

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
