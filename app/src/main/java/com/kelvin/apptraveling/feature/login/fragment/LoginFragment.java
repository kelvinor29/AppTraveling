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

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.FragmentLoginBinding;
import com.kelvin.apptraveling.feature.home.activity.HomeActivity;
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private String username;
    private String userEmail;
    private String userPassword;
    private String userAge;

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

        // TextWatcher para el manejo de los errores y habilitar el button logIn
        binding.tietUsernameTyped.addTextChangedListener(textWatcher());
        binding.tietPasswordTyped.addTextChangedListener(textWatcher());

        validateUserData();

    }

    public void recoverData() {
        // Recupera los datos del bundle de la clave "userData"
        getParentFragmentManager().setFragmentResultListener("userDataFromRegister", this, (requestKey, result) -> {
            username = result.getString("username");
            userEmail = result.getString("email");
            userPassword = result.getString("password");
            userAge = result.getString("age");

            Snackbar.make(binding.getRoot(), "Nombre: " + username + ", Email: " + userEmail + ", Password: " + userPassword + ", Edad: " + userAge, Snackbar.LENGTH_LONG).show();

            Log.d("Depurando", "Datos Recibidos al Login: Username: " + username + ", Email: " + userEmail + ", Password: " + userPassword + ", Edad: " + userAge);

            validateUserData();
        });
    }

    public void validateUserData() {
        // Recuperar el texto digitado en los textInputEditText
        String usernameTyped = binding.tietUsernameTyped.getText().toString().trim();
        String passwordTyped = binding.tietPasswordTyped.getText().toString().trim();

        // Verificacion que los editTexts estén correctamente escritos
        boolean isUsernameValid = binding.tietUsernameTyped.getText().toString().matches("[a-z]+");
        boolean isUserNameCorrect = binding.tietUsernameTyped.getText().toString().equals(username);
        boolean isPasswordValid = binding.tietPasswordTyped.getText().toString().length() >= 5;
        boolean isPasswordCorrect = binding.tietPasswordTyped.getText().toString().equals(userPassword);

        binding.tilUsernameTyped.setError(isUsernameValid ? null : getString(R.string.errorUsername));
        binding.tilPasswordTyped.setError(isPasswordValid ? null : "Debe de contener al menos 5 carácteres");
        binding.tilPasswordTyped.setError(isPasswordCorrect ? null : "La contraseña no coincide.");

        //binding.bLogIn.setEnabled(isUsernameValid && isUserNameCorrect && isPasswordValid && isPasswordCorrect);
        binding.bLogIn.setEnabled(true);
    }


    private TextWatcher textWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateUserData();
            }
        };

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

                String usernameTyped = binding.tietUsernameTyped.getText().toString().trim();
                String passwordTyped = binding.tietPasswordTyped.getText().toString().trim();

                sendUserData(usernameTyped, passwordTyped);
            }
        });

        // Mostrar mensajes "Próximamente"
        View.OnClickListener showComingSoonMessage = v -> Snackbar.make(binding.getRoot(), "Próximamente...", Snackbar.LENGTH_SHORT).show();
        binding.tvForgetPassword.setOnClickListener(showComingSoonMessage);
        binding.btnGoogle.setOnClickListener(showComingSoonMessage);
    }

    //Enviar los datos a la home
    public void sendUserData(String textUsername, String textPassword) {
        // Ir a la home  con los datos que se introdujo
        Bundle userData = new Bundle();
        userData.putString("username", textUsername);
        userData.putString("password", textPassword);

        getParentFragmentManager().setFragmentResult("userDataFromLogin", userData);

        Log.d("Depurando", "Datos Enviados al Login: Username: " + textUsername + ", Password: " + textPassword);

        ((LoginActivity) getActivity()).changeActivity(new HomeActivity());

    }
}