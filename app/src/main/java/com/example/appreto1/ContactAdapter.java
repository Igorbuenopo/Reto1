package com.example.appreto1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.checkerframework.checker.nullness.qual.NonNull;

import Entidades.Contacto;

public class ContactAdapter extends FirestoreRecyclerAdapter<Contacto, ContactAdapter.ContactViewHolder> {
    Context context;

    public ContactAdapter(@NonNull FirestoreRecyclerOptions<Contacto> options, Context context) {
        super(options);
        this.context=context;
    }

    //se crea cada contacto
    @Override
    protected void onBindViewHolder(@NonNull ContactViewHolder holder, int position, @NonNull Contacto contacto) {
        holder.tituloTxt.setText(contacto.getNombre());
        holder.numeroTxt.setText(contacto.getNumero());
        holder.direccionTxt.setText(contacto.getDireccion());

    }

    //se crea la view de cada contacto
    @androidx.annotation.NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view);
    }

    //clase de la view de contacto
    class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView tituloTxt,numeroTxt,direccionTxt;
        public ContactViewHolder(@NonNull View itemView){
            super(itemView);
            tituloTxt = itemView.findViewById(R.id.contacto_titulo);
            numeroTxt = itemView.findViewById(R.id.contacto_num);
            direccionTxt = itemView.findViewById(R.id.contacto_texto);
        }
    }

}
