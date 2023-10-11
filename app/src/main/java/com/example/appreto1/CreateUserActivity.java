package com.example.appreto1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import Entidades.Evento;
import Entidades.Nota;

public class CreateUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; //Inciar firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.d(TAG, "incioclaseregistrar");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        //valores
        EditText contrasenna = (EditText) findViewById(R.id.viewPass);
        Button confirmar = (Button) findViewById(R.id.buttonConfirmarCrear);
        EditText usuario = (EditText) findViewById(R.id.viewUsuario);

        //listener para el boton de crear usuario
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = usuario.getText().toString();
                String password = contrasenna.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);


                            builder.setTitle("Usuario creado");

                            builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                                // boton de aceptar y cerrar pop-up

                                dialog.cancel();
                            });

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Evento evento = new Evento();
                            evento.setTitulo("1");
                            db.collection(email).document("Evento"+1).set(evento);

                            Nota nota = new Nota();
                            db.collection(email.concat(".notas")).document("Nota"+1).set(nota);

                            builder.setMessage("Enhorabuena, ya es usted parte de (nombre de app)");
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            Intent cambio = new Intent(CreateUserActivity.this, MainActivity.class);
                            startActivity(cambio);

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);


                            builder.setTitle("Error Registrar");

                            builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                                // boton de aceptar y cerrar pop-up

                                dialog.cancel();
                            });

                            builder.setMessage("Error al intentar crear un usuario, porfavor inserte un email y una contraseña de al menos 6 caracteres");
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                });

            }
        });

    };

}