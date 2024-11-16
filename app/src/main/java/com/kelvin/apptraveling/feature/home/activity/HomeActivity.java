package com.kelvin.apptraveling.feature.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.ActivityHomeBinding;
import com.kelvin.apptraveling.feature.home.fragment.RentCarFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar); // Funcion que convierte de toolbar a actionbar

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    // Opciones de menu toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemMenu = item.getItemId();

        if (itemMenu == R.id.castle_nav) { // Ir a la pagina web Disney

            String urlDisney = "https://www.disneylandparis.com/es-es/";
            Intent goToUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(urlDisney));
            startActivity(goToUrl);

            return true;
        } else if (itemMenu == R.id.car_nav) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new RentCarFragment()) // Aqui remplaza el fragment al otro
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Asignar al TabLayout

}