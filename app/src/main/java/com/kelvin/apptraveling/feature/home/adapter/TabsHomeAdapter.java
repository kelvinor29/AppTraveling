package com.kelvin.apptraveling.feature.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kelvin.apptraveling.feature.home.fragment.HomeFragment;
import com.kelvin.apptraveling.feature.home.fragment.PlacesFragment;
import com.kelvin.apptraveling.feature.home.fragment.ProfileFragment;
import com.kelvin.apptraveling.feature.home.fragment.RentCarFragment;

public class TabsHomeAdapter extends FragmentStateAdapter {

    public TabsHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new RentCarFragment();
            case 2:
                return new PlacesFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
