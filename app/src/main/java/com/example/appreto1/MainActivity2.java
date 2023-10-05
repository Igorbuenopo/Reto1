package com.example.appreto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

<<<<<<< HEAD
=======
        Button calendar = findViewById(R.id.idButCalendario);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, ActivityFechas.class);
                startActivity(i);
>>>>>>> 4240248e2af75d40917b99e13af957d5e4035c3d

    }
}