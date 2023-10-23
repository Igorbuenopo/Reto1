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
    FloatingActionButton atrasNotas;
    RecyclerView recyclerNota;
    FirebaseFirestore db;
    NoteAdapter noteAdapter;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("usuario");

        recyclerNota = findViewById(R.id.recyclerNotas);
        buscar = findViewById(R.id.buscarNota);

        anyadirNota = findViewById(R.id.btnanyadirnota);
        anyadirNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityNotas.this, ActivityAnyadirNota.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });




        atrasNotas = findViewById(R.id.btnnotasatras);
        atrasNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityNotas.this, MainActivity2.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });


        setupRecyclerView();
        buscarView();
    }

    void setupRecyclerView(){
        db = FirebaseFirestore.getInstance();
        Query query  = db.collection(usuario+"notas").orderBy("time",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Nota> options = new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query,Nota.class).build();
        recyclerNota.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options,this);
        recyclerNota.setAdapter(noteAdapter);
    }

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

    public void textSearch(String s){
        Query query  = db.collection(usuario+"notas");
        FirestoreRecyclerOptions<Nota> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Nota>()
                        .setQuery(query.orderBy("titulo")
                                .startAt(s).endAt(s+"~"), Nota.class).build();
        noteAdapter = new NoteAdapter(firestoreRecyclerOptions, this);
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
