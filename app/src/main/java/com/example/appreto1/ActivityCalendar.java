package com.example.appreto1;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();
        String mensaje = intent.getStringExtra(EXTRA_MESSAGE);

        TextView bienvenida = findViewById(R.id.textviewBienvenida);
        bienvenida.setText("Bienvenido "+mensaje);

    }
}