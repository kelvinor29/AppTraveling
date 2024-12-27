package com.kelvin.apptraveling.feature.login.fragment;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.kelvin.apptraveling.R;
import com.kelvin.apptraveling.data.api.API;
import com.kelvin.apptraveling.data.model.User;
import com.kelvin.apptraveling.data.model.UserApi;
import com.kelvin.apptraveling.databinding.FragmentLoginBinding;
import com.kelvin.apptraveling.feature.home.activity.HomeActivity;
import com.kelvin.apptraveling.feature.login.activity.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private User user;
    PendingIntent pendingIntent;

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

        // Solicitud de permiso de notificaciones
        requestNotificationPermission();
    }

    public void requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);

        }
    }

    // Recupera el dato User del bundle de la clave "user"
    private void recoverData() {
        getParentFragmentManager().setFragmentResultListener("userDataFromRegister", this, (requestKey, result) -> {
            // Recupera el objeto User
            user = result.getParcelable("user");

            if (user != null) {
                Snackbar.make(binding.getRoot(), "User registered: " + user.getUserName(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void setupListeners() {
        // Lanzar al Register Fragment
        binding.tvRegisterNow.setOnClickListener(v -> ((LoginActivity) getActivity()).nextFragment(new RegisterFragment()));

        // Lanzar al Home Activity
        binding.bLogIn.setOnClickListener(v -> {
            attemptLogin();

        });

        // Mostrar mensajes "Próximamente"
        View.OnClickListener showComingSoonMessage = v -> Snackbar.make(binding.getRoot(), "Comming Soon...", Snackbar.LENGTH_SHORT).show();
        binding.tvForgetPassword.setOnClickListener(showComingSoonMessage);
        binding.btnGoogle.setOnClickListener(showComingSoonMessage);
    }

    private void attemptLogin() {

        String usernameTyped = String.valueOf(binding.tietUsernameTyped.getText()).trim();
        String passwordTyped = String.valueOf(binding.tietPasswordTyped.getText()).trim();

        if (user != null && isValidUser(user, usernameTyped, passwordTyped)) {
            sendUserData(user);
        } else {
            fetchUserFromApi(usernameTyped, passwordTyped);
        }
    }

    private boolean isValidUser(User user, String usernameTyped, String passwordTyped) {
        return usernameTyped.equals(user.getUserName()) && passwordTyped.equals(user.getUserPassword());
    }

    private boolean isValidUserApi(UserApi userApi, String usernameTyped, String passwordTyped) {
        Log.d("user", userApi.toString());
        Log.d("user", String.format("%s - %s", usernameTyped, passwordTyped));

        return usernameTyped.equals(userApi.getUsername()) && passwordTyped.equals(userApi.getAge().toString());

    }


    // Peticion de datos del usuario desde la API
    private void fetchUserFromApi(String username, String password) {
        API api = new API(API.BASE_URL);
        Call<UserApi> call = api.service.login(username, password);

        call.enqueue(new retrofit2.Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {

                if (response.isSuccessful() && response.body() != null) {
                    UserApi userApi = response.body();

                    // Validar las credenciales con el objeto recibido si no para realizar el login correctamente
                    if (!isValidUserApi(userApi, username, password)) {
                        Snackbar.make(binding.getRoot(), getString(R.string.errorPasswordLogIn), Snackbar.LENGTH_LONG).show();
                    } else {
                        sendUserApiData(userApi);
                    }

                } else {
                    Snackbar.make(binding.getRoot(), "Error: " + response.code() + " - " + response.message(), Snackbar.LENGTH_LONG).show();
                    Log.e("API Error", "Error code: " + response.code() + ", message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {
                Snackbar.make(binding.getRoot(), "Error connecting to server: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.e("Network Error", t.getMessage(), t);
            }
        });
    }


    private void sendUserApiData(UserApi userApi) {
        Bundle data = new Bundle();
        data.putSerializable("userApi", userApi);
        getParentFragmentManager().setFragmentResult("userDataFromLogin", data);

        showWelcomeNotification(userApi);
        navigateToHome();
    }

    private void navigateToHome() {
        ((LoginActivity) getActivity()).changeActivity(new HomeActivity());
    }


    //Enviar los datos de usuario a la home
    private void sendUserData(User user) {
        // Ir a la home  con los datos que se introdujo
        Bundle userData = new Bundle();
        userData.putParcelable("user", user);

        getParentFragmentManager().setFragmentResult("userDataFromLogin", userData);

        // Notificacion de bienvenida
        showWelcomeNotification(null);

        navigateToHome();

        Log.d("Depurando", "Data sent to Login Fragment: Username: " + user.getUserName() + ", Password: " + user.getUserPassword());
    }

    // Mostar la notificacion de Bienvenida
    private void showWelcomeNotification(UserApi userApi) {

        // Validacion si el permiso de notificaciones esta aceptada
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.i("Information", "Permission Granted");

            // Validacion si la version es compatible, android SDK 26 o superior
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                String id = "WelcomeChOne";

                //NotificationManager
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireActivity());

                //Canal de notificaciones
                NotificationChannel channel = new NotificationChannel(id, "WelcomeChanel", NotificationManager.IMPORTANCE_HIGH);
                channel.enableVibration(true);

                notificationManagerCompat.createNotificationChannel(channel);

                Bitmap imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_tokio_school);

                // Abrir nuevamente a la Home Activity por la notificacion recibida
                OpenHomeByNotification();


                //Crear notificación
                NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(requireActivity(), id)
                        .setContentText("We are glad to see you in this paradise.") //"Nos alegra verte en este paraíso."
                        .setSmallIcon(R.drawable.ic_star_rate)
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(imgBitmap);
                if (userApi != null) {
                    noBuilder.setContentTitle("Welcome " + userApi.getUsername() + "!!");

                } else {
                    noBuilder.setContentTitle("Welcome " + user.getUserName() + "!!");
                }


                notificationManagerCompat.notify(1, noBuilder.build());
            }
        }
    }

    // Abrir aplicacion por medio de la notificacion
    private void OpenHomeByNotification() {

        Intent intent = new Intent(requireActivity(), HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(requireActivity());
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(requireActivity(), 0, intent, PendingIntent.FLAG_MUTABLE);
        }
    }
}