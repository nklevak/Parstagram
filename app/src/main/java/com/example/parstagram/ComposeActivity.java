package com.example.parstagram;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.parstagram.fragments.ComposeFragment;
import com.example.parstagram.fragments.DetailsFragment;
import com.example.parstagram.fragments.FeedFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ComposeActivity extends AppCompatActivity {
//    Button btnPost;
//    Button btnPicture;
//    EditText etCaption;
//    ImageView ivPicture;
//    File photoFile;
//    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
//    public String photoFileName = "photo.jpg";
    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        // declare fragment manager to control fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        Toast.makeText(getApplicationContext(), "compose!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        Toast.makeText(getApplicationContext(), "profile!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_home:
                    default:
                        fragment = new FeedFragment();
                        Toast.makeText(getApplicationContext(), "feed!", Toast.LENGTH_LONG).show();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        // UNSURE ABOUT THIS
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Fragment newFragment = new DetailsFragment();
            Post post = getIntent().getParcelableExtra("POST");
            Bundle info = new Bundle();
            info.putParcelable("POST", post);
            newFragment.setArguments(info);
            fragmentManager.beginTransaction().replace(R.id.flContainer, newFragment).commit();
        }
    }
//    private void launchCamera() {
//        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference to access to future access
//        photoFile = getPhotoFileUri(photoFileName);
//
//        // wrap File object into a content provider
//        // required for API >= 24
//        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(ComposeActivity.this, "com.codepath.fileprovider", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }
//
//    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ComposeActivity");
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d("ComposeActivity", "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//
//                // RESIZE BITMAP, so the image file isn't too large
//                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(takenImage, 300);
//
//                // Configure byte output stream
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//
//                // Compress the image further
//                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
//
//                // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
//                File resizedFile = getPhotoFileUri(photoFileName + "_resized");
//                try {
//                    resizedFile.createNewFile();
//                    FileOutputStream fos = null;
//                    fos = new FileOutputStream(resizedFile);
//                    // Write the bytes of the bitmap to file
//                    fos.write(bytes.toByteArray());
//                    fos.close();
//                    // Load the taken image into a preview
//                    ivPicture.setImageBitmap(takenImage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void savePost(String caption, ParseUser user, File image) {
//        // create new post
//        Post post = new Post();
//        post.setCaption(caption);
//        post.setUser(user);
//        post.setImage(new ParseFile(photoFile));
//        // save post
//        post.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    Log.e("ComposeActivity", "post failure");
//                    e.printStackTrace();
//                    return;
//                }
//                Log.d("ComposeActivity", "success!");
//                Toast.makeText(getApplicationContext(), "success!", Toast.LENGTH_LONG).show();
//                etCaption.setText("");
//                ivPicture.setImageResource(0);
//            }
//        });
//    }
}
