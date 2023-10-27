package com.example.appreto1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import Entidades.Nota;

public class NoteAdapter extends FirestoreRecyclerAdapter<Nota, NoteAdapter.NoteViewHolder> {
    Context context;
    String usuario;

    public NoteAdapter(FirestoreRecyclerOptions<Nota> options, String usuario, Context context) {
        super(options);
        this.context = context;
        this.usuario = usuario;
    }
    //boton que crea la viewde cada nota
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    //boton que añade la nota a la view, que implementa on onlisyener que lleva a editar nota
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Nota nota) {
        holder.tituloTextView.setText(nota.getTitulo());
        holder.contentTextView.setText(nota.getContenido());
        holder.fechaTextView.setText(nota.getTime());
        holder.itemView.setOnClickListener((v) -> {
            Intent i = new Intent(context, ActivityAnyadirNota.class);
            i.putExtra("titulo", nota.getTitulo());
            i.putExtra("contenido", nota.getContenido());
            String docId = this.getSnapshots().getSnapshot(position).getId();
            i.putExtra("docId", docId);
            i.putExtra("usuario", usuario);
            context.startActivity(i);
        });
    }


    //clase de la view de notas
    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView tituloTextView, contentTextView, fechaTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tvnotetitulo);
            contentTextView = itemView.findViewById(R.id.tvnotecontenido);
            fechaTextView = itemView.findViewById(R.id.tvnotefecha);
        }
    }
}
