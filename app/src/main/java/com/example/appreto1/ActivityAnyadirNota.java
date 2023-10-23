package com.example.appreto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;

import Entidades.Nota;

public class ActivityAnyadirNota extends AppCompatActivity {

    EditText tituloNota;
    EditText contenidoNota;

    FloatingActionButton atrasnota;

    Button anyadir;
    Button eliminarNota;

    FirebaseFirestore db;
    TextView pagTit;

    String titulo, contenido, docId;
    boolean modoEditar = false;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_nota);

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("usuario");

        pagTit = findViewById(R.id.tvanyadirnota);
        tituloNota = findViewById(R.id.edTituloNota);
        contenidoNota = findViewById(R.id.edContenidoNota);

        atrasnota = findViewById(R.id.btnatrasnota);
        anyadir = findViewById(R.id.btnguardarnota);
        eliminarNota = findViewById(R.id.btneliminarNota);

        titulo = getIntent().getStringExtra("titulo");
        contenido = getIntent().getStringExtra("contenido");
        docId = getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            modoEditar = true;
        }
        tituloNota.setText(titulo);
        contenidoNota.setText(contenido);

        if(modoEditar){
            pagTit.setText("Editar nota");
            eliminarNota.setVisibility(View.VISIBLE);
        }

        anyadir.setOnClickListener((v) -> guardarNota());
        atrasnota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityAnyadirNota.this, ActivityNotas.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });

        eliminarNota.setOnClickListener((v) -> eliminarnotaFirestore());
    }

    void guardarNota() {
        String titNota = tituloNota.getText().toString();
        String contNota = contenidoNota.getText().toString();

        if (titNota == null || titNota.isEmpty()) {
            tituloNota.setError("Título vacío");
            return;
        }

        Nota n = new Nota(titNota, contNota, fechaHoy());
        guardarNotaFirestore(n);

    }

    private String fechaHoy() {
        String fechaHoy = "";
        LocalDate date = LocalDate.now();
        fechaHoy = date.getDayOfMonth()+"/"+date.getMonth().getValue()+"/"+date.getYear();
        return fechaHoy;
    }

    private void guardarNotaFirestore(Nota n) {
        DocumentReference dr;
        db = FirebaseFirestore.getInstance();
        if(modoEditar){
            dr = db.collection(usuario+"notas").document(docId);
        }else{
            dr = db.collection(usuario+"notas").document();
        }

        dr.set(n).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    Intent i = new Intent(ActivityAnyadirNota.this, ActivityNotas.class);
                    i.putExtra("usuario", usuario);
                    startActivity(i);

                }
            }
        });

    }

    void eliminarnotaFirestore() {
        DocumentReference dr;
        db = FirebaseFirestore.getInstance();
        dr = db.collection(usuario+"notas").document(docId);
        dr.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(ActivityAnyadirNota.this, ActivityNotas.class);
                    i.putExtra("usuario", usuario);
                    startActivity(i);
                }
            }
        });
    }
}