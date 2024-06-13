package com.kelvin.apptraveling.feature.login.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.databinding.FragmentRegisterBinding;
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private static final int pic_id = 1;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

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

        // Añadir TextWatchers para el manejo de cambios en el textInputEditTexts
        binding.tietUsername.addTextChangedListener(textWatcher());
        binding.tietEmail.addTextChangedListener(textWatcher());
        binding.tietPassword.addTextChangedListener(textWatcher());
        binding.actvAge.addTextChangedListener(textWatcher());

    }

    // Este es un ejemplo del manejo del menú de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            // Maneja el evento del botón de retroceso
            ((LoginActivity) getActivity()).backToLoginActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toolBarFunctions() {
        // Toolbar (Up Button)
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

    //Funcion para abrir la camara y tomar la captura
    public void openCameraToPic() {
        binding.ivPicProfile.setOnClickListener(v -> {
            Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(openCamera, pic_id);
        });

    }

    // Agregar foto al ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_id) {
            Bitmap picture = (Bitmap) data.getExtras().get("data");
            binding.ivPicProfile.setImageBitmap(picture);
        }
    }

    public void listeners() {

        // Button Register
        binding.bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recupera los datos de los EditText en el momento del click
                String textPassword = binding.tietPassword.getText().toString();
                String textUsername = binding.tietUsername.getText().toString();
                String textEmail = binding.tietEmail.getText().toString();
                String textAge = binding.actvAge.getText().toString();

                sendDataUser(textUsername, textEmail, textPassword, textAge);
            }
        });

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
                validateFields();
            }
        };

    }

    public void validateFields() { // Activar el button de register para avanzar con el logueo

        // Recuperar el array string de strings.xml
        String[] ageArray = getResources().getStringArray(R.array.ages);
        // Crear adapter con los datos de el array string
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ageArray);
        // Asigar el adapter al AutoCompleteTextView
        binding.actvAge.setAdapter(adapter);

        // Verificar que todos los campos estén correctamente llenos
        boolean isUsernameValid = binding.tietUsername.getText().toString().matches("[a-z]+");
        boolean isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(binding.tietEmail.getText().toString().trim()).matches();
        boolean isPasswordValid = binding.tietPassword.getText().toString().length() >= 5;
        boolean isAgeValid = binding.actvAge.getText().toString().equals("+18");

        // Establecer errores si no son válidos
        binding.tilUsername.setError(isUsernameValid ? null : getString(R.string.errorUsername));
        binding.tietEmail.setError(isEmailValid ? null : "Formato de correo electrónico inválido");
        binding.tilPassword.setError(isPasswordValid ? null : "Debe de contener al menos 5 carácteres");
        binding.actvAge.setOnItemClickListener(((parent, view, position, id) -> {
            String selectedAge = ageArray[position];

            binding.tietAge.setError(Objects.equals(selectedAge, ageArray[3]) ? null : "Ups, esta App no es para ti!");

        }));

        // Habilitar o deshabilitar el button
        binding.bRegister.setEnabled(isUsernameValid && isEmailValid && isPasswordValid && isAgeValid);

    }

    public void sendDataUser(String textUsername, String textEmail, String textPassword, String textAge) { // Funcion que abarca lo necesario para ir al login

        // Ir al Login nuevamente con los datos que se introdujo
        Bundle userData = new Bundle();
        userData.putString("username", textUsername);
        userData.putString("email", textEmail);
        userData.putString("password", textPassword);
        userData.putString("age", textAge);

        getParentFragmentManager().setFragmentResult("userDataFromRegister", userData);

        Log.d("Depurando", "Datos Enviados al Login: Username: " + textUsername + ", Email: " + textEmail + ", Password: " + textPassword + ", Edad: " + textAge);

        ((LoginActivity) getActivity()).backToLoginActivity();
    }
}