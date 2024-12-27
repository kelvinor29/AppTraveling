package com.kelvin.apptraveling.feature.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.databinding.FragmentHomeBinding;
import com.kelvin.apptraveling.feature.home.domain.Car;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        getParentFragmentManager().setFragmentResultListener("userDataFromLogin", this, ((requestKey, result) -> {
            username = result.getString("username");

            Log.d("Depurando", "Data received: Username: " + username);
            Snackbar.make(binding.getRoot(), "Name: " + username, Snackbar.LENGTH_LONG).show();
        }));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}