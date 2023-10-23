package com.example.appreto1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import Entidades.Evento;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> daysOfMonth;
    private ArrayList<Evento> listaEventos = new ArrayList<Evento>();
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> daysOfMonth, ArrayList<Evento> listaEventos, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.listaEventos = listaEventos;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight()*0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = daysOfMonth.get(position);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LocalDate a = LocalDate.of(year,month+1,day);

        if(date == null){
            holder.dayofMonth.setText("");
        }else{
            holder.dayofMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(a)){
                holder.itemView.setBackgroundColor(Color.LTGRAY);
            }

            for(int i = 0; i < listaEventos.size(); i++){
                int anyo = listaEventos.get(i).getAno();
                int mes = listaEventos.get(i).getMes();
                int dia = listaEventos.get(i).getDia();
                LocalDate e = LocalDate.of(anyo,mes,dia);

                if(date.equals(e)){
                    holder.itemView.setBackgroundColor(Color.rgb(53, 127, 149));
                }
            }


        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }
}
