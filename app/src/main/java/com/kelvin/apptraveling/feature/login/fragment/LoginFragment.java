package com.kelvin.apptraveling.feature.login.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.database.User;
import com.kelvin.apptraveling.databinding.FragmentLoginBinding;
import com.kelvin.apptraveling.feature.home.activity.HomeActivity;
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recoverData();

        setupListeners();

    }

    // Recupera el dato User del bundle de la clave "userData"
    public void recoverData() {
        getParentFragmentManager().setFragmentResultListener("userDataFromRegister", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Recupera el objeto User
                user = result.getParcelable("user");

                if (user != null) {
                    Snackbar.make(binding.getRoot(), "Nombre: " + user.getUserName() + ", Email: " + user.getUserEmail() +
                            ", Password: " + user.getUserPassword() + ", Edad: " + user.getUserAge(), Snackbar.LENGTH_LONG).show();

                }
            }
        });
    }

    private void setupListeners() {
        // Lanzar al Register Fragment
        binding.tvRegisterNow.setOnClickListener(v -> {
            ((LoginActivity) getActivity()).nextFragment(new RegisterFragment());
        });

        // Lanzar al Home Activity
        binding.bLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    if (isCorrectUser()) {
                        sendUserData(user);
                    } else {
                        Snackbar.make(binding.getRoot(), getString(R.string.errorPasswordLogIn), Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Snackbar.make(binding.getRoot(), "No existe ningún usuario, debe de registrarse primero", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // Mostrar mensajes "Próximamente"
        View.OnClickListener showComingSoonMessage = v -> Snackbar.make(binding.getRoot(), "Próximamente...", Snackbar.LENGTH_SHORT).show();
        binding.tvForgetPassword.setOnClickListener(showComingSoonMessage);
        binding.btnGoogle.setOnClickListener(showComingSoonMessage);
    }

    public boolean isCorrectUser(){

        String usernameTyped = String.valueOf(binding.tietUsernameTyped.getText()).trim();
        String passwordTyped = String.valueOf(binding.tietPasswordTyped.getText()).trim();

        // Validar usuario correcto
        return usernameTyped.equals(String.valueOf(user.getUserName())) && passwordTyped.equals(String.valueOf(user.getUserPassword()));
    }

    //Enviar los datos a la home
    public void sendUserData(User user) {
        // Ir a la home  con los datos que se introdujo
        Bundle userData = new Bundle();
        userData.putParcelable("user", user);

        getParentFragmentManager().setFragmentResult("userDataFromLogin", userData);

        Log.d("Depurando", "Datos Enviados al Login: Username: " + user.getUserName() + ", Password: " + user.getUserPassword());

        ((LoginActivity) getActivity()).changeActivity(new HomeActivity());

    }
}