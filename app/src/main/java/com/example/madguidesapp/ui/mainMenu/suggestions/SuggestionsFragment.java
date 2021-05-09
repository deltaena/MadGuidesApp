package com.example.madguidesapp.ui.mainMenu.suggestions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.android.viewModel.DrawerActivityViewModel;
import com.example.madguidesapp.pojos.Suggestion;
import com.example.madguidesapp.pojos.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class SuggestionsFragment extends Fragment {

    private static final String TAG = "SuggestionsFragment";
    
    private DrawerActivityViewModel drawerActivityViewModel;
    private EditText suggestionEditText;

    private Uri suggestionFileUri;

    private Suggestion suggestion = new Suggestion();

    OnCompleteListener<UploadTask.TaskSnapshot> onImageUploaded = task -> {
        task.getResult().getMetadata().getReference().getDownloadUrl().
                addOnCompleteListener(task1 -> {
                    suggestion.setAttachmentUrl(task1.getResult().toString());

                    setFieldsAndUploadSuggestion();
                });
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerActivityViewModel = new ViewModelProvider(requireActivity()).get(DrawerActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);

        suggestionEditText = view.findViewById(R.id.suggestionEditText);

        ImageView attachmentImageView = view.findViewById(R.id.suggestionImageView);

        CheckBox checkBox = view.findViewById(R.id.includeImageCheckBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                attachmentImageView.setVisibility(View.VISIBLE);
            }
            else{
                attachmentImageView.setVisibility(View.GONE);
            }
        });

        attachmentImageView.setOnClickListener(v -> {
            ImagePicker.Companion.with(this).start();
        });

        Button sendSuggestionButton = view.findViewById(R.id.sendSuggestionbutton);
        sendSuggestionButton.setOnClickListener(v -> {

            if(checkBox.isChecked()){
                drawerActivityViewModel.
                        uploadSuggestionImage(suggestionFileUri).
                        addOnCompleteListener(onImageUploaded);
            }
            else{
                setFieldsAndUploadSuggestion();
            }
        });

        return view;
    }

    public void setFieldsAndUploadSuggestion(){
        String suggestionText = suggestionEditText.getText().toString().trim();

        if(suggestionText.isEmpty()){
            suggestionEditText.setError("Campo obligatorio");
        }

        suggestion.setSuggestionText(suggestionText);
        suggestion.setCreationDate(new Date());

        if(drawerActivityViewModel.areUserRegistered()) {
            suggestion.setSenderEmail(drawerActivityViewModel.getUserLiveData().getValue().getEmail());
        }
        else{
            suggestion.setSenderEmail("");
        }

        drawerActivityViewModel.uploadSuggestion(suggestion);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            suggestionFileUri = Uri.parse(data.getDataString());
        }
    }
}












