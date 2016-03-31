package derekhsieh.derekhsiehapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

import AsyncTaskRunners.PostImageAsyncTaskRunner;
import AsyncTaskRunners.GetImageAsyncTaskRunner;

/**
 * Created by phoenix on 12/23/15.
 * Activity where the user can take a new picture, send a new topic, or rate and image
 */
// TODO: I could not come up with a better name for the activity for the life of me, so I'm open to any suggestions.
public class FriendPageActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Context context = this;
    private Bitmap imageBitmap = null;
    private ImageView cameraImageView;
    private String user;
    private String friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        this.user = extras.getString("username");
        this.friend = extras.getString("friend");
        String friendsImage = friend+"'s image";

        setContentView(R.layout.activity_friend_page);

        TextView friendNameTextView = (TextView) findViewById(R.id.friendNameTextView);
        friendNameTextView.setText(friendsImage);

        cameraImageView = (ImageView) findViewById(R.id.imageView);
        getImage();

        Button takeImageButton = (Button) findViewById(R.id.takeImageButton);
        takeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starts default android camera app
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        Button sendNewTopicButton = (Button) findViewById(R.id.sendNewTopicButton);
        sendNewTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opens a dialog where the user can enter a new topic
                NewTopicDialog topicDialog = new NewTopicDialog(context);
                topicDialog.show();
                topicDialog.setDialogResult(new NewTopicDialog.OnNewTopicDialogResult() {
                    @Override
                    public void finish(String result) {
                        // TODO: Implement on server side before uncommenting
//                        PostTopicAsyncTaskRunner runner = new PostTopicAsyncTaskRunner(getApplicationContext());
//                        AsyncTask<String, String, Boolean> setAsyncTask = runner.execute(FriendPageActivity.this.user, FriendPageActivity.this.friend, result);
//                        try {
//                            setAsyncTask.get();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }
                        Toast.makeText(getApplicationContext(), "SENT NEW TOPIC. YISSSS!!! " + result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button rateImageButton = (Button) findViewById(R.id.rateImageButton);
        rateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Opens a dialog where the user can select a rating
                ImageRatingDialog ratingDialog = new ImageRatingDialog(context);
                ratingDialog.show();
                ratingDialog.setDialogResult(new ImageRatingDialog.OnImageRatingDialogResult(){
                    @Override
                    public void finish(Float result) {
                        // TODO: Implement on server side before uncommenting
//                        PostRatingAsyncTaskRunner runner = new PostRatingAsyncTaskRunner(getApplicationContext());
//                        AsyncTask<String, String, Boolean> setAsyncTask = runner.execute(FriendPageActivity.this.user, FriendPageActivity.this.friend, String.valueOf(result));
//                        try {
//                            setAsyncTask.get();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }
                        Toast.makeText(getApplicationContext(), "SENT RATING. AWWWWWW YEAAA!!! " + String.valueOf(result), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void saveImage() {
        if (imageBitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            // TODO: Use Serializer to send images
            String bitmapString = Base64.encodeToString(byteArray, Base64.NO_WRAP | Base64.URL_SAFE | Base64.NO_PADDING);
            PostImageAsyncTaskRunner runner = new PostImageAsyncTaskRunner(getApplicationContext());
            AsyncTask<String, String, Boolean> setAsyncTask = runner.execute(this.user, this.friend, bitmapString);
            try {
                Boolean requests = setAsyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void getImage() {
        GetImageAsyncTaskRunner runner = new GetImageAsyncTaskRunner(getApplicationContext());
        AsyncTask<String, String, String> getAsyncTask = runner.execute(this.friend, this.user);
        try {
            String response = getAsyncTask.get();
            if (response != null)
            {
                if (response.equals("No image")) {
                    Toast.makeText(getApplicationContext(), "No image in database", Toast.LENGTH_SHORT).show();
                } else {
                    byte[] encodeByte = Base64.decode(response, Base64.NO_WRAP | Base64.URL_SAFE | Base64.NO_PADDING);
                    imageBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    cameraImageView.setImageBitmap(imageBitmap);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        Handles the return from the android default camera app
         */
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            saveImage();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable("image", imageBitmap);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        /*
        Ensures that when you rotate the device, the image will still be shown
        */
        super.onRestoreInstanceState(savedInstanceState);
        imageBitmap = (Bitmap) savedInstanceState.get("image");
        cameraImageView.setImageBitmap(imageBitmap);
    }

    public static void longInfo(String str) {
        if(str.length() > 4000) {
            System.out.println(str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            System.out.println(str);
    }
}
