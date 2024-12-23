package com.kelvin.apptraveling.feature.home.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.ActivityHomeBinding;
import com.kelvin.apptraveling.feature.home.adapter.TabsHomeAdapter;


public class HomeActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar); // Funcion que convierte de toolbar a actionbar

        checkLocationPermition();

        setupTabsHome();

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

        }

        return super.onOptionsItemSelected(item);
    }

    // Asignar al TabLayout
    public void setupTabsHome() {
        TabsHomeAdapter adapter = new TabsHomeAdapter(this);
        binding.vpHome.setAdapter(adapter);

        new TabLayoutMediator(binding.tbHome, binding.vpHome, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic_camera);
                        tab.setContentDescription(getString(R.string.upcoming_meetups));
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_car_nonshape);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_mountains);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.ic_user);
                        break;
                }
            }
        }).attach();
    }

    // Validacion de permisos de ubicacion
    public void checkLocationPermition() {

        // Validacion si ya tiene el permiso de ubicacion
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar el permiso al usuario
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    // Verificacion de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            // Validar si el permiso fue concedido
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                // Notificarle al usuario
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("WARNING");
                builder.setMessage("Cannot proceed without location permission.");

                builder.setPositiveButton("Ok", (dialog, which) -> finish());

                builder.setCancelable(false);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }
    }
}