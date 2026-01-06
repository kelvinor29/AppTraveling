package com.kelvin.apptraveling.feature.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.data.models.Hotel;
import com.kelvin.apptraveling.databinding.CardViewHotelsBinding;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    CardViewHotelsBinding binding;
    public List<Hotel> hotelList;
    private boolean isDataLoaded = false;
    private OnHotelClickListener listener;

    public interface OnHotelClickListener {
        void onHotelClick(Hotel hotel);
    }

    public HotelAdapter(List<Hotel> hotelList, OnHotelClickListener listener) {
        this.hotelList = hotelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_view_hotels, parent, false);
        return new HotelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bind(hotel);

        holder.binding.cvHotel.setOnClickListener(v -> {
            if (listener != null)
                listener.onHotelClick(hotel);
        });

        // Verificar y actualizar el estado de carga
        if (isDataLoaded) {

            // Detiene el shimmer y muestra los resultados reales
            holder.binding.smCards.stopShimmer();
            holder.binding.smCards.setVisibility(View.GONE);
            holder.binding.cvHotel.setVisibility(View.VISIBLE);
        } else {
            // Mostrar el shimmer mientras se cargan los datos
            holder.binding.smCards.startShimmer();
            holder.binding.smCards.setVisibility(View.VISIBLE);
            holder.binding.cvHotel.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {

        return hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        private final CardViewHotelsBinding binding;

        public HotelViewHolder(CardViewHotelsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Hotel hotel) {
            binding.setHotel(hotel);
            binding.executePendingBindings();
        }
    }

    // Funcion para actualizar el estado de carga
    public void setDataLoaded(boolean isDataLoaded) {
        this.isDataLoaded = isDataLoaded;
        notifyDataSetChanged();
    }

}
