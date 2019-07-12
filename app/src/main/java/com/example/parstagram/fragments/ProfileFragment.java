package com.example.parstagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.parstagram.GridAdapter;
import com.example.parstagram.MainActivity;
import com.example.parstagram.Post;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    // instance vars
    private Button btnSignout;
    private TextView tvHandle;
    private ImageView ivProfile;
    private RecyclerView rvGrid;
    private List<Post> postsList;
    GridAdapter gridAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find current user and set handle
        ParseUser user = ParseUser.getCurrentUser();
        tvHandle = view.findViewById(R.id.tvHandle);
        tvHandle.setText("@" + user.getUsername());
        rvGrid = view.findViewById(R.id.rvGrid);
        postsList = new ArrayList<>();

        // set up grid view adapter JUST ADDED
        rvGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
        gridAdapter = new GridAdapter(getContext(), postsList);
        rvGrid.setAdapter(gridAdapter);

        // todo get posts by user
        queryUserPosts();

        // set profile picture
         ivProfile = view.findViewById(R.id.ivProfile);

        ParseFile profile = user.getParseFile("profile");
        if (profile != null) {
            Glide.with(getContext())
                    .load(profile.getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfile);
        }
        else {
            Glide.with(getContext())
                    .load("@drawable/user.png")
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfile);
        }

        // set on click listener for sign out button
        btnSignout = view.findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // logs user out
                ParseUser.logOut();

                // goes back to sign in page
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void queryUserPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        // since it's an expensive operation you want to do this in a background thread not in the
        // same thread as the UI
        postQuery.orderByDescending("createdAt");
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postsQ, ParseException e) {
                if (e != null) {
                    Log.e("ComposeActivity", "query failed");
                    e.printStackTrace();
                    return;
                }
                postsList.addAll(postsQ);
                gridAdapter.notifyDataSetChanged();
                Log.d("ComposeActivity", "number of posts: " + postsList.size());
            }
        });
    }
}
