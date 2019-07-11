package com.example.parstagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.parstagram.MainActivity;
import com.example.parstagram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    // instance vars
    private Button btnSignout;
    private TextView tvHandle;
    private ImageView ivProfile;
//    private TextView tvName;

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
//        tvName = view.findViewById(R.id.tvName);
        tvHandle.setText("@" + user.getUsername());

        // todo--set profile picture
        // DOESN'T WORJK FOR PROFILE VIEW
         ivProfile = view.findViewById(R.id.ivProfile);
        ParseFile profile = user.getParseFile("profile");
            if (profile != null) {
                Glide.with(getContext())
                        .load(profile.getUrl())
                        .into(ivProfile);
            }
            else {
                Glide.with(getContext())
                        .load("@drawable/icon.png")
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
}
