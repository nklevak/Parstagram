package com.example.parstagram;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // get 20 most recent posts
        posts = new ArrayList<>();
        queryPosts();


    }

    private void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        // since it's an expensive operation you want to do this in a background thread not in the
        // sane thread as the UI
        postQuery.orderByDescending("created_at");
        postQuery.setLimit(20);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postsQ, ParseException e) {
                if (e != null) {
                    Log.e("ComposeActivity", "query failed");
                    e.printStackTrace();
                    return;
                }
                // add posts to list
                for (int i = 0; i < postsQ.size(); i++) {
                    posts.add(postsQ.get(i));
                }
                Log.d("ComposeActivity", "number of posts: " + posts.size());
            }
        });
    }
}