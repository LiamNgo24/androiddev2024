package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomeFragmentPagerAdapter extends FragmentStateAdapter {
    private final int PAGE_COUNT = 3;
    private final String[] titles = new String[] { "Hanoi", "Paris", "Toulouse" };

    public HomeFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new WeatherAndForecastFragment();
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }

    public CharSequence getPageTitle(int page) {
        return titles[page];
    }
}