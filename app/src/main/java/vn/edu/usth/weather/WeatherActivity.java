package vn.edu.usth.weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private final String imageUrl = "https://usth.edu.vn/wp-content/uploads/2021/11/logo.png"; // Image URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_weather);

        // set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(this);

        pager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, pager, (tab, position) -> tab.setText(adapter.getPageTitle(position))).attach();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i("create", "onCreate called");

        playAudio();
    }

    // inflate toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // async task to download an image
    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        private final Handler handler;

        public ImageDownloadTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(WeatherActivity.this, "Downloading image...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap image = null;
            InputStream inputStream = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(imageUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                inputStream = connection.getInputStream();
                image = BitmapFactory.decodeStream(inputStream); // decode the image stream into a Bitmap
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return image; // return the downloaded Bitmap
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // pass the bitmap to the handler
                Bundle bundle = new Bundle();
                bundle.putParcelable("server_response", result);

                Message msg = new Message();
                msg.setData(bundle);

                handler.sendMessage(msg);
            } else {
                Toast.makeText(WeatherActivity.this, "Failed to download image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // handler to process the downloaded image and set it in the ImageView
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = msg.getData().getParcelable("server_response");

            if (bitmap != null) {
                ImageView imageView = findViewById(R.id.imageView); // applies to imageView id
                imageView.setImageBitmap(bitmap); // display the downloaded image
            } else {
                Toast.makeText(WeatherActivity.this, "No image to display", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                new ImageDownloadTask(handler).execute(imageUrl); // start image download task with URL
                return true;

            case R.id.action_settings:
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("pause", "onPause called");
        // pause the media player if it's playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("resume", "onResume called");
        // resume the media player if it was paused
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Destroy", "onDestroy called");
        // release the media player resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playAudio() {
        mediaPlayer = MediaPlayer.create(this, R.raw.sample);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start(); // plays audio automatically
            Log.i("MediaPlayer", "Audio started and looping enabled");
        } else {
            Log.e("MediaPlayer", "Failed to initialize MediaPlayer");
        }
    }
}
