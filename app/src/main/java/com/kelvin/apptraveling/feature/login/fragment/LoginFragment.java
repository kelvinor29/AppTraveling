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
import android.text.Editable;
import android.text.TextWatcher;
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
    PendingIntent pendingIntent;

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

        // Solicitud de permiso de notificaciones
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);

        }
    }

    // Recupera el dato User del bundle de la clave "user"
    public void recoverData() {
        getParentFragmentManager().setFragmentResultListener("userDataFromRegister", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Recupera el objeto User
                user = result.getParcelable("user");

                if (user != null) {
                    Snackbar.make(binding.getRoot(), "Name: " + user.getUserName() + ", Email: " + user.getUserEmail() +
                            ", Password: " + user.getUserPassword() + ", Age: " + user.getUserAge(), Snackbar.LENGTH_LONG).show();

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
                } else {
                    Snackbar.make(binding.getRoot(), "No user found, you must register first", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // Mostrar mensajes "Próximamente"
        View.OnClickListener showComingSoonMessage = v -> Snackbar.make(binding.getRoot(), "Comming Soon...", Snackbar.LENGTH_SHORT).show();
        binding.tvForgetPassword.setOnClickListener(showComingSoonMessage);
        binding.btnGoogle.setOnClickListener(showComingSoonMessage);
    }

    public boolean isCorrectUser() {

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

        // Notificacion de bienvenida
        ShowNotification();

        ((LoginActivity) getActivity()).changeActivity(new HomeActivity());

        Log.d("Depurando", "Data sent to Login Fragment: Username: " + user.getUserName() + ", Password: " + user.getUserPassword());
    }

    // Mostar la notificacion de Bienvenida
    private void ShowNotification() {

        // Validacion si el permiso de notificaciones esta aceptada
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.i("Information", "Permission Granted");

            // Validacion si la version es compatible, android SDK 26 o superior
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Log.i("Information", "Version correcta");

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
                        .setContentTitle("Welcome " + user.getUserName() + "!!")
                        .setContentText("We are glad to see you in this paradise.") //"Nos alegra verte en este paraíso."
                        .setSmallIcon(R.drawable.ic_star_rate)
                        .setContentIntent(pendingIntent)
                        .setLargeIcon(imgBitmap);

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