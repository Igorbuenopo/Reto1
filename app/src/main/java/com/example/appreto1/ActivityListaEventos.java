package com.example.appreto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

import Entidades.Evento;

public class ActivityListaEventos extends AppCompatActivity implements EventAdapter.OnLongItemListener {


    int ano, mes, dia;
    String usuario;


    TextView tvEventoFech;

    FloatingActionButton atras, anyadir;
    ArrayList<Evento> listaEventos = new ArrayList<Evento>();
    ArrayList<Evento> listaEventosDia = new ArrayList<Evento>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerEvent;
    EventAdapter eventAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);


        Bundle bundle = getIntent().getExtras();
        listaEventos = (ArrayList<Evento>) bundle.getSerializable("lista");
        ano = bundle.getInt("año");
        mes = bundle.getInt("mes");
        dia = bundle.getInt("dia");
        usuario = bundle.getString("usuario");
        //get los extras para ir rellenando la fecha, la ponemos en un texto para que el usuario previsualice
        tvEventoFech = findViewById(R.id.tveventos);
        String eventoFech = tvEventoFech.getText().toString() + "\r\n" + dia + "/" + mes + "/" + ano;
        tvEventoFech.setText(eventoFech);


        conseguirEventosDia(listaEventos);


        atras = findViewById(R.id.btneventosatras);
        anyadir = findViewById(R.id.btnanyadirevento);
        recyclerEvent = findViewById(R.id.recyclerEvent);


        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityListaEventos.this, ActivityEvent.class);
                i.putExtra("dia", dia);
                i.putExtra("mes", mes);
                i.putExtra("año", ano);
                i.putExtra("usuario", usuario);
                i.putExtra("lista", (Serializable) listaEventos);
                startActivity(i);
            }
        });


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityListaEventos.this, MainActivityCalendarioEvento.class);
                i.putExtra("usuario", usuario);
                i.putExtra("lista", (Serializable) listaEventos);
                startActivity(i);
            }
        });
        setupRecyclerView();
        ;
    }


    void conseguirEventosDia(ArrayList<Evento> listaEventos) {
        for (int i = 0; i < listaEventos.size(); i++) {
            int diaE = listaEventos.get(i).getDia();
            int mesE = listaEventos.get(i).getMes();
            int anyoE = listaEventos.get(i).getAno();


            if (diaE == dia && mesE == mes && anyoE == ano) {
                listaEventosDia.add(listaEventos.get(i));
            }
        }
    }


    void setupRecyclerView() {
        recyclerEvent.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(listaEventosDia, usuario, this);
        recyclerEvent.setAdapter(eventAdapter);
    }


    @Override
    public void OnItemLongClick(View v, String titulo) {
        Log.d("mensaje", "holaa");
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaEventos.this);


        builder.setTitle("Login");


        builder.setPositiveButton("Si", (DialogInterface.OnClickListener) (dialog, which) -> {
            // boton de aceptar y cerrar pop-up
            db.collection(usuario).whereEqualTo("titulo", titulo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot docu : task.getResult()) {
                            db.collection(usuario).document(docu.getId()).delete();
                        }
                    }
                    Intent i = new Intent(ActivityListaEventos.this, MainActivityCalendarioEvento.class);
                    i.putExtra("usuario", usuario);
                    startActivity(i);
                }


            });


            dialog.cancel();
        });


        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // boton de aceptar y cerrar pop-up


            dialog.cancel();
        });


        builder.setMessage("¿Quieres borrar este evento?");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
