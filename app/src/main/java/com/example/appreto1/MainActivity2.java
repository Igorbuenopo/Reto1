package com.example.appreto1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Entidades.Evento;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<Evento> eventos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //recoger nombre de usuario del intent anterior
        Bundle bundle = getIntent().getExtras();
        String usuario = bundle.getString("usuario");


        CardView calc = findViewById(R.id.cardCalcu);
        CardView calen = findViewById(R.id.cardCalend);
        CardView contact = findViewById(R.id.cardContact);
        CardView salir = findViewById(R.id.cardLogout);
        CardView nota = findViewById(R.id.cardNota);



        //boton que lleva al bloc de notas
        nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambio = new Intent(MainActivity2.this, ActivityNotas.class);
                cambio.putExtra("usuario", usuario);
                startActivity(cambio);
            }
        });

        //boton que lleva al calendario
        calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cambio = new Intent(MainActivity2.this, MainActivityCalendarioEvento.class);
                cambio.putExtra("usuario", usuario);
                startActivity(cambio);
            }
        });

        //boton que lleva a la calculadora
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, ActivityCalcNote.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        //boton que leva a la lista de contactos
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, ActivityContactos.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        //boton que cierra sesion
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}