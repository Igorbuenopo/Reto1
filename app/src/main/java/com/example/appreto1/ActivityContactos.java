package com.example.appreto1;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Entidades.Contacto;

public class ActivityContactos extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnVolver;

    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        recyclerView = findViewById(R.id.recyclerViewContact);
        btnVolver = findViewById(R.id.btnVolverFlecha);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityContactos.this, MainActivity2.class);
                startActivity(i);
            }
        });
        setupRecyclerView();

    }

     void setupRecyclerView() {
         Query query = FirebaseFirestore.getInstance().collection("Contactos");
          FirestoreRecyclerOptions<Contacto> options = new FirestoreRecyclerOptions.Builder<Contacto>().setQuery(query,Contacto.class).build();
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
          contactAdapter = new ContactAdapter(options,this);
         recyclerView.setAdapter(contactAdapter);
        Log.d(TAG, "tamaño del array: " +options.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        contactAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        contactAdapter.stopListening();
    }

/*
    @Override
    protected void onResume() {
        super.onResume();
        contactAdapter.notifyDataSetChanged();
    }
*/

}
