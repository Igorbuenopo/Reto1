package com.example.appreto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    //boleano para saber si está en modo editar
    boolean modoEditar = false;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_nota);

        //recogemos el usuario
        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("usuario");

        //seteamos los botones, editText y TextView
        pagTit = findViewById(R.id.tvanyadirnota);
        tituloNota = findViewById(R.id.edTituloNota);
        contenidoNota = findViewById(R.id.edContenidoNota);

        atrasnota = findViewById(R.id.btnatrasnota);
        anyadir = findViewById(R.id.btnguardarnota);
        eliminarNota = findViewById(R.id.btneliminarNota);

        //recogemos el titulo, contenido y id de la base de datos de la nota
        titulo = getIntent().getStringExtra("titulo");
        contenido = getIntent().getStringExtra("contenido");
        docId = getIntent().getStringExtra("docId");

        //si el id de la nota no está vacío o no es nulo, significa que estamo en editar
        if(docId!=null && !docId.isEmpty()){
            modoEditar = true;
        }
        tituloNota.setText(titulo);
        contenidoNota.setText(contenido);

        //para traducir en ingles, el editar nota
        if(modoEditar){
            Log.d("yow", pagTit.getText().toString());
            if(pagTit.getText().toString().equals("Añadir Nota")){
                pagTit.setText("Editar Nota");
            }else{
                pagTit.setText("Edit Note");
            }

            eliminarNota.setVisibility(View.VISIBLE);
        }

        //boton añadir o editar nota
        anyadir.setOnClickListener((v) -> guardarNota());
        //botón atras
        atrasnota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityAnyadirNota.this, ActivityNotas.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
            }
        });

        //boton para eliminar nota
        eliminarNota.setOnClickListener((v) -> eliminarnotaFirestore());
    }

    //funcion guardar nota
    void guardarNota(){
        String titNota = tituloNota.getText().toString();
        String contNota = contenidoNota.getText().toString();

        //comprueba que el titulo no está vacio
        if (titNota == null || titNota.isEmpty()) {
            tituloNota.setError("Título vacío");
            return;
        }

        Nota n = new Nota(titNota, contNota, fechaHoy());
        guardarNotaFirestore(n);

    }

    //funcion para recoger la fecha de hoy
    private String fechaHoy() {
        String fechaHoy = "";
        LocalDate date = LocalDate.now();
        fechaHoy = date.getDayOfMonth()+"/"+date.getMonth().getValue()+"/"+date.getYear();
        return fechaHoy;
    }

    //guardar nota firestore
    private void guardarNotaFirestore(Nota n) {
        //no deja si esta en modo invitado
        if(usuario.equals("Invitado")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAnyadirNota.this);


            builder.setTitle("Error Invitado");

            builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                // boton de aceptar y cerrar pop-up

                dialog.cancel();
            });

            builder.setMessage("Create una cuenta para añadir notas.");
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            DocumentReference dr;
            db = FirebaseFirestore.getInstance();
            //comprueba si estamos en modo editar o no
            if(modoEditar){
                dr = db.collection(usuario+"notas").document(docId);
            }else{
                dr = db.collection(usuario+"notas").document();
            }

            //mete la nota en la base de datos y nos lleva a la lista de notas
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


    }

    void eliminarnotaFirestore() {
        if(usuario.equals("Invitado")){
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAnyadirNota.this);


            builder.setTitle("Error Invitado");

            builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                // boton de aceptar y cerrar pop-up

                dialog.cancel();
            });

            builder.setMessage("Create una cuenta para eliminar notas.");
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            DocumentReference dr;
            db = FirebaseFirestore.getInstance();
            dr = db.collection(usuario + "notas").document(docId);
            dr.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(ActivityAnyadirNota.this, ActivityNotas.class);
                        i.putExtra("usuario", usuario);
                        startActivity(i);
                    }
                }
            });
        }
    }
}