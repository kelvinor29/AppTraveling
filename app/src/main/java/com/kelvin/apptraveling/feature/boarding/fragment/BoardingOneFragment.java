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
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

public class BoardingOneFragment extends Fragment {

    FragmentBoardingOneBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardingOneBinding.inflate(inflater, container, false);

        listeners();
        return binding.getRoot();
    }

    public void listeners() {

        // Ir al segundo Boarding
        binding.bNext.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_boardingOneFragment_to_boardingTwoFragment);
        });
    }
}

