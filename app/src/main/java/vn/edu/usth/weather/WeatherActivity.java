package vn.edu.usth.weather;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
