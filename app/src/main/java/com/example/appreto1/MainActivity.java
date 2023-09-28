package com.example.appreto1;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        EditText usuario = (EditText) findViewById(R.id.TextImputNombre);
        EditText contrasenna = (EditText) findViewById(R.id.editTextContrasenna);

        Button confirmar = (Button) findViewById(R.id.buttonConfirmar);




         confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = usuario.getText().toString();
                String password = contrasenna.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //intent
                                    Intent cambio = new Intent (MainActivity.this, ActivityCalendar.class);
                                    String mensaje = email;
                                    cambio.putExtra( "mensaje", mensaje);
                                    startActivity(cambio);

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                                    builder.setTitle("Login");

                                    builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                                        // boton de aceptar y cerrar pop-up

                                        dialog.cancel();
                                    });

                                    builder.setMessage("Usuario o contraseña incorrecta");
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }
                            }
                        });
            }
        });
    };

}