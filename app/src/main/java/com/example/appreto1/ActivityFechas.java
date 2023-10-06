package com.example.appreto1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class ActivityFechas extends AppCompatActivity {

    // EN DESUSO

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button botonHora;

    Button btnVolver;
    TextView txtFecha;
    String textoHora;
    String textoFecha;
    int hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_fecha);
       botonHora = findViewById(R.id.btnHora);

        initDatePicker();
        dateButton = findViewById(R.id.btnFecha);
        dateButton.setText(getHoy());

        txtFecha = findViewById(R.id.mostrarFecha);

        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDatePicker(v);
            }
        });

        botonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(v);
            }
        });
    }

    private String getHoy() {
        Calendar cale = Calendar.getInstance();
        int ano = cale.get(Calendar.YEAR);
        int mes = cale.get(Calendar.MONTH);
        mes = mes + 1;
        int dia = cale.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dia, mes, ano);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes = mes + 1;
                String fecha = makeDateString(dia, mes, ano);
                dateButton.setText(fecha);
                textoFecha = fecha;
                txtFecha.setText(textoFecha);
            }
        };

        Calendar cale = Calendar.getInstance();
        int ano = cale.get(Calendar.YEAR);
        int mes = cale.get(Calendar.MONTH);
        int dia = cale.get(Calendar.DAY_OF_MONTH);

        // los estilos estan puestos en numerico,previamente con comando AlertDialog.THEME_X
        int style = -1;

        datePickerDialog = new DatePickerDialog(this, style,dateSetListener, ano, mes, dia);
    }

    private String makeDateString(int dia, int mes, int ano) {
        return dia + "-" + getMonthFormat(mes) +"-" + ano;
    }

    private String getMonthFormat(int mes) {
        if(mes ==1)
            return "ENE";
        if(mes ==2)
            return "FEB";
        if(mes ==3)
            return "MAR";
        if(mes ==4)
            return "ABR";
        if(mes ==5)
            return "MAY";
        if(mes ==6)
            return "JUN";
        if(mes ==7)
            return "JUL";
        if(mes ==8)
            return "AGO";
        if(mes ==9)
            return "SEP";
        if(mes ==10)
            return "OCT";
        if(mes ==11)
            return "NOV";
        if(mes ==12)
            return "DIC";

        //no deberia pasar nunca, se pone por defecto
        return "NULL";
    }

    private void openDatePicker(View view){
        datePickerDialog.show();
    }


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
}