package com.example.appreto1;

import android.content.DialogInterface;
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

                    builder.setMessage("Es admin");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setLayout(1000, 1600);

                } else {

                    builder.setMessage("Usuario o contraseña incorrecta");
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }
}