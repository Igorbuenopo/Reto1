package com.example.appreto1;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usuario = (EditText)findViewById(R.id.TextImputNombre);
        EditText contrasenna = (EditText)findViewById(R.id.editTextContrasenna);
        Button confirmar = (Button)findViewById(R.id.buttonConfirmar);



        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        builder.setTitle("Login");

        builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // boton de aceptar y cerrar pop-up

                    dialog.cancel();
                });



        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario.getText().toString().equals("admin") && contrasenna.getText().toString().equals("1234")) {

                    Intent cambio = new Intent (MainActivity.this, ActivityCalendar.class);

                    String mensaje = usuario.getText().toString();
                    cambio.putExtra(EXTRA_MESSAGE, mensaje);

                    startActivity(cambio);


                } else {

                    builder.setMessage("Usuario o contraseña incorrecta");
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }
}