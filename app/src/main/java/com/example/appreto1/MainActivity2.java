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


        //firebase database initialise
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //arraylist para guardar eventos de la base de datos



      //listener que activa la recoleccion de datos de la base de datos

        //sacar documentos fin



        calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection(usuario).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //pasar datos a la clase Evento
                                Evento evento = document.toObject(Evento.class);
                                //guardar Evento en arraylist
                                eventos.add(evento);


                            }
                            Intent cambio = new Intent(MainActivity2.this, MainActivityCalendarioEvento.class);
                            cambio.putExtra("usuario", usuario);
                            cambio.putExtra("lista", (Serializable) eventos);
                            startActivity(cambio);
                        }

                    }

                });
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