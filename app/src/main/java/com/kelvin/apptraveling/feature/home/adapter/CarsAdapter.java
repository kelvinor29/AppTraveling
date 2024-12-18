package com.kelvin.apptraveling.feature.home.adapter;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.CardViewCarsBinding;
import com.kelvin.apptraveling.feature.home.domain.Car;

import java.util.ArrayList;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarHolder> {

    CardViewCarsBinding binding;

    private final ArrayList<Car> carsList;

    public CarsAdapter(ArrayList<Car> carsList) {
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public CarsAdapter.CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.card_view_cars, null);
        return new CarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.CarHolder holder, int position) {
        binding.setCar(carsList.get(position));
        holder.bind(carsList.get(position));
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public class CarHolder extends RecyclerView.ViewHolder {

        public CarHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardViewCarsBinding.bind(itemView);

            // Boton de eliminar un cardview con sus datos
            binding.ibTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        carsList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

            // Listener para mostrar el vehiculo seleccionado
            binding.clCardvBackground.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Snackbar.make(binding.getRoot(), String.format("%s Selected", carsList.get(position).getCarName()), Snackbar.LENGTH_LONG).show();
                }
            });
        }

        public void bind(Car itemCar) {
            binding.ivCarImg.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), itemCar.getCarFeatures().getImgCar()));
            binding.vBackgroundCar.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), itemCar.getCarFeatures().getColorCar())));
        }
    }
}
