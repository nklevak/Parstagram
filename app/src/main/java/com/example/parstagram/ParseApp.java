package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register Post class
        ParseObject.registerSubclass(Post.class);

        // initialize parse
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("nklevakInsta")
                .clientKey("fortLee")
                .server("http://nklevak-parsetagaram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
