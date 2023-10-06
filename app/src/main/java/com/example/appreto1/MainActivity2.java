package com.example.appreto1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        String usuario = bundle.getString("usuario");

        CardView calc = findViewById(R.id.cardCalcu);
        CardView calen = findViewById(R.id.cardCalend);

        calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity2.this,MainActivityCalendarioEvento.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, ActivityCalcNote.class);

                startActivity(i);
            }
        });

    }
}