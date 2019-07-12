package com.example.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public EditText etUsername;
    public EditText etPassword;
    public EditText etEmail;
    public EditText etHandle;
    public Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // get edit text items by id
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etHandle = findViewById(R.id.etHandle);

        // get button and create on click listener
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // set up new user on parse server
                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set core properties
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setEmail(etEmail.getText().toString());

                // Set custom properties
                user.put("handle", etHandle.getText().toString());
//                File userPic = getPhotoFileUri("user.png");
//                user.put("profile", new ParseFile(userPic));

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("LoginActivityForSignup", "Signup successful");

                            // navigate to home page
                            Intent intent = new Intent (SignupActivity.this, ComposeActivity.class);
                            startActivity(intent);

                            // have to finish log in so that user cant just log out by clicking back
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG);
                            Log.e("LoginActivityForSignup", "Signup failure");
                            e.printStackTrace();

                            // navigate back to login page
                            Intent intent = new Intent (SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }


}
