package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // instance vars
        TextView tvHandle;
        TextView tvHandleBottom;
        TextView tvCaption;
        ImageView ivImage;
        ImageView ivProfile;


        @Override
        public void onClick(View view) {
            // get and validate position
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // get post at click position
                Post post = posts.get(position);

                Intent intent = new Intent(context, ComposeActivity.class);
                intent.putExtra("POST", post);
                context.startActivity(intent);
            }
        }

        public ViewHolder(@NonNull View item) {
            super(item);

            // set on click listener
            item.setOnClickListener(this);

            // initialize vars using findById
            tvHandle = item.findViewById(R.id.tvHandle);
            tvHandleBottom = item.findViewById(R.id.tvBottomHandle);
            tvCaption = item.findViewById(R.id.tvCaption);
            ivImage = item.findViewById(R.id.ivImage);
            ivProfile = item.findViewById(R.id.ivPropic);
        }

        public void bind(Post post) {
            // todo use better string formatting
            tvHandle.setText("@" + post.getUser().getUsername());
            tvHandleBottom.setText("@" + post.getUser().getUsername() + ":");
            tvCaption.setText(post.getCaption());
            // load image
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }
            ParseFile profile = post.getUser().getParseFile("profile");
            if (profile != null) {
                Glide.with(context)
                        .load(profile.getUrl())
                        .apply(RequestOptions.circleCropTransform())

                        .into(ivProfile);
            }
            else {
                Glide.with(context)
                        .load("@drawable/user.png")
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivProfile);
            }
        }
    }

    // added for swipe to refresh

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}
