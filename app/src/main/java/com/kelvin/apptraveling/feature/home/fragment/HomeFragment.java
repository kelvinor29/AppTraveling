package com.kelvin.apptraveling.feature.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.data.api.API;
import com.kelvin.apptraveling.data.models.Hotel;
import com.kelvin.apptraveling.data.models.HotelResponse;
import com.kelvin.apptraveling.databinding.FragmentHomeBinding;
import com.kelvin.apptraveling.feature.home.adapter.HotelAdapter;
import com.kelvin.apptraveling.feature.home.fragment.map.MapFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HotelAdapter hotelAdapter;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        getParentFragmentManager().setFragmentResultListener("userDataFromLogin", this, ((requestKey, result) -> {
            username = result.getString("username");
            Snackbar.make(binding.getRoot(), "Bienvenido: " + username, Snackbar.LENGTH_LONG).show();
        }));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hotelAdapter = new HotelAdapter(new ArrayList<>(), this::navigateToMap);

        binding.rvHotels.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvHotels.setAdapter(hotelAdapter);

        fetchHotels();
    }

    private void navigateToMap(Hotel hotel) {
        MapFragment mapFragment = MapFragment.newInstance(hotel);

        FrameLayout mapContainer = requireActivity().findViewById(R.id.fl_mapFragment);

        mapContainer.setVisibility(View.VISIBLE);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_mapFragment, mapFragment)
                .addToBackStack(null)
                .commit();
    }


    private void fetchHotels() {
        API api = new API(API.BASE_URL);
        Call<HotelResponse> call = api.service.getHotels();

        call.enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelResponse> call, @NonNull Response<HotelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hotel> hotels = response.body().getResults();

                    if (hotels == null || hotels.isEmpty()) {
                        binding.llNullHotels.setVisibility(View.VISIBLE);
                    } else {
                        binding.llNullHotels.setVisibility(View.GONE);
                        hotelAdapter.hotelList.clear();
                        hotelAdapter.hotelList.addAll(hotels);
                        hotelAdapter.setDataLoaded(true);
                    }
                } else {
                    binding.llNullHotels.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelResponse> call, @NonNull Throwable t) {
                if (binding != null) {
                    binding.llNullHotels.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
