package com.example.appreto1;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreto1.databinding.ActivityCreateUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
// ...
// Initialize Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //sharedPreferences crear
        Context context = MainActivity.this;
        sharedpreferences = context.getSharedPreferences(
                getString(R.string.preference_email_user), Context.MODE_PRIVATE);

        //sharedPreferences leer
        String defaultUser = getResources().getString(R.string.preference_email_user);
        String rememberUser = sharedpreferences.getString(getString(R.string.preference_email_user), defaultUser);


        //firebase
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //valores
        EditText contrasenna = (EditText) findViewById(R.id.editTextContrasenna);
        Button confirmar = (Button) findViewById(R.id.buttonConfirmar);
        Button privado = (Button) findViewById(R.id.ButtonPrivado);
        Button registrar = (Button) findViewById(R.id.buttonRegistrarse);
        EditText txtUsuario = (EditText) findViewById(R.id.TextImputNombre);
        //sharedPreferences colocal leido
        txtUsuario.setText(rememberUser);




        //Iniciar sesion
         confirmar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String email = txtUsuario.getText().toString();
                 String password = contrasenna.getText().toString();
                 if(email.isEmpty() || password.isEmpty()){
                     AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                     builder.setTitle("Login");

                     builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                         // boton de aceptar y cerrar pop-up

                         dialog.cancel();
                     });

                     builder.setMessage("Usuario o contraseña vacios");
                     AlertDialog dialog = builder.create();
                     dialog.show();

                     return;
                 }

                 mAuth.signInWithEmailAndPassword(email, password)
                         .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     // Sign in success, update UI with the signed-in user's information
                                     Log.d(TAG, "signInWithEmail:success");
                                     FirebaseUser user = mAuth.getCurrentUser();

                                     //intent
                                     Intent cambio = new Intent(MainActivity.this, MainActivity2.class);
                                     String usuario = email;
                                     cambio.putExtra("usuario", usuario);
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
                //sharedPreferences escribir
                 SharedPreferences.Editor editor = sharedpreferences.edit();
                 editor.putString(getString(R.string.preference_email_user), email);
                 editor.apply();
             }

        });

         //entrar como privado
        privado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAuth.signInAnonymously()
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInAnonymously:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //intent
                                    Intent cambio = new Intent(MainActivity.this, MainActivity2.class);
                                    String mensaje = "Invitado";
                                    cambio.putExtra("usuario", mensaje);
                                    startActivity(cambio);

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                                    builder.setTitle("Error login privado");

                                    builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                                        // boton de aceptar y cerrar pop-up

                                        dialog.cancel();
                                    });

                                    builder.setMessage("Error al intentar iniciar sesion como invitado");
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }
                            }
                        });
            }

        });

        //crear usuario
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambio = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(cambio);
            }

        });



    };



}