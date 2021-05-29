package com.example.madguidesapp.android.recyclerView.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madguidesapp.R;
import com.example.madguidesapp.pojos.Comment;
import com.example.madguidesapp.pojos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments){
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.commentTextView.setText(comment.getText());
        holder.dateTextView.setText(comment.getDateFormatted());

        comment.getUser().get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                holder.usernameTextView.setText(task.getResult().toObject(User.class).getUsername());
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView usernameTextView, dateTextView, commentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
        }
    }
}

















