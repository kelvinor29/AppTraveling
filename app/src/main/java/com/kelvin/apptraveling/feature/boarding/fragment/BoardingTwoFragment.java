package com.kelvin.apptraveling.feature.boarding.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.FragmentBoardingOneBinding;
import com.kelvin.apptraveling.databinding.FragmentBoardingTwoBinding;
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

public class BoardingTwoFragment extends Fragment {

    FragmentBoardingTwoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentBoardingTwoBinding.inflate(inflater, container, false);
        listeners();
        return binding.getRoot();
    }

    public void listeners() {
        binding.bSkip.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_boardingTwoFragment_to_loginActivity);

        });

        // Ir al tercer Boarding
        binding.bNext.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_boardingTwoFragment_to_boardingThreeFragment);
        });
    }
}
