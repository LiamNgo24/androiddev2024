package vn.edu.usth.weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_weather);

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(this);

        pager.setAdapter(adapter);


//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, weatherFragment)
//                .add(R.id.container, forecastFragment)
//                .commit();
//
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, forecastFragment)
//                .commit();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i("create" , "onCreate called");
    }

    public void onStart() {
        super.onStart();


        Log.i("start" , "onStart called");
    }

    public void onPause() {
        super.onPause();


        Log.i("pause" , "onPause called");
    }

    public void onResume() {
        super.onResume();

        Log.i("resume" , "onResume called");
    }

    public void onStop() {
        super.onStop();

        Log.i("stop" , "onStop called");

    }

    public void onDestroy() {
        super.onDestroy();

        Log.i("Destroy" , "onDestroy called");
    }


}