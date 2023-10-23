package com.example.appreto1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Entidades.Evento;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {


    private ArrayList<Evento> listaEventos = new ArrayList<Evento>();
    String user;
    private final OnLongItemListener onLongItemListener;


    public EventAdapter(ArrayList<Evento> listaEventos, String usuario, OnLongItemListener onLongItemListener) {
        this.listaEventos = listaEventos;
        this.user = usuario;
        this.onLongItemListener = onLongItemListener;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view, onLongItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.tituloTextView.setText(listaEventos.get(position).getTitulo());
        holder.lugarTextView.setText(listaEventos.get(position).getLugar());
        String minuto = String.valueOf(listaEventos.get(position).getMinuto());
        String hora = String.valueOf(listaEventos.get(position).getHora());
        if(minuto.length() == 1){
            minuto = "0"+minuto;
        }


        if(hora.length() == 1){
            hora = "0"+hora;
        }


        String horas = hora+":"+minuto;
        holder.horaTextView.setText(horas);

    }


    @Override
    public int getItemCount() {
        return listaEventos.size();
    }


    public interface OnLongItemListener{
        void OnItemLongClick(View v , String titulo);
    }




}

