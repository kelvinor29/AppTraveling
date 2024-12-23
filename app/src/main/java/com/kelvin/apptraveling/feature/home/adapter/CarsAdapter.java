package com.kelvin.apptraveling.feature.home.adapter;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.CardViewCarsBinding;
import com.kelvin.apptraveling.feature.home.domain.Car;

import java.util.ArrayList;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarHolder> {

    CardViewCarsBinding binding;
    private final ArrayList<Car> carsList;
    private boolean isDataLoaded = false;

    public CarsAdapter(ArrayList<Car> carsList) {
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public CarsAdapter.CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CardViewCarsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CarHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.CarHolder holder, int position) {
        binding.setCar(carsList.get(position));
        holder.bind(carsList.get(position));

        if (isDataLoaded) {

            // Detiene el shimmer y muestra los datos reales
            holder.svCards.stopShimmer();
            holder.svCards.setVisibility(View.GONE);
            holder.cv_car.setVisibility(View.VISIBLE);

        } else {
            // Mostrar el shimmer mientras se cargan los datos
            holder.svCards.startShimmer();
            holder.svCards.setVisibility(View.VISIBLE);
            holder.cv_car.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    // Funcion para actualizar el estado de carga (practicando la utilizacion de la libreria shimmer)
    public void setDataLoaded(boolean isDataLoaded) {
        this.isDataLoaded = isDataLoaded;
        notifyDataSetChanged();
    }

    public class CarHolder extends RecyclerView.ViewHolder {

        private final ShimmerFrameLayout svCards = binding.svCards;
        private final CardView cv_car = binding.cvCar;

        public CarHolder(@NonNull View itemView) {
            super(itemView);

            // Boton de eliminar un cardview con sus datos
            binding.ibTrash.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    carsList.remove(position);
                    notifyItemRemoved(position);
                }
            });

            // Listener para mostrar el vehiculo seleccionado
            binding.clCardvBackground.setOnClickListener(v -> {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Snackbar.make(itemView, String.format("%s Selected", carsList.get(position).getCarName()), Snackbar.LENGTH_LONG).show();
                }
            });
        }

        public void bind(Car itemCar) {
            binding.ivCarImg.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), itemCar.getCarFeatures().getImgCar()));
            binding.vBackgroundCar.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(itemView.getContext(), itemCar.getCarFeatures().getColorCar())));
        }
    }
}
