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
//                intent.putExtra("method", "details");
                context.startActivity(intent);

//                // create a fragment
//                Fragment fragment = new DetailsFragment();
//
//                // add post information to fragment
//                Bundle info = new Bundle();
//                info.putParcelable("POST", post);
//                fragment.setArguments(info);
//
//                // start new fragment


//                // create an intent to display the additional info activity
//                // an intent sends out a broadcast that you want something from a different part of the app
//                Intent intent = new Intent(context, com.example.flixster.MovieDetailsActivity.class);
//                // .putExtra() sends additional data with the intent
//                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
//                // starts activity
//                context.startActivity(intent);
            }
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.action_compose:
//                    fragment = new ComposeFragment();
//                    Toast.makeText(getApplicationContext(), "compose!", Toast.LENGTH_LONG).show();
//                    break;
//                case R.id.action_profile:
//                    fragment = new ProfileFragment();
//                    Toast.makeText(getApplicationContext(), "profile!", Toast.LENGTH_LONG).show();
//                    break;
//                case R.id.action_home:
//                default:
//                    fragment = new FeedFragment();
//                    Toast.makeText(getApplicationContext(), "feed!", Toast.LENGTH_LONG).show();
//                    break;
//            }
//            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
//            return true;
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
                        .into(ivProfile);
            }
            else {
                Glide.with(context)
                        .load("@drawable/icon.png")
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
