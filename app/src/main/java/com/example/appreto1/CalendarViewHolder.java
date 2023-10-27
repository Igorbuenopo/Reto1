package com.example.appreto1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entidades.Evento;

//view donde se mete el numro del mes
public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView dayofMonth;
    private final CalendarAdapter.OnItemListener onItemListener;


    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);

        dayofMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    //en cada numero se pone la funcion onclick
    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayofMonth.getText());
    }
}
