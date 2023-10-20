package com.example.appreto1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import Entidades.Evento;

public class MainActivityCalendarioEvento extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    Button siguiente;
    Button anterior;

    Button atras;

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    String usuario;

    ArrayList<Evento> listaEventos = new ArrayList<Evento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calendario_evento);
        initWidgets();

        Bundle bundle = getIntent().getExtras();
        usuario = bundle.getString("usuario");

        listaEventos = (ArrayList<Evento>) bundle.getSerializable("lista");


        Log.d("aaa", String.valueOf(listaEventos.size()));

        UTILS.selectedDate = LocalDate.now();

        setMonthView();
        siguiente = findViewById(R.id.mesSiguiente);
        anterior = findViewById(R.id.mesAnterior);
        atras = findViewById(R.id.atras);



       atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityCalendarioEvento.this, MainActivity2.class);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
       });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UTILS.selectedDate = UTILS.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UTILS.selectedDate = UTILS.selectedDate.minusMonths(1);
                setMonthView();
            }
        });
    }



    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.añoMesTv);

    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(UTILS.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(UTILS.selectedDate);
        ArrayList<Evento> eventoss = listaEventos;
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, eventoss, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = UTILS.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i<= 42; i++){
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add(null);
            }else{
                daysInMonthArray.add(LocalDate.of(UTILS.selectedDate.getYear(), UTILS.selectedDate.getMonth(),i-dayOfWeek));
            }
        }

        return daysInMonthArray;

    }
    private static String mayusMonth(String myDate){
        String monthMayus = String.valueOf(myDate.charAt(0)).toUpperCase()+ myDate.substring(1);
        return monthMayus;
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"));
        return mayusMonth(date.format(formatter));
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")){
            String message = "Selected Date "+dayText+" "+monthYearFromDate(UTILS.selectedDate);
            Toast.makeText(this,message, Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivityCalendarioEvento.this, ActivityEvent.class);
            i.putExtra("dia",Integer.valueOf(dayText));
            i.putExtra("mes",UTILS.selectedDate.getMonth().getValue());
            i.putExtra("año", UTILS.selectedDate.getYear());
            i.putExtra("usuario", usuario);
            startActivity(i);
        }
    }
}