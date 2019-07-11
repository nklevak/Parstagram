package com.example.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if user was previously logged in
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // set current user and go to home page
            Intent i = new Intent(MainActivity.this, ComposeActivity.class);
            startActivity(i);
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // on click listener for when user logs in
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                login(username, password);
            }
        });

        // on click user for when user wants to create a new account
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // navigate to signup page
                Intent intent = new Intent (MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String username, String password) {
        // set up parse configuration
        // do log in in background instead of just log in so that it does not run on the main thread
        // if it ran on the main thread, it wouldn't refresh and the UI would not update
        // it would eventually throw an error
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");

                    // navigate to home page
                    Intent intent = new Intent (MainActivity.this, ComposeActivity.class);
                    startActivity(intent);

                    // have to finish log in so that user cant just log out by clicking back
                    finish();
                }
                else {
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });

    }
}
