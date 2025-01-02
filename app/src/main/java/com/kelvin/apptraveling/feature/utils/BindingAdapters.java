package com.kelvin.apptraveling.feature.utils;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.kelvin.apptraveling.R;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Imagen de carga
                    .error(R.drawable.ic_home) // Imagen en caso de error
                    .into(view);
        } else {
            view.setImageResource(R.drawable.ic_yellow_star); // Imagen por defecto
        }
    }

}
