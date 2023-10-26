package com.example.appreto1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Entidades.Evento;
import Entidades.Fecha;

public class ActivityEvent extends AppCompatActivity {



    //Crear los botones y hacer el onclick despues
    EditText textTitulo;
    EditText textDescripcion;
    EditText textLugar;
    Button btnCrear;
    TextView txtFecha;
    FloatingActionButton atras;

    //Strings para almacenarlos en el elemento Evento
    String titulo, descripcion, lugar;
    int ano, mes, dia;

    ArrayList<Evento> listaEventos = new ArrayList<Evento>();

    //Variables para el TimePicker
    String textoHora;
    Button botonHora;
    int hora, minuto;

   // Fecha fecha = new Fecha();

    String usuario;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        //obtenemos el bundle que nos interesa
        Bundle bundle = getIntent().getExtras();
        ano = bundle.getInt("año");
        mes = bundle.getInt("mes");
        dia = bundle.getInt("dia");
        usuario = bundle.getString("usuario");
        listaEventos = (ArrayList<Evento>) bundle.getSerializable("lista");
        //get los extras para ir rellenando la fecha, la ponemos en un texto para que el usuario previsualice
        txtFecha = findViewById(R.id.txtFechaProv);
        txtFecha.setText(dia+"-"+mes+"-"+ano);
        atras = findViewById(R.id.bteventcreateatras);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityEvent.this, ActivityListaEventos.class);
                i.putExtra("dia", dia);
                i.putExtra("mes", mes);
                i.putExtra("año", ano);
                i.putExtra("usuario", usuario);
                i.putExtra("lista", (Serializable) listaEventos);
                startActivity(i);
            }
        });



        //creamos boton de hora y automaticamente le asignamos la hora del sistema con la funcion
        // getHour()
        botonHora = findViewById(R.id.btnHora);
        getHour();

        //Al clickar despliega el reloj para elegir la hora deseada
        botonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(v);
            }
        });

        textTitulo = findViewById(R.id.txtTitulo);
        textDescripcion = findViewById(R.id.txtDescripcion);
        textLugar = findViewById(R.id.txtLugar);



        btnCrear = findViewById(R.id.btnCrear);
        //boton crear evento
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titulo = textTitulo.getText().toString();
                descripcion = textDescripcion.getText().toString();
                lugar = textLugar.getText().toString();

                //comprueba que este el titulo
                if(titulo.isEmpty()){
                    textTitulo.setError("Titulo obligatorio");
                    return;
                }

                //comprueba que el no se repita el nombre del titulo
                for(int i = 0; i<listaEventos.size(); i++){
                    if(listaEventos.get(i).getTitulo().equals(titulo)){
                        textTitulo.setError("Titulo repetido");
                        return;
                    }
                }

                Evento evento = new Evento(titulo,descripcion,hora,minuto,dia,mes,ano,lugar);
                crearEvento(evento);
            }
        });

    }

    //Funcion para abrir el TimePicker
    public void openTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hora = selectedHour;
                minuto = selectedMinute;
                botonHora.setText(String.format(Locale.getDefault(),"%02d:%02d",hora,minuto));
                textoHora = String.format(Locale.getDefault(),"%02d:%02d",hora,minuto);
            }

        };


        int Theme_Material_Dialog_Alert = 16974374;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,Theme_Material_Dialog_Alert,onTimeSetListener, hora, minuto, true);
        timePickerDialog.setTitle("Selecciona hora");
        timePickerDialog.show();
    }

    //Funcion para recoger los datos de los textviews y crear un elemento de tipo Evento
    public void crearEvento(Evento e){
        //no deja crear en modo invitado
        if(usuario.equals("Invitado")){
            AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ActivityEvent.this);


            builder.setTitle("Error Invitado");

            builder.setPositiveButton("Aceptar", (DialogInterface.OnClickListener) (dialog, which) -> {
                // boton de aceptar y cerrar pop-up

                dialog.cancel();
            });

            builder.setMessage("Create una cuenta para añadir eventos.");
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            //metemos el evento
            db.collection(usuario).document().set(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Intent i = new Intent(ActivityEvent.this, MainActivityCalendarioEvento.class);
                        i.putExtra("usuario",usuario);
                        startActivity(i);
                    }
                }
            });
        }
    }

    //metodo para coger la hora del sistema
    public void getHour(){
        hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minuto = Calendar.getInstance().get(Calendar.MINUTE);
        String hr = String.valueOf(hora) + String.valueOf(minuto);
        Log.d(TAG, "hora"+hr);
        botonHora.setText(String.format(Locale.getDefault(),"%02d:%02d",hora,minuto));
    }
}