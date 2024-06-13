package com.kelvin.apptraveling.feature.boarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kelvin.apptraveling.feature.login.activity.LoginActivity;
import com.kelvin.apptraveling.R;

public class BoardingOneActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boarding1);

        ((Button) findViewById(R.id.b_skip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardingOneActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });

        // Ir al login Activity
        ((Button) findViewById(R.id.b_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardingOneActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });

    }
}