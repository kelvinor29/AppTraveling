package com.kelvin.apptraveling.feature.home.fragment.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.data.models.Hotel;
import com.kelvin.apptraveling.databinding.FragmentMapBinding;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private GoogleMap googleMap;
    private Hotel selectedHotel;


    //    Factory method to create a new instance of this fragment using the provided parameters.
    public static MapFragment newInstance(Hotel hotel) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putParcelable("hotel", hotel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the selected hotel from the arguments
        if (getArguments() != null)
            selectedHotel = (Hotel) getArguments().getParcelable("hotel");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // Check if mapFragment is not null
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    /**
     * Callback que se ejecuta cuando el GoogleMap está listo para usarse
     * Por qué funciona: El mapa se carga de forma asíncrona, así que esperamos a que esté listo
     * antes de intentar agregar marcadores o modificar la cámara.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (selectedHotel == null)
            return;

        if (selectedHotel.getAddress() == null)
            return;

        Double latitude = selectedHotel.getCoordinate().getLat();
        Double longitude = selectedHotel.getCoordinate().getLon();

        if (latitude == null || longitude == null)
            return;

        LatLng hotelLocation = new LatLng(latitude, longitude);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(hotelLocation)
                .title(selectedHotel.getName())
                .snippet(selectedHotel.getAddress().getStreetAddress());

        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotelLocation, 15f));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        FrameLayout mapContainer = requireActivity().findViewById(R.id.fl_mapFragment);
        mapContainer.setVisibility(View.GONE);
    }
}