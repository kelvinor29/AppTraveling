package com.kelvin.apptraveling.feature.login.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.ActivityLogInBinding;
import com.kelvin.apptraveling.feature.home.activity.HomeActivity;
import com.kelvin.apptraveling.feature.login.fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    // Codigo para pasar de un fragment a otro desde el mismo activity
    public void nextFragment(RegisterFragment registerFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, registerFragment) // Aqui remplaza el fragment al otro register fragment
                .addToBackStack(null)
                .commit();
    }

    public void backToLoginActivity() { // Maneja el evento del botón de retroceso
        getSupportFragmentManager().popBackStack();
    }

    public void changeActivity(HomeActivity homeActivity) {
        Intent goToHome = new Intent(this, HomeActivity.class);
        startActivity(goToHome);
    }


}