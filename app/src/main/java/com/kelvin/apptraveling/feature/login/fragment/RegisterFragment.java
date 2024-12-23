package com.kelvin.apptraveling.feature.login.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.FragmentRegisterBinding;
import com.kelvin.apptraveling.database.User;
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        setOptionsAges();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String urlTerms = "https://developers.google.com/ml-kit/terms";

        listeners();
        openCameraToPic();
        openChromeURL(urlTerms);

        toolBarFunctions();
        setHasOptionsMenu(true); // Funcion para permitir el manejo del menú de opciones

    }

    private void setOptionsAges() {
        String[] ageOptions = getResources().getStringArray(R.array.options_ages);

        // Adaptador
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, ageOptions);
        binding.actvAge.setAdapter(arrayAdapter);
    }

    // Este es un ejemplo del manejo del menú de opciones (Retroceder desde register)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            // Maneja el evento del botón de retroceso
            ((LoginActivity) getActivity()).backToLoginActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Configuracion del Toolbar (Up Button)
    public void toolBarFunctions() {
        // Utilizacion del la toolbar del Activity principal
        ((LoginActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ((LoginActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().setTitle(R.string.register);
        ((LoginActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

    }

    // Funcion para abrir algun URL
    public void openChromeURL(String urlTerms) {
        binding.tvSeeTerms.setOnClickListener(v -> {
            Intent openURL = new Intent(Intent.ACTION_VIEW, Uri.parse(urlTerms));
            startActivity(openURL);
        });
    }

    // Agregar foto al ImageView
    ActivityResultLauncher<Intent> activityResultLancherCamara = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();

            // Manejo de la imagen
            Bitmap picture = (Bitmap) data.getExtras().get("data");
            binding.ivPicProfile.setImageBitmap(picture);
        }
    });

    //Funcion para abrir la camara y tomar la captura
    public void openCameraToPic() {
        binding.ivPicProfile.setOnClickListener(v -> {
            Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLancherCamara.launch(openCamera);
        });

    }

    public void listeners() {

        // Button Register
        binding.bRegister.setOnClickListener(v -> {

            if (validateFields()) {

                User user = new User(
                        String.valueOf(binding.tietUsername.getText()).trim(),
                        String.valueOf(binding.tietEmail.getText()).trim(),
                        String.valueOf(binding.tietPassword.getText()).trim(),
                        String.valueOf(binding.actvAge.getText()).trim());

                // Enviar los datos al apartado del login
                sendDataUser(user);
            }
            else {
                Snackbar.make(binding.getRoot(), getString(R.string.errorEmptyRegister), Snackbar.LENGTH_LONG).show();
            }

        });

        // Validaciones de los datos del usuario a tiempo real
        // Validacion del nombre de usuario
        binding.tietUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern pattern = Pattern.compile("[a-zA-Z]+");

                if (isFieldEmpty(s, binding.tilUsername))
                    return;

                if (!pattern.matcher(String.valueOf(binding.tietUsername.getText()).trim()).matches()) {
                    binding.tilUsername.setError(getString(R.string.errorEmail));
                } else {
                    binding.tilUsername.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Validacion del email
        binding.tietEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (isFieldEmpty(String.valueOf(binding.tietEmail.getText()).trim(), binding.tilUsername))
                        return;

                    if (!Patterns.EMAIL_ADDRESS.matcher(String.valueOf(binding.tietEmail.getText()).trim()).matches()) {
                        binding.tilEmail.setError(getString(R.string.errorEmail));
                    } else {
                        binding.tilEmail.setError(null);
                    }

                }
            }
        });

        // Validacion de contraseña
        binding.tietPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isFieldEmpty(s, binding.tilPassword))
                    return;

                if (s.toString().length() < 5) {
                    binding.tilPassword.setError(getString(R.string.errorCharacterers));
                } else {
                    binding.tilPassword.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addTextWatcher();

        // Validacion de años
        binding.actvAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isFieldEmpty(s, binding.tietAge))
                    return;

                if (!s.toString().equals("+18")) {
                    binding.tietAge.setError(getString(R.string.errorAge));
                    binding.bRegister.setEnabled(false);
                } else {
                    binding.tietAge.setError(null);
                    binding.bRegister.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void addTextWatcher(){
        TextWatcher textsWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean isEnabled = !String.valueOf(binding.tietUsername.getText()).trim().isEmpty()
                        && !String.valueOf(binding.tietEmail.getText()).trim().isEmpty()
                        && !String.valueOf(binding.tietPassword.getText()).trim().isEmpty()
                        && !String.valueOf(binding.actvAge.getText()).trim().isEmpty();

                binding.bRegister.setEnabled(isEnabled);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.tietUsername.addTextChangedListener(textsWatcher);
        binding.tietEmail.addTextChangedListener(textsWatcher);
        binding.tietPassword.addTextChangedListener(textsWatcher);
        binding.actvAge.addTextChangedListener(textsWatcher);

    }

    // Validar si el campo de una casilla esta vacio para mostrar el error
    public boolean isFieldEmpty(CharSequence text, TextInputLayout tiLayout) {
        if (text.toString().trim().isEmpty()) {
            tiLayout.setError(getString(R.string.errorEmptyInput));
            return true;
        } else {
            tiLayout.setError(null);
            return false;
        }
    }

    public boolean validateFields() {

        return binding.tilUsername.getError() == null && binding.tilEmail.getError() == null
                && binding.tilPassword.getError() == null && binding.tietAge.getError() == null
                && !String.valueOf(binding.tietUsername.getText()).trim().isEmpty()
                && !String.valueOf(binding.tietEmail.getText()).trim().isEmpty()
                && !String.valueOf(binding.tietPassword.getText()).trim().isEmpty()
                && !String.valueOf(binding.actvAge.getText()).trim().isEmpty();
    }

    // Funcion que abarca lo necesario para ir al login
    public void sendDataUser(User user) {

        // Ir al Login nuevamente con los datos que se introdujo
        Bundle userData = new Bundle();
        userData.putParcelable("user", user);

        // Enviar el resultado al otro fragment
        getParentFragmentManager().setFragmentResult("userDataFromRegister", userData);

        Log.d("Depurando", "Datos Enviados al Login: Username: " + user.getUserName() + ", Email: " + user.getUserEmail() + ", Password: "
                + user.getUserPassword() + ", Edad: " + user.getUserAge());

        ((LoginActivity) getActivity()).backToLoginActivity();
    }
}