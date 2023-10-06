package com.example.appreto1;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

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

    //Strings para almacenarlos en el elemento Evento
    String titulo, descripcion, lugar;
    int ano, mes, dia;

    //Variables para el TimePicker
    String textoHora;
    Button botonHora;
    int hora, minuto;

    Fecha fecha = new Fecha();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        //obtenemos el bundle que nos interesa
        Bundle bundle = getIntent().getExtras();
        ano = bundle.getInt("año");
        mes = bundle.getInt("mes");
        dia = bundle.getInt("dia");
        //get los extras para ir rellenando la fecha, la ponemos en un texto para que el usuario previsualice
        txtFecha = findViewById(R.id.txtFechaProv);
        txtFecha.setText(dia+"-"+mes+"-"+ano);

        fecha.setDia(dia);
        fecha.setMes(mes);
        fecha.setAno(ano);

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

        titulo = textTitulo.getText().toString();
        descripcion = textDescripcion.getText().toString();
        lugar = textLugar.getText().toString();

        btnCrear = findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecha.setHora(hora);
                fecha.setAno(ano);
                Evento evento = new Evento(titulo,descripcion,lugar,fecha);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("usuario").document("Evento [i]").set(evento);

                Intent i = new Intent(ActivityEvent.this, MainActivityCalendarioEvento.class);
                startActivity(i);
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,-1,onTimeSetListener, hora, minuto, true);
        timePickerDialog.setTitle("Selecciona hora");
        timePickerDialog.show();
    }

    //Funcion para recoger los datos de los textviews y crear un elemento de tipo Evento
    public void crearEvento(String textoTitulo, String textoDescripcion,String textoLugar, int hora, int minuto ){
        Evento event = new Evento();
        Fecha fecha = new Fecha();
        fecha.setHora(hora);
        fecha.setMinuto(minuto);
        event.setTitulo(textoTitulo);
        event.setDescripcion(textoDescripcion);
        event.setLugar(textoLugar);
        event.setFecha(fecha.toString());
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