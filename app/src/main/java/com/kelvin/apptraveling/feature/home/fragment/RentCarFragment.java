package com.kelvin.apptraveling.feature.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kelvin.apptraveling.databinding.FragmentRentCarBinding;
import com.kelvin.apptraveling.feature.home.adapter.CarsAdapter;
import com.kelvin.apptraveling.feature.home.adapter.MyTabsRecyclerViewAdapter;
import com.kelvin.apptraveling.feature.home.domain.CarsProvider;

public class RentCarFragment extends Fragment {

   private FragmentRentCarBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRentCarBinding.inflate(inflater, container, false);

        CarsProvider carsList = new CarsProvider();
        binding.rvCars.setHasFixedSize(true);
        binding.rvCars.setAdapter(new CarsAdapter(carsList.getCarsList()));
        return binding.getRoot();

    }

}