package com.kelvin.apptraveling.feature.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.ActivityHomeBinding;
import com.kelvin.apptraveling.feature.home.adapter.TabsHomeAdapter;
import com.kelvin.apptraveling.feature.home.fragment.RentCarFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar); // Funcion que convierte de toolbar a actionbar

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
    public void setupTabsHome(){
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

}