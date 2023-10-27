package com.example.appreto1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    //clase de la view del evento
    public TextView tituloTextView;
    public TextView lugarTextView;
    public TextView horaTextView;
    public TextView descTextView;
    private final EventAdapter.OnLongItemListener onLongItemListener;




    public EventViewHolder(@NonNull View itemView, EventAdapter.OnLongItemListener onLongItemListener) {
        super(itemView);
        tituloTextView = itemView.findViewById(R.id.tveventtitulo);
        lugarTextView = itemView.findViewById(R.id.tveventlugar);
        horaTextView = itemView.findViewById(R.id.tveventhora);
        descTextView = itemView.findViewById(R.id.tvdesclugar);
        this.onLongItemListener = onLongItemListener;
        itemView.setOnLongClickListener(this);
    }



    //funcion que sirve para que haga algo cada vez que se mantiene la view de cada evento
    @Override
    public boolean onLongClick(View v) {
        onLongItemListener.OnItemLongClick(v, (String) tituloTextView.getText());
        return true;
    }
}

