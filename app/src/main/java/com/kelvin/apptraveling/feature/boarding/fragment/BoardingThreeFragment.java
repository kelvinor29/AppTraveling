package com.kelvin.apptraveling.feature.boarding.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.FragmentBoardingThreeBinding;

public class BoardingThreeFragment extends Fragment {

    FragmentBoardingThreeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardingThreeBinding.inflate(inflater, container, false);

        binding.bNext.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_boardingThreeFragment_to_loginActivity);

        });

        return binding.getRoot();
    }


}