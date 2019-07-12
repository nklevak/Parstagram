package com.example.parstagram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.parstagram.Post;
import com.example.parstagram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class DetailsFragment extends Fragment {
    // instance vars
    private ImageView ivProfile;
    private TextView tvHandle;
    private TextView tvHandleTwo;
    private TextView tvCaption;
    private TextView tvTime;
    private ImageView ivImage;
    private Post post;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize vars
        tvHandle = view.findViewById(R.id.tvHandle);
        tvCaption = view.findViewById(R.id.tvCaption);
        tvTime = view.findViewById(R.id.tvTime);
        ivImage = view.findViewById(R.id.ivImage);
        tvHandleTwo = view.findViewById(R.id.tvHandleTwo);

        // get back post
        Bundle extras = getArguments();
        post = extras.getParcelable("POST");

        // find user and set handle
        ParseUser user = post.getUser();
        tvHandle.setText(user.getUsername());
        tvHandleTwo.setText("@" + user.getUsername());
        tvCaption.setText(post.getCaption());
        tvTime.setText(post.getCreatedAt().toString()); // fix date formstting
        if (post.getImage() != null) {
            Glide.with(getContext())
                    .load(post.getImage().getUrl())
                    .into(ivImage);
        }

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
    }
}
