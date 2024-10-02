package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
//import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;



public class WeatherActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

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

    // handle action bar item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get the ID of the clicked menu item
        int id = item.getItemId();

        // Use switch-case to handle different menu items
        switch (id) {
            case R.id.action_refresh:
                // Show toast when refresh button is clicked
                Toast.makeText(this, "Refresh clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_settings:
                // Start PrefActivity when settings button is clicked
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;

            default:
                // Let the superclass handle any other menu items
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