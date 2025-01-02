package com.kelvin.apptraveling.feature.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.CardViewCarsBinding;
import com.kelvin.apptraveling.data.models.Car;

import java.util.List;

public class MyTabsRecyclerViewAdapter extends RecyclerView.Adapter {

    List<Car> carsList;
    CardViewCarsBinding binding;

    public MyTabsRecyclerViewAdapter(List<Car> carsList) {
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_cars, parent, false);
        return new CarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CarsViewHolder carsViewHolder = (CarsViewHolder) holder;
        binding.setCar(carsList.get(position));
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {

        public CarsViewHolder(@NonNull View view) {
            super(view);
            binding = CardViewCarsBinding.bind(view);
        }
    }
}
