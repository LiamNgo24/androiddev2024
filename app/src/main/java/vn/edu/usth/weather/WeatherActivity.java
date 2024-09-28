package vn.edu.usth.weather;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;

public class WeatherActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_weather);

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

    @Override
    public void onStart() {
        super.onStart();
        Log.i("start", "onStart called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("pause", "onPause called");
        // Pause the media player if it's playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("resume", "onResume called");
        // Resume the media player if it was paused
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("stop", "onStop called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Destroy", "onDestroy called");
        // Release the media player resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Method to initialize and play an audio file with looping
    private void playAudio() {
        // Initialize the media player with an MP3 file from the raw folder
        mediaPlayer = MediaPlayer.create(this, R.raw.sample); // Replace 'your_audio' with the name of your file
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // Enable looping
            mediaPlayer.start(); // Start playing the audio automatically
            Log.i("MediaPlayer", "Audio started and looping enabled");
        } else {
            Log.e("MediaPlayer", "Failed to initialize MediaPlayer");
        }
    }
}