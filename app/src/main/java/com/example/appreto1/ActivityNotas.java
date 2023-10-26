package com.example.appreto1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Entidades.Nota;

public class ActivityNotas extends AppCompatActivity {

    FloatingActionButton anyadirNota;
    SearchView buscar;
    FloatingActionButton atrasNotas, calcu;
    RecyclerView recyclerNota;
    FirebaseFirestore db;
    NoteAdapter noteAdapter;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        //recogemos el usuario
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("usuario");

        recyclerNota = findViewById(R.id.recyclerNotas);
        buscar = findViewById(R.id.buscarNota);

        anyadirNota = findViewById(R.id.btnanyadirnota);
        calcu = findViewById(R.id.btnnotacalcu);

        //boton que va a la calculadora
        calcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityNotas.this, ActivityCalcNote.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });
        //boton que va al activity de añadir nota
        anyadirNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityNotas.this, ActivityAnyadirNota.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });




        atrasNotas = findViewById(R.id.btnnotasatras);
        //boton de atras que vuelve al menu
        atrasNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityNotas.this, MainActivity2.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });

        //setea el recycler y el searchview
        setupRecyclerView();
        buscarView();
    }

    //seteamos el recycler con las notas del usuario, ordenadas segun su fecha
    void setupRecyclerView(){
        db = FirebaseFirestore.getInstance();
        Query query  = db.collection(usuario+"notas").orderBy("time",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> options = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query,Nota.class).build();
        recyclerNota.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, usuario, this);
        recyclerNota.setAdapter(noteAdapter);
    }

    //funcion del searchview, tanto como entras en el search, como cuando cambias el texto de el
    void buscarView() {
        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });

    }

    //funcion del search que busca solo las notas, con lo que ponga
    public void textSearch(String s){
        Query query  = db.collection(usuario+"notas");
        FirestoreRecyclerOptions<Nota> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Nota>()
                        .setQuery(query.orderBy("titulo")
                                .startAt(s).endAt(s+"~"), Nota.class).build();
        noteAdapter = new NoteAdapter(firestoreRecyclerOptions, usuario, this);
        noteAdapter.startListening();
        recyclerNota.setAdapter(noteAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }
}
