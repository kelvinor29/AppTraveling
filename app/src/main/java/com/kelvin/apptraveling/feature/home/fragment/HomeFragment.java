package com.kelvin.apptraveling.feature.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.data.api.API;
import com.kelvin.apptraveling.data.models.Hotel;
import com.kelvin.apptraveling.data.models.HotelResponse;
import com.kelvin.apptraveling.databinding.FragmentHomeBinding;
import com.kelvin.apptraveling.feature.home.adapter.HotelAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String username;
    private HotelAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Recibe datos del fragment de login
        getParentFragmentManager().setFragmentResultListener("userDataFromLogin", this, ((requestKey, result) -> {
            username = result.getString("username");

            Log.d("Depurando", "Data received: Username: " + username);
            Snackbar.make(binding.getRoot(), "Name: " + username, Snackbar.LENGTH_LONG).show();
        }));

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new HotelAdapter(new ArrayList<>());

        binding.rvHotels.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvHotels.setAdapter(adapter);

        fetchHotels();

    }

    private void fetchHotels() {
        API api = new API(API.BASE_URL);
        Call<HotelResponse> call = api.service.getHotels();

        // Mostrar shimmer
        call.enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {

                if (response.isSuccessful()) {

                    // Validar si se encontraron resultados
                    if (response.body().getResults() == null) {
                        binding.llNullHotels.setVisibility(View.VISIBLE);
                    }

                    List<Hotel> hotels = response.body().getResults();

                    adapter.hotelList.clear();
                    adapter.hotelList.addAll(hotels);
                    adapter.notifyDataSetChanged();

                    adapter.setDataLoaded(true);

                } else {
                    binding.llNullHotels.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                binding.llNullHotels.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}