package com.example.musicvibes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    Context context;
    List<PostModel> posts;
    OnPostClickListener listener;

    interface OnPostClickListener {
        void onClick(PostModel post);
    }

    public PostAdapter(Context context, List<PostModel> posts, OnPostClickListener listener) {
        this.context = context;
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        PostModel post = posts.get(position);
        holder.title.setText(post.title);
        holder.excerpt.setText(post.excerpt);
        Glide.with(context).load(post.imageUrl).into(holder.image);
        holder.itemView.findViewById(R.id.ibDetail).setOnClickListener(v -> listener.onClick(post));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView excerpt;
        ImageView image;
        TextView publishDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postTitle);
            excerpt = itemView.findViewById(R.id.postDesc);
            image = itemView.findViewById(R.id.postImage);
            publishDate = itemView.findViewById(R.id.tvDate);
        }
    }
}