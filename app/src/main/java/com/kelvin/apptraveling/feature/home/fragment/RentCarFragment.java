package com.kelvin.apptraveling.feature.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    private CarsAdapter carsAdapter;


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
        carsAdapter = new CarsAdapter(carsList.getCarsList());

        binding.rvCars.setAdapter(carsAdapter);

        // Simulacion de carga de datos
        Handler handler = new Handler(requireActivity().getMainLooper());
        handler.postDelayed(() -> carsAdapter.setDataLoaded(true), 2000);

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}