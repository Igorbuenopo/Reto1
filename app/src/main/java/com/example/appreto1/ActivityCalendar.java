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

        //Bundle bundle = getIntent().getExtras();
        //String mensaje = bundle.getString("mensaje");

        //TextView bienvenida = findViewById(R.id.textviewBienvenida);
        //bienvenida.setText(mensaje);

    }
}