package com.example.appreto1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Contactos extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        recyclerView = findViewById(R.id.recyclerViewContact);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

    }


}
